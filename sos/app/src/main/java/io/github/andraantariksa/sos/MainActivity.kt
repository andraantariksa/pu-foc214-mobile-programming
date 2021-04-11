package io.github.andraantariksa.sos

import android.Manifest
import android.R
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import io.github.andraantariksa.sos.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var locationManager: LocationManager
    private lateinit var smsManager: SmsManager
    private val gpsReceiver = GPSReceiver(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        smsManager = SmsManager.getDefault()

        setupListener()
        setContentView(binding.root)
    }

    private fun setupListener() {
        val MY_PERMISSIONS_REQUEST_LOCATION = 99

        if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                AlertDialog
                        .Builder(this)
                        .setTitle("Permission title")
                        .setMessage("Permission message")
                        .setPositiveButton(R.string.ok, DialogInterface.OnClickListener {
                            dialogInterface, i -> ActivityCompat.requestPermissions(
                                    this@MainActivity,
                                    arrayOf(
                                            Manifest.permission.ACCESS_FINE_LOCATION,
                                            Manifest.permission.SEND_SMS),
                                    MY_PERMISSIONS_REQUEST_LOCATION)
                        })
                        .create()
                        .show()
            } else {
                ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.SEND_SMS),
                        MY_PERMISSIONS_REQUEST_LOCATION)
            }
        }
        Log.d("zzzzzzzzzzz", "accepted")
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000L,
                1.0f,
                gpsReceiver)
        binding.buttonSOS.setOnClickListener {
            try {
                smsManager.sendTextMessage(
                        "081343026955",
                        null,
                        "Please take me from longitude: ${gpsReceiver.longitude}, " +
                                "latitude: ${gpsReceiver.latitude}",
                        null,
                        null)
                Toast.makeText(
                        this,
                        "S.O.S message sent!",
                        Toast.LENGTH_LONG).show()
                Log.d("zzzzzzz", "BBBBBBBBBBBBBBB")
            } catch (e: Exception) {
                Toast.makeText(
                        this,
                        "Message failed. Error ${e.message}",
                        Toast.LENGTH_LONG).show()
                Log.d("zzzzzzz", "AAAAAAAAAAAAAA")
            }
        }
    }
}