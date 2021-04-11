package io.github.andraantariksa.clockalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import io.github.andraantariksa.clockalarm.databinding.ActivityMainBinding
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var alarmManager: AlarmManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        setListener()
    }

    private fun setListener() {
        binding.buttonSetAlarm.setOnClickListener {
//            alarmManager.set()
            val dateFormatter = SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.US)

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()

            val calendar2 = Calendar.getInstance()
            calendar2.timeInMillis = System.currentTimeMillis()
            calendar2.set(Calendar.HOUR_OF_DAY, binding.timePickerAlarm.hour)
            calendar2.set(Calendar.MINUTE, binding.timePickerAlarm.minute)

            if (calendar2 < calendar) {
                calendar2.add(Calendar.HOUR, 24)
            }

            val date = calendar2.timeInMillis

            val alarmIntent = Intent(this, AlarmStartBroadcastReceiver::class.java)
            val alarmPendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0)

            alarmManager.setExact(AlarmManager.RTC, date, alarmPendingIntent)

            Snackbar.make(
                binding.constraintLayoutRoot,
                "Alarm created at ${dateFormatter.format(date)}",
                Snackbar.LENGTH_LONG
            ).show()
        }

        binding.buttonDeleteAlarm.setOnClickListener {
            Log.d("zzzzz", "Cancel")

            removeOngoingAlarm()

            Snackbar.make(binding.constraintLayoutRoot, "Alarm canceled", Snackbar.LENGTH_LONG)
                .show()
        }
    }

    private fun removeOngoingAlarm() {
        val alarmIntent = Intent(this, AlarmStartBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0)
        alarmManager.cancel(pendingIntent)

        stopService(Intent(this, AlarmRingtoneService::class.java))
    }
}