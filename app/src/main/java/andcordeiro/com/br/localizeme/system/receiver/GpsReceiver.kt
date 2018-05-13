package andcordeiro.com.br.localizeme.system.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.widget.Toast

class GpsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action!!.matches("android.location.PROVIDERS_CHANGED".toRegex())) {
            val manager = context?.getSystemService(Context.LOCATION_SERVICE)
                    as LocationManager
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(context, "Please, enable location to capture position!",
                        Toast.LENGTH_SHORT).show()
            }
        }
    }
}