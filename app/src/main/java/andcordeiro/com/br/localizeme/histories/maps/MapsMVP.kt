package andcordeiro.com.br.localizeme.histories.maps


import andcordeiro.com.br.localizeme.entities.Result
import android.content.Context
import android.location.Location
import rx.Observable

interface MapsMVP{

    interface View{

        fun createMyMakerPosition(location: Location?)

        fun updateMyMakerPosition(location: Location?)

        fun setMakersPlaces(result: Result)

        fun shortShowMessage(msg: String?)

        fun longShowMessage(msg: String?)

        fun requestProviderEnable()

        fun clearMapsMakers()

        fun getContext(): Context
    }

    interface Presenter: Gps {

        fun setView(view: MapsMVP.View)

        fun rxUnsubscribe()

        fun loadPlaces(query: String?)

        fun setMyMarkerCreated(boolean: Boolean)

        fun getMyMarkerCreated(): Boolean

    }

    interface Model{
        fun loadPlacesAsync(query: String, location: Location?): Observable<Result>
    }

}