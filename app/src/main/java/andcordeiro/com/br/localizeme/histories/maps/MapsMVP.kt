package andcordeiro.com.br.localizeme.histories.maps

import andcordeiro.com.br.localizeme.entities.Location
import andcordeiro.com.br.localizeme.entities.Result
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
    }

    interface Presenter: Gps {

        fun setView(view: MapsMVP.View)

        fun rxUnsubscribe()

        fun loadPlaces(query: String?)

        fun setMyMarkerCreated(boolean: Boolean)

        fun getMyMarkerCreated(): Boolean

    }

    interface Model{
        fun loadPlaces(query: String, location: Location?): Observable<Result>
    }

}