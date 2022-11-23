package com.example.testjetpack

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

var userID = "123456789AB"
var age = 18

@Composable
fun BPMScreen() {

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
                onClick = { Global.bpmProtocol!!.readHistorysOrCurrDataAndSyncTiming() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(211,211,211)),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 3.dp)
            ) {
                Text(text = "ReadHistory")
            }
            Button(
                onClick = { Global.bpmProtocol!!.clearAllHistorys() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(211,211,211)),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "ClearHistory")
            }
            Button(
                onClick = { Global.bpmProtocol!!.disconnect() },
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
                onClick = { Global.bpmProtocol!!.readDeviceInfo() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(211,211,211)),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 3.dp)
            ) {
                Text(text = "ReadDevice")
            }
            Button(
                onClick = {
                    Global.bpmProtocol!!.writeUserData(userID, age) },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(211,211,211)),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "WriteUser")
            }
            Button(
                onClick = { Global.bpmProtocol!!.readUserAndVersionData() },
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
                onClick = { Global.bpmProtocol!!.readLastData() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(211,211,211)),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 3.dp)
            ) {
                Text(text = "Read Last Data")
            }
            Button(
                onClick = { Global.bpmProtocol!!.clearLastData() },
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
                onClick = { Global.bpmProtocol!!.readDeviceTime() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(211,211,211)),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 3.dp)
            ) {
                Text(text = "Read Device Time")
            }
            Button(
                onClick = { Global.bpmProtocol!!.syncTiming() },
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
