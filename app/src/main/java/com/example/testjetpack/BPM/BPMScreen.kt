package com.example.testjetpack

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun BPMScreen(navController: NavController) {
    val context = LocalContext.current

    Scaffold(
        topBar = { BPMTopBar() }
    ) {
        Column() {
            ButtonView()
            TextList()
        }
    }
}

@Composable
fun TextList() {
    Column() {
        Text(
            text = "Log",
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun ButtonView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(start = 3.dp, end = 3.dp)) {
            Button(
                onClick = {  },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(211,211,211)),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 3.dp)
            ) {
                Text(text = "ReadHistory")
            }
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(211,211,211)),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "ClearHistory")
            }
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(211,211,211)),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 3.dp)
            ) {
                Text(text = "Disconnect")
            }
        }
        Row(modifier = Modifier
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(start = 3.dp, end = 3.dp)) {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(211,211,211)),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 3.dp)
            ) {
                Text(text = "ReadDevice")
            }
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(211,211,211)),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "WriteUser")
            }
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(211,211,211)),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 3.dp)
            ) {
                Text(text = "ReadVersion")
            }
        }
        Row(modifier = Modifier
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(start = 3.dp, end = 3.dp)) {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(211,211,211)),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 3.dp)
            ) {
                Text(text = "Read Last Data")
            }
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(211,211,211)),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 3.dp)
            ) {
                Text(text = "Clear Last Data")
            }
        }
        Row(modifier = Modifier
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(start = 3.dp, end = 3.dp)) {
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(211,211,211)),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 3.dp)
            ) {
                Text(text = "Read Device Time")
            }
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(211,211,211)),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 3.dp)
            ) {
                Text(text = "Sync Device Time")
            }
        }
    }
}

@Composable
fun BPMTopBar() {
    TopAppBar(
        title = {
            Column() {
                Text(text = "Microlife SDK Test", fontSize = 20.sp)
                Text(text = "Blood Pressure", fontSize = 15.sp)
            }
        },
        backgroundColor = Color.White
    )
}
