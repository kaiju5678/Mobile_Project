package com.example.mobile_project.vehicle

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun VehicleTestScreen() {
    val context = LocalContext.current
    val vehicleViewModel: VehicleViewModel = viewModel(factory = VehicleViewModelFactory(context))

//        ใช้ครั้งแรกที่ Run พอ
//// LaunchedEffect(Unit) จะทำงานแค่ครั้งเดียวตอน Composable ถูกเรียก
//    LaunchedEffect(Unit) {
//        // สั่งเพิ่มข้อมูลเข้าไปตรงๆ ผ่าน ViewModel
//        vehicleViewModel.insertVehicle(
//            brand = "Toyota",
//            model = "Yaris Ativ",
//            segment = "B-Segment",
//            seats = 4,
//            door = 4,
//            gear = "Auto",
//            energy = "GasoLine",
//            price = 459000.0,
//            img = null,
//            status = "Available"
//        )
//        vehicleViewModel.insertVehicle(
//            brand = "Toyota",
//            model = "Corolla Altis",
//            segment = "C-Segment",
//            seats = 4,
//            door = 4,
//            gear = "Auto",
//            energy = "Gasoline",
//            price = 579000.0,
//            img = null,
//            status = "Available"
//        )
//        vehicleViewModel.insertVehicle(
//            brand = "Toyota",
//            model = "Camry",
//            segment = "D-Segment",
//            seats = 4,
//            door = 4,
//            gear = "Auto",
//            energy = "Gasoline",
//            price = 759000.0,
//            img = null,
//            status = "Available"
//        )
//        vehicleViewModel.insertVehicle(
//            brand = "Honda",
//            model = "City",
//            segment = "B-Segment",
//            seats = 4,
//            door = 4,
//            gear = "Auto",
//            energy = "Gasoline",
//            price = 479000.0,
//            img = null,
//            status = "Available"
//        )
//        vehicleViewModel.insertVehicle(
//            brand = "Honda",
//            model = "Civic",
//            segment = "C-Segment",
//            seats = 4,
//            door = 4,
//            gear = "Auto",
//            energy = "Gasoline",
//            price = 679000.0,
//            img = null,
//            status = "Available"
//        )
//        vehicleViewModel.insertVehicle(
//            brand = "Honda",
//            model = "Accord",
//            segment = "D-Segment",
//            seats = 4,
//            door = 4,
//            gear = "Auto",
//            energy = "Gasoline",
//            price = 789000.0,
//            img = null,
//            status = "Available"
//        )
//        vehicleViewModel.insertVehicle(
//            brand = "Mazda",
//            model = "2",
//            segment = "B-Segment",
//            seats = 4,
//            door = 4,
//            gear = "Auto",
//            energy = "Gasoline",
//            price = 429000.0,
//            img = null,
//            status = "Available"
//        )
//        vehicleViewModel.insertVehicle(
//            brand = "Mazda",
//            model = "3",
//            segment = "C-Segment",
//            seats = 4,
//            door = 4,
//            gear = "Auto",
//            energy = "Gasoline",
//            price = 659000.0,
//            img = null,
//            status = "Available"
//        )
//        vehicleViewModel.insertVehicle(
//            brand = "Mazda",
//            model = "6e",
//            segment = "D-Segment",
//            seats = 4,
//            door = 4,
//            gear = "Auto",
//            energy = "Electric",
//            price = 829000.0,
//            img = null,
//            status = "Available"
//        )
//
//        // จะเพิ่มกี่คันก็ก๊อปปี้คำสั่ง insertVehicle วางต่อกันได้เลยครับ
//    }

    val vehicles by vehicleViewModel.allVehicles.collectAsState(initial = emptyList())

    // --- State สำหรับเก็บค่าทุก Field ที่สอดคล้องกับ VehicleEntity ---
    var brand by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var segment by remember { mutableStateOf("") }
    var seats by remember { mutableStateOf("") }
    var door by remember { mutableStateOf("") }
    var gear by remember { mutableStateOf("") }
    var energy by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var img by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }



    // เปลี่ยนมาใช้ LazyColumn ครอบทั้งหมดเพื่อให้ Scroll ได้เวลาช่องกรอกข้อมูลเยอะๆ
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp) // เว้นระยะห่างระหว่างแต่ละช่องนิดหน่อย
    ) {
        // --- ส่วนของฟอร์มกรอกข้อมูล ---
        item {
            Text(text = "Add New Vehicle", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(value = brand, onValueChange = { brand = it }, label = { Text("Brand (e.g., Honda)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = model, onValueChange = { model = it }, label = { Text("Model (e.g., Civic)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = segment, onValueChange = { segment = it }, label = { Text("Segment (e.g., Sedan, SUV)") }, modifier = Modifier.fillMaxWidth())

            // จัดกลุ่ม Seats และ Door ให้อยู่ในบรรทัดเดียวกันเพื่อประหยัดพื้นที่
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = seats, onValueChange = { seats = it },
                    label = { Text("Seats") }, modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = door, onValueChange = { door = it },
                    label = { Text("Doors") }, modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            // จัดกลุ่ม Gear และ Energy ให้อยู่ในบรรทัดเดียวกัน
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = gear, onValueChange = { gear = it }, label = { Text("Gear (Auto/Manual)") }, modifier = Modifier.weight(1f))
                OutlinedTextField(value = energy, onValueChange = { energy = it }, label = { Text("Energy (Petrol/EV)") }, modifier = Modifier.weight(1f))
            }

            OutlinedTextField(
                value = price, onValueChange = { price = it },
                label = { Text("Price") }, modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(value = img, onValueChange = { img = it }, label = { Text("Image URL (Optional)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = status, onValueChange = { status = it }, label = { Text("Status (e.g., Available)") }, modifier = Modifier.fillMaxWidth())

            Button(
                onClick = {
                    // ตรวจสอบว่าฟิลด์สำคัญไม่เป็นค่าว่างก่อนบันทึก
                    if (brand.isNotBlank() && model.isNotBlank()) {
                        vehicleViewModel.insertVehicle(
                            brand = brand,
                            model = model,
                            segment = segment,
                            seats = seats.toIntOrNull() ?: 0, // ถ้าแปลงเป็นตัวเลขไม่ได้ให้เป็น 0
                            door = door.toIntOrNull() ?: 0,
                            gear = gear,
                            energy = energy,
                            price = price.toDoubleOrNull() ?: 0.0,
                            img = img.ifBlank { null }, // ถ้าไม่ได้กรอกให้เป็น null ตามฐานข้อมูล
                            status = status
                        )
                        // เคลียร์ช่องฟอร์มหลังบันทึก
                        brand = ""; model = ""; segment = ""; seats = ""
                        door = ""; gear = ""; energy = ""; price = ""
                        img = ""; status = ""
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) {
                Text("Save to Database")
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Vehicle List", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
        }

        // --- ส่วนของ List แสดงข้อมูลจาก Database ---
        items(vehicles) { vehicle ->
            VehicleItemCard(vehicle, onDelete = { vehicleViewModel.deleteVehicle(it) })
        }
    }
}

@Composable
fun VehicleItemCard(vehicle: VehicleEntity, onDelete: (VehicleEntity) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "${vehicle.brand} ${vehicle.model}", fontWeight = FontWeight.Bold)

                // แสดงข้อมูลรายละเอียดเพิ่มเติมที่เพิ่งเพิ่มเข้าไป
                Text(
                    text = "Segment: ${vehicle.segment} | Seats: ${vehicle.seats} | Door: ${vehicle.door}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Gear: ${vehicle.gear} | Energy: ${vehicle.energy}",
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Price: ${vehicle.price} | Status: ${vehicle.status}", color = MaterialTheme.colorScheme.primary)
            }

            Button(
                onClick = { onDelete(vehicle) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Delete")
            }
        }
    }
}