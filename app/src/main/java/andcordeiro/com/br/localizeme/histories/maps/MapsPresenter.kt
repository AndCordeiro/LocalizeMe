package andcordeiro.com.br.localizeme.histories.maps


import andcordeiro.com.br.localizeme.R
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers

class MapsPresenter(var model: MapsMVP.Model): MapsMVP.Presenter,
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
            subscription = model.loadPlacesAsync(query!!, getLocation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if(!it.status.equals(view!!.getContext()
                                        .getString(R.string.zero_results_search_returned))){
                            view?.clearMapsMakers()
                            view?.setMakersPlaces(it)
                        }else{
                            view?.shortShowMessage(view!!.getContext()
                                    .getString(R.string.search_no_returned_results))
                        }
                    }, {view?.shortShowMessage(view!!.getContext()
                            .getString(R.string.please_check_connection_internet))})
        }else{
            view?.shortShowMessage(view!!.getContext().getString(R.string.find_location_for_search))
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
            locationManager = view!!.getContext()
                    .getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                val location =
                        locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                this.setLocation(location)
                createdMyMarker()
                locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        0, 0F, this)
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

    override fun setLocation(location: Location?) {
        this.location = location
    }

    override fun createdMyMarker() {
        if(!myMarkerCreated && view!!.map() != null && getLocation() != null){
            view?.createMyMakerPosition(this.location!!)
        }
    }

    override fun onLocationChanged(location: android.location.Location) {
        this.location = location
        if(myMarkerCreated){
            view?.updateMyMakerPosition(this.location!!)
        }else{
            createdMyMarker()
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

    override fun onProviderEnabled(provider: String?) {}

    override fun onProviderDisabled(provider: String?) {
        if (!(view?.getContext() as Activity).isFinishing) {
            startGps()
        }
    }
}