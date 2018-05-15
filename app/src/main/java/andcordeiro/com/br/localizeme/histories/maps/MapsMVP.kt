package andcordeiro.com.br.localizeme.histories.maps

import andcordeiro.com.br.localizeme.entities.Result
import android.content.Context
import android.location.Location
import com.google.android.gms.maps.GoogleMap
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

        fun map(): GoogleMap?
    }

    interface Presenter: Gps {

        fun setView(view: MapsMVP.View)

        fun rxUnsubscribe()

        fun loadPlaces(query: String?)

        fun setMyMarkerCreated(boolean: Boolean)

        fun getMyMarkerCreated(): Boolean

        fun createdMyMarker()
    }

    interface Model{
        fun loadPlacesAsync(query: String, location: Location?): Observable<Result>
    }

}