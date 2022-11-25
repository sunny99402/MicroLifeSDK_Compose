package com.example.testjetpack.BPM

import com.ideabus.model.protocol.BPMProtocol
import com.ideabus.model.bluetooth.MyBluetoothLE
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.testjetpack.*
import com.ideabus.ideabuslibrary.util.BaseUtils
import com.ideabus.model.data.*

class BPMTestActivity() : ComponentActivity(), BPMProtocol.OnConnectStateListener,
    View.OnClickListener, BPMProtocol.OnDataResponseListener, BPMProtocol.OnNotifyStateListener,
    MyBluetoothLE.OnWriteStateListener {
    private val TAG = "BPMTestActivity"
    var logListAdapter: LogListAdapter? = null
    private var isConnecting = false

    private val vm: LogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        //Initialize the body ester machine Bluetooth module
        super.onCreate(savedInstanceState)
        initParam()
        setContent {
            ConnectScreen()
        }
    }

    fun initParam() {
        //Initialize the connection SDK
        Global.bpmProtocol = BPMProtocol.getInstance(this, false, true, Global.sdkid_BPM)
        logListAdapter = LogListAdapter()
    }

    override fun onStart() {
        Log.d(TAG, "1026 onStart")
        super.onStart()

        Global.bpmProtocol!!.setOnConnectStateListener(this)
        Global.bpmProtocol!!.setOnDataResponseListener(this)
        Global.bpmProtocol!!.setOnNotifyStateListener(this)
        Global.bpmProtocol!!.setOnWriteStateListener(this)
        startScan()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Global.bpmProtocol!!.isConnected) Global.bpmProtocol!!.disconnect()
        Global.bpmProtocol!!.stopScan()
    }

    private fun startScan() {
        if (!Global.bpmProtocol!!.isSupportBluetooth(this)) {
            Log.d(TAG, "1026 not support Bluetooth")
            return
        }
        logListAdapter?.addLog("start scan", model = vm)
        Global.bpmProtocol!!.startScan(10)
    }

    override fun onStop() {
        super.onStop()
        Global.bpmProtocol!!.disconnect()
        Global.bpmProtocol!!.stopScan()
    }

    override fun onClick(v: View) {
        val btn = findViewById<View>(v.id) as Button
        logListAdapter?.addLog("WRITE : " + btn.text.toString(), model = vm)

    }

    override fun onWriteMessage(isSuccess: Boolean, message: String) {
        logListAdapter?.addLog("WRITE : $message", model = vm)
    }

    override fun onNotifyMessage(message: String) {
        logListAdapter?.addLog("NOTIFY : $message", model = vm)
    }

    override fun onResponseReadHistory(dRecord: DRecord) {
        logListAdapter?.addLog("BPM : ReadHistory -> DRecord = $dRecord", model = vm)
    }

    override fun onResponseClearHistory(isSuccess: Boolean) {
        logListAdapter?.addLog("BPM : ClearHistory -> isSuccess = $isSuccess", model = vm)
    }

    override fun onResponseReadUserAndVersionData(user: User, versionData: VersionData) {
        logListAdapter?.addLog(
            "BPM : ReadUserAndVersionData -> user = " + user +
                    " , versionData = " + versionData
            , model = vm)
    }

    override fun onResponseWriteUser(isSuccess: Boolean) {
        logListAdapter?.addLog("BPM : WriteUser -> isSuccess = $isSuccess", model = vm)
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
            , model = vm)
    }

    override fun onResponseClearLastData(isSuccess: Boolean) {
        logListAdapter?.addLog("BPM : ClearLastData -> isSuccess = $isSuccess", model = vm)
    }

    override fun onResponseReadDeviceInfo(deviceInfo: DeviceInfo) {
        logListAdapter?.addLog("BPM : ReadDeviceInfo -> DeviceInfo = $deviceInfo", model = vm)
    }

    override fun onResponseWriteDeviceTime(isSuccess: Boolean) {
        logListAdapter?.addLog("BPM : Write -> DeviceTime = $isSuccess", model = vm)
    }

    override fun onResponseReadDeviceTime(deviceInfo: DeviceInfo) {
        logListAdapter?.addLog("BPM : Read -> DeviceTime = $deviceInfo", model = vm)
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
            logListAdapter?.addLog("onScanResult：$name mac:$mac rssi:$rssi", model = vm)
        }
        if (isConnecting) return
        isConnecting = true
        //Stop scanning before connecting
        Global.bpmProtocol!!.stopScan()
        //Connection
        if (name.startsWith("A")) {
            logListAdapter?.addLog("3G Model！", model = vm)
            Global.bpmProtocol!!.connect(mac)

        } else {
            logListAdapter?.addLog("4G Model！", model = vm)
            Global.bpmProtocol!!.bond(mac)

        }
    }

    override fun onConnectionState(state: BPMProtocol.ConnectState) {
        //BLE connection status return, used to judge connection or disconnection
        when (state) {
            BPMProtocol.ConnectState.Connected -> {
                isConnecting = false
                logListAdapter?.addLog("Connected", model = vm)
                setContent {
                    BPMScreen(model =  vm)
                }
            }
            BPMProtocol.ConnectState.ConnectTimeout -> {
                isConnecting = false
                logListAdapter?.addLog("ConnectTimeout", model = vm)
            }
            BPMProtocol.ConnectState.Disconnect -> {
                isConnecting = false
                logListAdapter?.addLog("Disconnected", model = vm)
                startScan()
                setContent {
                    ConnectScreen()
                }
            }
            BPMProtocol.ConnectState.ScanFinish -> {
                logListAdapter?.addLog("ScanFinish", model = vm)
                startScan()
            }
        }
    }
}