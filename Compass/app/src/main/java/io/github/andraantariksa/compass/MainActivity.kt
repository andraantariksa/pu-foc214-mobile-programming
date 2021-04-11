package io.github.andraantariksa.compass

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.github.andraantariksa.compass.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SensorEventListener {
    var azimuth_angle = 0f
    private var compassSensorManager: SensorManager? = null
    var accelerometer: Sensor? = null
    var magnetometer: Sensor? = null
    var accel_read: FloatArray? = null
    var magnetic_read: FloatArray? = null

    lateinit var binding: ActivityMainBinding

    private var current_degree = 0f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        compassSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = compassSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magnetometer = compassSensorManager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        compassSensorManager!!.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
        compassSensorManager!!.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        super.onPause()
        compassSensorManager!!.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) accel_read = event.values
        if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) magnetic_read = event.values
        if (accel_read != null && magnetic_read != null) {
            val R = FloatArray(9)
            val I = FloatArray(9)
            val successfull_read = SensorManager.getRotationMatrix(R, I, accel_read, magnetic_read)
            if (successfull_read) {
                val orientation = FloatArray(3)
                SensorManager.getOrientation(R, orientation)
                azimuth_angle = orientation[0]
                val degrees = azimuth_angle * 180f / 3.14f
                val degreesInt = Math.round(degrees)
                binding.textViewCompass.setText(Integer.toString(degreesInt) + 0x00B0.toChar() + "to absolute north.")
                val rotate = RotateAnimation(
                    current_degree,
                    (-degreesInt).toFloat(),
                    Animation.RELATIVE_TO_SELF,
                    0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f
                )
                rotate.duration = 100
                rotate.fillAfter = true
                binding.imageViewCompass.startAnimation(rotate)
                current_degree = -degreesInt.toFloat()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}