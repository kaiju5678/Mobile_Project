package com.example.mobile_project.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_project.R

@Composable
fun LoginScreen(onNavigateToRegister: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- ส่วนของโลโก้ ---
        Icon(
            painter = painterResource(id = R.drawable.hitcar_template),
            contentDescription = "Hitcar Logo",
            modifier = Modifier.size(120.dp),
            tint = Color(0xFF00337C)
        )

        Text(
            text = "hitcar",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF00337C),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = "Login to your Account",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF00337C),
            modifier = Modifier.padding(bottom = 40.dp)
        )

        // --- ส่วนของฟอร์มพื้นหลังสีฟ้าอ่อน ---
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color(0xFFE1F1FA),
                    shape = RoundedCornerShape(topStart = 60.dp, topEnd = 60.dp)
                )
                .padding(32.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(30.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color(0xFF00337C),
                        unfocusedBorderColor = Color(0xFF00337C)
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(30.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    trailingIcon = {
                        Icon(imageVector = Icons.Default.Visibility, contentDescription = null)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = Color(0xFF00337C),
                        unfocusedBorderColor = Color(0xFF00337C)
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { /* TODO: Login Logic */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00337C)),
                    shape = RoundedCornerShape(30.dp)
                ) {
                    Text("Login", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = { }) {
                    Text(
                        text = "Forget Password ?",
                        color = Color(0xFF00337C),
                        textDecoration = androidx.compose.ui.text.style.TextDecoration.Underline
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f), color = Color.Gray)
                    Text(
                        text = " or ",
                        modifier = Modifier.padding(horizontal = 8.dp),
                        color = Color(0xFF00337C)
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f), color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Surface(
                    onClick = { },
                    shape = androidx.compose.foundation.shape.CircleShape,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color.Gray),
                    modifier = Modifier.size(50.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("G", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ใส่ modifier clickable เพื่อให้กดแล้วเปลี่ยนหน้า
                Row(modifier = Modifier.clickable { onNavigateToRegister() }) {
                    Text(text = "Don't have an account? ", color = Color(0xFF00337C))
                    Text(
                        text = "Register",
                        color = Color(0xFF00337C),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}