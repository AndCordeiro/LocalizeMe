package andcordeiro.com.br.localizeme.system.extensions

import android.content.Context
import android.net.ConnectivityManager

fun isConnected(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager
    val netInfo = cm.activeNetworkInfo
    return netInfo != null && netInfo.isConnected
}