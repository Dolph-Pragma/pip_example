package com.example.pip_example

//import androidx.multidex.MultiDex;

import android.app.PictureInPictureParams
import android.graphics.Point
import android.os.Build
import android.util.Rational
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel


class MainActivity: FlutterActivity() {
    private val CHANNEL = "flutter.pipexample.com.channel"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
            .setMethodCallHandler { call: MethodCall, result: MethodChannel.Result ->
                if (call.method == "perreo") {
                    val d = windowManager
                        .defaultDisplay
                    val p = Point()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                        d.getSize(p)
                    }
                    val width = p.x
                    val height = p.y
                    var ratio: Rational? = null
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ratio = Rational(width*16, height*9)
                    }
                    var pipBuilder: PictureInPictureParams.Builder? = null
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        pipBuilder = PictureInPictureParams.Builder()
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        pipBuilder!!.setAspectRatio(ratio).build()
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        enterPictureInPictureMode(pipBuilder!!.build())
                    }
                } else {
                    result.notImplemented()
                }
            }
    }
}
