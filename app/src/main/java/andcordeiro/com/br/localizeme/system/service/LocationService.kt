package andcordeiro.com.br.localizeme.system.service

import andcordeiro.com.br.localizeme.R
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AlertDialog
import android.util.Log

class LocationService(private val context: Context): LocationListener{

    private val TAG = LocationService::class.java.getSimpleName()
    private var locationManager: LocationManager? = null
    private var location: Location? = null
    private var alertDialog: AlertDialog? = null

    fun isProviderEnabled(): Boolean {
        return locationManager != null && locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    fun start(minTime: Long, minDistance: Float) {
        try {
            locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, this)
            } else {
                requestProviderEnable()
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }

    }

    fun requestProviderEnable() {
        if (alertDialog == null || !alertDialog!!.isShowing) {
            alertDialog = AlertDialog.Builder(context)
                    .setCancelable(false)
                    .setTitle(context.getString(R.string.request_gps_enable_title))
                    .setMessage(context.getString(R.string.request_gps_enable_msg))
                    .setInverseBackgroundForced(true)
                    .setPositiveButton(context.getString(R.string.request_gps_enable_button)
                    ) { dialog, which -> context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
                    .setCancelable(false)
                    .create()
            try {
                alertDialog?.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun stop() {
        if (locationManager != null) {
            try {
                locationManager?.removeUpdates(this)
            } catch (e: SecurityException) {
                e.printStackTrace()
            }

        }
    }

    fun getLocation(): Location {
        return this.location!!
    }

    override fun onLocationChanged(location: Location?) {
        Log.d(TAG, "onLocationChanged: Location: " + location?.getLatitude() + "/" + location?.getLongitude() + "/" + location?.getAccuracy())
        this.location = location
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderEnabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProviderDisabled(provider: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    interface Listener {
        fun onLocationChanged(location: Location)
    }
}