package com.puldroid.hardwaresensors

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var sm: SensorManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensorList = sm.getSensorList(Sensor.TYPE_ALL)

        for (sensor in sensorList) {
            Log.d(
                    "SENSOR", """
                        ${sensor.vendor}
                        ${sensor.name}
                    """.trimIndent()
            )
        }

        val accelSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val sensorListner = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            }

            override fun onSensorChanged(event: SensorEvent) {
                event.values.let {
                    val bgColor = acceltoColor(it[0], it[1], it[2])
                    root.setBackgroundColor(bgColor)
                }
            }

        }
        sm.registerListener(sensorListner,
                accelSensor,
                100 * 100)

    }

    private fun acceltoColor(fl: Float, fl1: Float, fl2: Float): Int {
        val R = (((fl + 12) / 24) * 255).toInt()
        val G = (((fl1 + 12) / 24) * 255).toInt()
        val B = (((fl2 + 12) / 24) * 255).toInt()

        return Color.rgb(R, G, B)

    }
}
