package com.prongbang.flutter_kiosk_mode

import AndroidDeviceAdminReceiver
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.NonNull
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

    @SuppressLint("ObsoleteSdkInt")
    private fun isInLockTaskMode(): Boolean {
        val am = activity?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            am.lockTaskModeState != ActivityManager.LOCK_TASK_MODE_NONE
        } else {
            @Suppress("DEPRECATION")
            am.isInLockTaskMode
        }
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        Log.i("", "call.method: ${call.method}")
        when (call.method) {
            "start" -> startKiosk(result)
            "stop" -> stopKiosk(result)
            "owner" -> ownerApp(result)
            "check" -> result.success(isInLockTaskMode())
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
