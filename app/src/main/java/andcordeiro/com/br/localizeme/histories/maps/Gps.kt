package andcordeiro.com.br.localizeme.histories.maps

import andcordeiro.com.br.localizeme.entities.Location

interface Gps{

    fun isProviderEnabled(): Boolean

    fun startGps()

    fun stopGps()

    fun getLocation(): Location?
}