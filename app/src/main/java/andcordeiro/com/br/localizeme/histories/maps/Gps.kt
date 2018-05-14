package andcordeiro.com.br.localizeme.histories.maps

import android.location.Location


interface Gps{

    fun isProviderEnabled(): Boolean

    fun startGps()

    fun stopGps()

    fun getLocation(): Location?

    fun setLocation(location: Location?)
}