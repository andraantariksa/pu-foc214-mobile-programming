package io.github.andraantariksa.sos

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.NonNull

class GPSReceiver(private val context: Context): LocationListener {
    var longitude: Double = 0.0
    var latitude: Double = 0.0

    override fun onLocationChanged(@NonNull location: Location) {
        longitude = location.longitude
        latitude = location.latitude
    }

    override fun onProviderDisabled(@NonNull provider: String) {
//        super.onProviderDisabled(provider)
        Toast.makeText(
            context,
            "GPS disabled",
            Toast.LENGTH_LONG).show()
    }

    override fun onProviderEnabled(@NonNull provider: String) {
//        super.onProviderEnabled(provider)
        Toast.makeText(
            context,
            "GPS enabled",
            Toast.LENGTH_LONG).show()
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        super.onStatusChanged(provider, status, extras)
    }
}