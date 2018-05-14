package andcordeiro.com.br.localizeme.system.receiver

import andcordeiro.com.br.localizeme.R
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import android.support.v7.app.AlertDialog

class GpsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action!!.matches("android.location.PROVIDERS_CHANGED".toRegex())) {
            val manager = context?.getSystemService(Context.LOCATION_SERVICE)
                    as LocationManager
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                try {
                    AlertDialog.Builder(context)
                            .setTitle(context.getString(R.string.request_gps_enable_title))
                            .setMessage(context.getString(R.string.request_gps_enable_msg))
                            .setPositiveButton(context.getString(R.string.request_gps_enable_button),
                                    { _, _ -> context.startActivity(
                                            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) })
                            .create().show()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}