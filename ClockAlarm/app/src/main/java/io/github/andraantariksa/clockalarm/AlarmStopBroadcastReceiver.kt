package io.github.andraantariksa.clockalarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.core.app.NotificationManagerCompat

class AlarmStopBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        context.stopService(Intent(context, AlarmRingtoneService::class.java))
        NotificationManagerCompat.from(context).cancel(0)
    }
}