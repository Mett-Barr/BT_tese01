package com.example.bttese01.bluetooth

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class Bluetooth(private val activity: Activity) {

    private val manager: BluetoothManager =
        activity.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

    private val adapter: BluetoothAdapter = manager.adapter

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, intent: Intent?) {
            val action = intent?.action
            if (action == BluetoothAdapter.ACTION_STATE_CHANGED) {
                val newState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0)
                val oldState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0)

                Log.d("bluetooth state", "newState:$newState oldState:$oldState")
            }
        }
    }

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            Log.d("!!! scanCallback", "onScanResult  callbackType:$callbackType result:$result")
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            super.onBatchScanResults(results)
            Log.d("!!!scanCallback", "onBatchScanResults  result:$results")
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Log.d("!!! scanCallback", "onScanFailed  errorCode:$errorCode")
        }
    }

    private val scanSettings = ScanSettings.CREATOR

    private val builder = ScanSettings.Builder()
        // 掃描模式
        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)

        // 回調類型
        .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)

        // 掃描器匹配模式
        .setMatchMode(ScanSettings.MATCH_MODE_AGGRESSIVE)

    init {
//        permission()
        enableBluetooth()
    }

//    private fun permission() {
//
//    }

    private fun enableBluetooth() {
        if (!adapter.isEnabled) {
            // 方法一：請求打開藍牙
            Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE).also {
                activity.startActivityForResult(
                    it,
                    1
                )
            }

            // 方法二：半靜默打開藍牙
            // 低版本會靜默打開藍牙，高版本會請求打開藍牙
            // adapter.enable()
        }

        scan()
    }

    fun scan() {
        // 4.3 & 4.4
        // adapter.startLeScan(BluetoothAdapter.LeScanCallback)
        // adapter.stopLeScan(BluetoothAdapter.LeScanCallback)

        // 5.0以上
        val scanner = adapter.bluetoothLeScanner
//        scanner.startScan(null, scanSettings ,scanCallback)
    }
}