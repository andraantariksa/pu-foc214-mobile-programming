package io.github.andraantariksa.clockalarm

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings
import android.util.Log

class AlarmRingtoneService: Service() {
    private lateinit var player: MediaPlayer

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("zzzzzz", "playing")

        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI) //Settings.System.DEFAULT_RINGTONE_URI
        player.isLooping = true

        player.start()

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
    }
}