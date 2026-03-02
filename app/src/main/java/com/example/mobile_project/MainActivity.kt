package com.example.mobile_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobile_project.ui.theme.Mobile_ProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WelcomeScreen()
        }
    }
}


// กำหนดสีตามภาพ Design
val TopBackgroundColor = Color(0xFFF7F8FA) // สีขาวอมเทาพื้นหลังด้านบน
val PrimaryDarkBlue = Color(0xFF0D3D82) // สีน้ำเงินเข้ม (ปุ่ม Login, ข้อความ)
val BottomPanelBlue = Color(0xFF2FA2E9) // สีฟ้าอ่อน (พื้นหลังด้านล่าง)

@Composable
fun WelcomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(TopBackgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- ส่วนบน (โลโก้ และ ข้อความ) ---
        Spacer(modifier = Modifier.height(80.dp))

        // ใส่รูปโลโก้ของคุณตรงนี้ (เปลี่ยน R.drawable.hitcar_logo เป็นชื่อไฟล์ของคุณ)
        Image(
            painter = painterResource(id = R.drawable.hitcar_template),
            contentDescription = "HitCar Logo",
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Let's find your\nperfect car!",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryDarkBlue,
            textAlign = TextAlign.Center,
            lineHeight = 38.sp
        )

        // ดันเนื้อหาส่วนล่างให้ไปติดขอบล่าง
        Spacer(modifier = Modifier.weight(1f))

        // --- ส่วนล่าง (กล่องสีฟ้าและปุ่มกด) ---
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = BottomPanelBlue,
            shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp) // ขอบมนด้านบน
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 56.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Hello",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Welcome To HirCar, where\nyou can find your perfect car",
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(48.dp))

                // ปุ่ม Login
                Button(
                    onClick = { /* TODO: ใส่ Action เมื่อกด Login */ },
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryDarkBlue),
                    shape = RoundedCornerShape(28.dp) // ปุ่มโค้งมนแบบแคปซูล
                ) {
                    Text(
                        text = "Login",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ปุ่ม Register
                Button(
                    onClick = { /* TODO: ใส่ Action เมื่อกด Register */ },
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text(
                        text = "Register",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryDarkBlue
                    )
                }
            }
        }
    }
}