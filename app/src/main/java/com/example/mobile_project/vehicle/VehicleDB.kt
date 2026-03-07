package com.example.mobile_project.vehicle

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

// 1. Entity (ตารางข้อมูล)
@Entity(tableName = "vehicles")
data class VehicleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val brand: String,
    val model: String,
    val segment: String,
    val seats: Int,
    val door: Int,
    val gear: String,
    val energy: String,
    val price: Double,
    val img: String?, // ให้เป็น nullable เผื่อยังไม่มีรูป
    val status: String
)

// 2. DAO (คำสั่งจัดการข้อมูล)
@Dao
interface VehicleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vehicle: VehicleEntity)

    @Query("SELECT * FROM vehicles")
    fun getAllVehicles(): Flow<List<VehicleEntity>>

    @Query("SELECT * FROM vehicles WHERE id = :id")
    fun getVehicleById(id: Int): Flow<VehicleEntity?>

    @Update
    suspend fun update(vehicle: VehicleEntity)

    @Delete
    suspend fun delete(vehicle: VehicleEntity)
}

// 3. Database
@Database(entities = [VehicleEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "hitcar_database"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}

// 4. Repository (ตัวกลางระหว่าง DAO กับ ViewModel)
class VehicleRepository(private val dao: VehicleDao) {
    val allVehicles: Flow<List<VehicleEntity>> = dao.getAllVehicles()

    suspend fun insert(vehicle: VehicleEntity) {
        dao.insert(vehicle)
    }

    fun getVehicleById(id: Int): Flow<VehicleEntity?> {
        return dao.getVehicleById(id)
    }

    suspend fun update(vehicle: VehicleEntity) {
        dao.update(vehicle)
    }

    suspend fun delete(vehicle: VehicleEntity) {
        dao.delete(vehicle)
    }
}

// 5. ViewModel
class VehicleViewModel(private val repository: VehicleRepository) : ViewModel() {
    val allVehicles = repository.allVehicles

    fun insertVehicle(
        brand: String, model: String, segment: String, seats: Int,
        door: Int, gear: String, energy: String, price: Double,
        img: String?, status: String
    ) {
        viewModelScope.launch {
            repository.insert(
                VehicleEntity(
                    brand = brand, model = model, segment = segment, seats = seats,
                    door = door, gear = gear, energy = energy, price = price,
                    img = img, status = status
                )
            )
        }
    }

    fun deleteVehicle(vehicle: VehicleEntity) {
        viewModelScope.launch {
            repository.delete(vehicle)
        }
    }
}

// 6. ViewModel Factory
class VehicleViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VehicleViewModel::class.java)) {
            val database = AppDatabase.getDatabase(context)
            val repository = VehicleRepository(database.vehicleDao())
            @Suppress("UNCHECKED_CAST")
            return VehicleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}