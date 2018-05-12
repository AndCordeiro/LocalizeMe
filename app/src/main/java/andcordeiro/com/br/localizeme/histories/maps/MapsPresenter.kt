package andcordeiro.com.br.localizeme.histories.maps


import andcordeiro.com.br.localizeme.entities.Location
import andcordeiro.com.br.localizeme.system.service.LocationService
import android.util.Log
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers


class MapsPresenter(var model: MapsMVP.Model, var gps: LocationService): MapsMVP.Presenter, LocationService.Listener {

    private var view: MapsMVP.View? = null
    private var subscription: Subscription? = null

    override fun setView(view: MapsMVP.View) {
        this.view = view
    }

    override fun loadPlaces(query: String?) {
        subscription = model.loadPlaces(query!!, Location(-23.2130963, -46.8145406))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({view?.showMessage("deu certo"); Log.e("TAG", it.toString())}, {view?.showMessage("Deu erro")})
    }

    override fun rxUnsubscribe() {
        if (subscription != null && !subscription!!.isUnsubscribed) {
                subscription!!.unsubscribe()
        }
    }

    override fun getPosition(): Location {
        return Location(gps.getLocation().latitude, gps.getLocation().longitude)
    }

    override fun onLocationChanged(location: android.location.Location) {
        view?.updateMyMakerPosition(Location(location.latitude, location.longitude))
    }
}