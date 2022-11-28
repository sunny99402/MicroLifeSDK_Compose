package com.example.testjetpack

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.testjetpack.BPM.BPMTestActivity

@Composable
fun ChoseScreen() {
    val context = LocalContext.current
    Scaffold(
        topBar = { ChoseTopBar() }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                modifier = Modifier
                    .width(250.dp)
                    .padding(top = 30.dp),
                onClick = {
                    context.startActivity(Intent(context, BPMTestActivity::class.java))
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(211,211,211)),
            ) {
                Text(text = "BPM")
            }
        }
    }
}

@Composable
fun ChoseTopBar() {
    TopAppBar(
        title = {
            Text(text = "Microlife SDK Test", fontSize = 20.sp)
        },
        backgroundColor = Color.White
    )
}


