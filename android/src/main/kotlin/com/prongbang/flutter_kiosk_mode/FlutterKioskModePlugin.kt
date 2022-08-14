package com.prongbang.flutter_kiosk_mode

import android.app.Activity
import android.util.Log
import androidx.annotation.NonNull
import com.prongbang.kiosk.AndroidDeviceAdminReceiver
import com.prongbang.kiosk.AndroidDevicePolicyManager
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

class FlutterKioskModePlugin : FlutterPlugin, MethodCallHandler, ActivityAware {

    private var activity: Activity? = null
    private var androidDevicePolicyManager: AndroidDevicePolicyManager? = null

    private lateinit var channel: MethodChannel

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_kiosk_mode")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        Log.i("", "call.method: ${call.method}")
        when (call.method) {
            "start" -> startKiosk(result)
            "stop" -> stopKiosk(result)
            "owner" -> ownerApp(result)
            else -> result.notImplemented()
        }
    }

    private fun ownerApp(result: Result) {
        result.success(androidDevicePolicyManager?.isDeviceOwnerApp() ?: false)
    }

    private fun stopKiosk(result: Result) {
        try {
            androidDevicePolicyManager?.stopLockTask()
            result.success(true)
        } catch (e: Exception) {
            result.success(false)
        }
    }

    private fun startKiosk(result: Result) {
        try {
            androidDevicePolicyManager?.startLockTask()
            result.success(true)
        } catch (e: Exception) {
            result.success(false)
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity
        androidDevicePolicyManager = activity?.let {
            AndroidDevicePolicyManager(
                it,
                AndroidDeviceAdminReceiver.getComponentName(it)
            )
        }
    }

    override fun onDetachedFromActivity() {}

    override fun onDetachedFromActivityForConfigChanges() {}

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        activity = binding.activity
        androidDevicePolicyManager = activity?.let {
            AndroidDevicePolicyManager(
                it,
                AndroidDeviceAdminReceiver.getComponentName(it)
            )
        }
    }

}
