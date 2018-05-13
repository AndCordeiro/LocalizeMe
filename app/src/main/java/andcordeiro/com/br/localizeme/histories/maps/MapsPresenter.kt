package andcordeiro.com.br.localizeme.histories.maps


import andcordeiro.com.br.localizeme.entities.Location
import andcordeiro.com.br.localizeme.system.extensions.isConnected
import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers

class MapsPresenter(var model: MapsMVP.Model, var context: Context): MapsMVP.Presenter,
        LocationListener {

    private var view: MapsMVP.View? = null
    private var subscription: Subscription? = null
    private var locationManager: LocationManager? = null
    private var location: Location? = null
    private var myMarkerCreated: Boolean = false

    override fun setView(view: MapsMVP.View) {
        this.view = view
    }

    override fun loadPlaces(query: String?) {
        if(myMarkerCreated){
            if(isConnected(context)){
                subscription = model.loadPlaces(query!!, getLocation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({view?.clearMapsMakers(); view?.setMakersPlaces(it)},
                                {view?.shortShowMessage("Deu erro")})
            }else{
                view?.shortShowMessage("Please connect Internet to search!")
            }
        }else{
            view?.shortShowMessage("Please wait until you find your location!")
        }
    }

    override fun rxUnsubscribe() {
        if (subscription != null && !subscription!!.isUnsubscribed) {
                subscription!!.unsubscribe()
        }
    }

    override fun setMyMarkerCreated(boolean: Boolean) {
        myMarkerCreated = boolean
    }

    override fun getMyMarkerCreated(): Boolean = myMarkerCreated

    override fun isProviderEnabled(): Boolean = (locationManager != null &&
            locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER))

    override fun startGps() {
        try {
            locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                val location =
                        locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if(location != null){
                    this.location = Location(location.latitude, location.longitude)
                }
                locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        1000, 0.001F, this)
            } else {
                view?.requestProviderEnable()
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    override fun stopGps() {
        if (locationManager != null) {
            try {
                locationManager!!.removeUpdates(this)
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }

    override fun getLocation(): Location? = location


    override fun onLocationChanged(location: android.location.Location) {
        this.location = Location(location.latitude, location.longitude)
        if(myMarkerCreated){
            view?.updateMyMakerPosition(this.location!!)
        }else{
            view?.createMyMakerPosition(this.location!!)
            myMarkerCreated = true
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

    override fun onProviderEnabled(provider: String?) {}

    override fun onProviderDisabled(provider: String?) {}

}