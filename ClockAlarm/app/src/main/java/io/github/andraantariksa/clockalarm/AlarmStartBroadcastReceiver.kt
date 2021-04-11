package io.github.andraantariksa.clockalarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.*

class AlarmStartBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        createNotificationChannel(context)

//        val date = intent.getSerializableExtra("date") as Date
//        val calendar = Calendar.getInstance()
//        calendar.time = date

//         ${calendar.get(Calendar.HOUR)}:${calendar.get(Calendar.MINUTE)}
        Toast.makeText(context, "Scheduled alarm", Toast.LENGTH_LONG).show()

        Log.d("zzzzzz", "start service")
        context.startService(Intent(context, AlarmRingtoneService::class.java))
        Log.d("zzzzzz", "started service")

        val alarmIntent = Intent(context, AlarmStopBroadcastReceiver::class.java)
        val alarmPendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0)

        val builder =
            NotificationCompat.Builder(context, context.getString(R.string.channel_id_alarm))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Alarm Timeout")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setOngoing(true)
                .addAction(R.drawable.ic_launcher_foreground, "Stop", alarmPendingIntent)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(0, builder.build())
        }
    }

    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(context.getString(R.string.channel_id_alarm), name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}