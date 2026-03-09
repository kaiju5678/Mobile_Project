package com.example.mobile_project.firebaseDB

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

// เก็บ email ของ user ที่ login อยู่
object UserSession {
    var currentUserEmail: String = ""
}

// Data Class — ไม่เก็บ password แล้ว (Firebase Auth จัดการให้)
data class User(
    val id: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val status: String = "active",
    val role: String = "user"
)

// DataSource
class FirestoreUserDataSource {
    private val collection = Firebase.firestore.collection("users")

    suspend fun insertWithId(user: User, uid: String) {
        // ใช้ Firebase Auth UID เป็น Document ID เพื่อ link กัน
        collection.document(uid).set(user).await()
    }
}

// Repository
class UserRepository(
    private val dataSource: FirestoreUserDataSource = FirestoreUserDataSource()
) {
    suspend fun insertWithId(user: User, uid: String) {
        dataSource.insertWithId(user, uid)
    }
}

// ViewModel
class UserViewModel(
    private val repository: UserRepository = UserRepository()
) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    // ── Register ──
    // สร้าง account ใน Firebase Auth ก่อน แล้วค่อย save profile ลง Firestore
    fun registerUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phone: String,
        onResult: (Boolean, String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val uid = authResult.user?.uid ?: run {
                    onResult(false, "Failed to get user ID.")
                    return@addOnSuccessListener
                }
                val newUser = User(
                    id = uid,
                    email = email,
                    firstName = firstName,
                    lastName = lastName,
                    phone = phone
                )
                viewModelScope.launch {
                    try {
                        repository.insertWithId(newUser, uid)
                        onResult(true, "")
                    } catch (e: Exception) {
                        onResult(false, e.message ?: "Failed to save profile.")
                    }
                }
            }
            .addOnFailureListener { e ->
                val msg = when {
                    e.message?.contains("email address is already in use") == true ->
                        "This email is already registered."
                    e.message?.contains("badly formatted") == true ->
                        "Invalid email format."
                    e.message?.contains("at least 6") == true ->
                        "Password must be at least 6 characters."
                    else -> e.message ?: "Registration failed."
                }
                onResult(false, msg)
            }
    }

    // ── Login ──
    // ใช้ Firebase Auth แทน Firestore query — รองรับ Forgot Password
    fun loginUser(
        email: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                UserSession.currentUserEmail = email
                onResult(true, "")
            }
            .addOnFailureListener { e ->
                val msg = when {
                    e.message?.contains("no user record") == true ||
                            e.message?.contains("identifier") == true ->
                        "No account found with this email."
                    e.message?.contains("password is invalid") == true ||
                            e.message?.contains("incorrect") == true ->
                        "Incorrect password."
                    e.message?.contains("too many") == true ||
                            e.message?.contains("blocked") == true ->
                        "Too many failed attempts. Try again later."
                    else -> "Invalid email or password."
                }
                onResult(false, msg)
            }
    }

    // ── Forgot Password ──
    // Firebase Auth ส่ง reset email ให้อัตโนมัติ ไม่ต้องเขียน logic เอง
    fun sendPasswordReset(
        email: String,
        onResult: (Boolean, String) -> Unit
    ) {
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                onResult(true, "")
            }
            .addOnFailureListener { e ->
                val msg = when {
                    e.message?.contains("no user record") == true ||
                            e.message?.contains("identifier") == true ->
                        "No account found with this email."
                    e.message?.contains("badly formatted") == true ->
                        "Invalid email format."
                    else -> e.message ?: "Failed to send reset email."
                }
                onResult(false, msg)
            }
    }

    // ── Fetch Profile ──
    fun fetchUserData(email: String, onResult: (User?) -> Unit) {
        val uid = auth.currentUser?.uid
        if (uid != null) {
            // ใช้ UID โดยตรง — เร็วกว่า query
            db.collection("users").document(uid).get()
                .addOnSuccessListener { doc -> onResult(doc.toObject(User::class.java)) }
                .addOnFailureListener { onResult(null) }
        } else {
            // fallback สำหรับ Google login
            db.collection("users").whereEqualTo("email", email).get()
                .addOnSuccessListener { docs ->
                    onResult(if (!docs.isEmpty) docs.documents[0].toObject(User::class.java) else null)
                }
                .addOnFailureListener { onResult(null) }
        }
    }

    // ── Update Profile ──
    fun updateUserData(
        oldEmail: String,
        newEmail: String,
        newFirstName: String,
        newLastName: String,
        newPhone: String,
        onResult: (Boolean) -> Unit
    ) {
        val uid = auth.currentUser?.uid
        val updateMap = mapOf(
            "email" to newEmail,
            "firstName" to newFirstName,
            "lastName" to newLastName,
            "phone" to newPhone
        )

        if (uid != null) {
            db.collection("users").document(uid)
                .update(updateMap)
                .addOnSuccessListener { onResult(true) }
                .addOnFailureListener { onResult(false) }
        } else {
            // fallback สำหรับ Google login
            db.collection("users").whereEqualTo("email", oldEmail).get()
                .addOnSuccessListener { docs ->
                    if (!docs.isEmpty) {
                        db.collection("users").document(docs.documents[0].id)
                            .update(updateMap)
                            .addOnSuccessListener { onResult(true) }
                            .addOnFailureListener { onResult(false) }
                    } else {
                        onResult(false)
                    }
                }
                .addOnFailureListener { onResult(false) }
        }
    }
}