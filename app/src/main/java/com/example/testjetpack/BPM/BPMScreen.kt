package com.example.testjetpack

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

var userID = "123456789AB"
var age = 18

@SuppressLint("UnrememberedMutableState")
@Composable
fun BPMScreen(model: ViewModel) {
    val data by model.listData.observeAsState(initial = emptyList())
    val name by mutableStateOf(model.name)
    val connectState by mutableStateOf(model.isConnect)

    Scaffold(
        topBar = { BPMTopBar() }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            if(connectState) {
                ButtonView(name)
            }
            TextList(data)
        }
    }
}

@Composable
fun TextList(data: List<String>) {
    Column() {
        Text(
            text = "Log",
            modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
        )

        val listState = rememberLazyListState()
        // Remember a CoroutineScope to be able to launch
        val coroutineScope = rememberCoroutineScope()
        LazyColumn(state = listState) {
            items(data) { index ->
                TextItem(
                    text = index,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, color = Color(245, 245, 245)),
                )
            }
            coroutineScope.launch {
                // Animate scroll to the last item
                listState.animateScrollToItem(index = data.size)
            }
        }
    }
}

@Composable
fun TextItem(text: String, modifier: Modifier) {
    var color: Color =
        if(text.startsWith("WRITE")) {
            Color.Red
        } else if(text.startsWith("NOTIFY")) {
            Color.Blue
        } else {
            Color.Green
        }
    Text(
        text = text,
        modifier = modifier,
        color = color)
}

@Composable
fun ButtonView(name: String?) {
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

        if(name?.startsWith("A") == true) {
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
        }

        if(name?.startsWith("B") == true) {
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
}

@Composable
fun BPMTopBar() {
    TopAppBar(
        title = {
            Column() {
                Text(text = "Microlife SDK Test", fontSize = 20.sp)
                Text(text = "Blood Pressure ${Global.bpmProtocol!!.sdkVersion}", fontSize = 15.sp)
            }
        },
        backgroundColor = Color.White
    )
}
