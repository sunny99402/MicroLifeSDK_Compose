package com.example.testjetpack

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testjetpack.ui.theme.TestJetpackTheme
import com.ideabus.ideabuslibrary.util.BaseUtils
import com.ideabus.model.bluetooth.MyBluetoothLE
import com.ideabus.model.data.*
import com.ideabus.model.protocol.BPMProtocol
import java.lang.StringBuilder
import java.util.*

class MainActivity : ComponentActivity(), BPMProtocol.OnConnectStateListener,
    BPMProtocol.OnDataResponseListener, BPMProtocol.OnNotifyStateListener,
    MyBluetoothLE.OnWriteStateListener {
    private val TAG = "BPMTestActivity"
    lateinit var bpmList: ListView
    private lateinit var logListAdapter: LogListAdapter
    private val isSendPersonParam = false
    private var toolbar: Toolbar? = null
    private var isConnecting = false
    private var userID = "123456789AB"
    private var age = 18

    private var requestBluetooth = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            //granted
        }else{
            //deny
        }
    }
    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach {
                Log.d("test006", "${it.key} = ${it.value}")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TestJetpackTheme {
                NavGraph()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    requestMultiplePermissions.launch(arrayOf(
                        Manifest.permission.BLUETOOTH_SCAN,
                        Manifest.permission.BLUETOOTH_CONNECT))
                }
                else{
                    val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                    requestBluetooth.launch(enableBtIntent)
                }
                Global.bpmProtocol = BPMProtocol.getInstance(this, false, true, Global.sdkid_BPM)
                logListAdapter = LogListAdapter(this)
                Global.bpmProtocol!!.setOnConnectStateListener(this)
                Global.bpmProtocol!!.setOnDataResponseListener(this)
                Global.bpmProtocol!!.setOnNotifyStateListener(this)
                Global.bpmProtocol!!.setOnWriteStateListener(this)
                startScan()
            }
        }
    }

    private fun startScan() {
        if (!Global.bpmProtocol!!.isSupportBluetooth(this)) {
            Log.d("Test", "1026 not support Bluetooth")
            return
        }
        logListAdapter?.addLog("start scan")
        Global.bpmProtocol!!.startScan(10)
    }

    @Composable
    private fun NavGraph() {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = "chose" ) {
            composable("chose") {
                ChoseScreen(navController)
            }
            composable("bpm") {
                BPMScreen(navController = navController, activity = this@MainActivity)
            }
        }
    }

    override fun onWriteMessage(isSuccess: Boolean, message: String) {
        logListAdapter?.addLog("WRITE : $message")
    }

    override fun onNotifyMessage(message: String) {
        logListAdapter?.addLog("NOTIFY : $message")
    }

    override fun onResponseReadHistory(dRecord: DRecord) {
        logListAdapter?.addLog("BPM : ReadHistory -> DRecord = $dRecord")
    }

    override fun onResponseClearHistory(isSuccess: Boolean) {
        logListAdapter?.addLog("BPM : ClearHistory -> isSuccess = $isSuccess")
    }

    override fun onResponseReadUserAndVersionData(user: User, versionData: VersionData) {
        logListAdapter?.addLog(
            "BPM : ReadUserAndVersionData -> user = " + user +
                    " , versionData = " + versionData
        )
    }

    override fun onResponseWriteUser(isSuccess: Boolean) {
        logListAdapter?.addLog("BPM : WriteUser -> isSuccess = $isSuccess")
    }

    override fun onResponseReadLastData(
        dRecord: CurrentAndMData,
        historyMeasuremeNumber: Int,
        userNumber: Int,
        MAMState: Int,
        isNoData: Boolean
    ) {
        logListAdapter?.addLog(
            "BPM : ReadLastData -> DRecord = " + dRecord +
                    " historyMeasuremeNumber = " + historyMeasuremeNumber +
                    " userNumber = " + userNumber + " MAMState = " + MAMState +
                    " isNoData = " + isNoData
        )
    }

    override fun onResponseClearLastData(isSuccess: Boolean) {
        logListAdapter?.addLog("BPM : ClearLastData -> isSuccess = $isSuccess")
    }

    override fun onResponseReadDeviceInfo(deviceInfo: DeviceInfo) {
        logListAdapter?.addLog("BPM : ReadDeviceInfo -> DeviceInfo = $deviceInfo")
    }

    override fun onResponseWriteDeviceTime(isSuccess: Boolean) {
        logListAdapter?.addLog("BPM : Write -> DeviceTime = $isSuccess")
    }

    override fun onResponseReadDeviceTime(deviceInfo: DeviceInfo) {
        logListAdapter?.addLog("BPM : Read -> DeviceTime = $deviceInfo")
    }

    override fun onBtStateChanged(isEnable: Boolean) {
        //BLE will be returned when it is turned enable or disable
        if (isEnable) {
            Toast.makeText(this, "BLE is enable!!", Toast.LENGTH_SHORT).show()
            startScan()
        } else {
            Toast.makeText(this, "BLE is disable!!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onScanResult(mac: String, name: String, rssi: Int) {
        BaseUtils.printLog("d", TAG, "1026 onScanResult:$name")
        //Blood pressure machine
        if (!name.startsWith("n/a")) {
            logListAdapter?.addLog("onScanResult：$name mac:$mac rssi:$rssi")
        }
        if (isConnecting) return
        isConnecting = true
        //Stop scanning before connecting
        Global.bpmProtocol!!.stopScan()
        //Connection
        if (name.startsWith("A")) {
            logListAdapter?.addLog("3G Model！")
            Global.bpmProtocol!!.connect(mac)
        } else {
            logListAdapter?.addLog("4G Model！")
            Global.bpmProtocol!!.bond(mac)
        }
    }

    override fun onConnectionState(state: BPMProtocol.ConnectState) {
        //BLE connection status return, used to judge connection or disconnection
        when (state) {
            BPMProtocol.ConnectState.Connected -> {
                isConnecting = false
                //findViewById<View>(R.id.buttonView).visibility = View.VISIBLE
                logListAdapter?.addLog("Connected")
            }
            BPMProtocol.ConnectState.ConnectTimeout -> {
                isConnecting = false
                //findViewById<View>(R.id.buttonView).visibility = View.GONE
                logListAdapter?.addLog("ConnectTimeout")
            }
            BPMProtocol.ConnectState.Disconnect -> {
                isConnecting = false
                //findViewById<View>(R.id.buttonView).visibility = View.GONE
                logListAdapter?.addLog("Disconnected")
                startScan()
            }
            BPMProtocol.ConnectState.ScanFinish -> {
                //findViewById<View>(R.id.buttonView).visibility = View.GONE
                logListAdapter?.addLog("ScanFinish")
                startScan()
            }
        }
    }

    fun setUserInfo() {
        val ran = Random()
        val A = IntArray(11)
        val stringBuilder = StringBuilder()
        for (i in 0..10) {
            if (i < 9) {
                A[i] = (Math.random() * 10 + 48).toInt()
            } else {
                A[i] = (Math.random() * 26 + 65).toInt()
            }
            stringBuilder.append(A[i].toChar())
        }
        userID = stringBuilder.toString()
        age = 18 + ran.nextInt(62)
    }
}
