package andcordeiro.com.br.localizeme.histories.maps

import andcordeiro.com.br.localizeme.entities.Location
import andcordeiro.com.br.localizeme.entities.Result
import rx.Observable

interface MapsMVP{

    interface View{

        fun createMyMakerPosition(location: Location)

        fun updateMyMakerPosition(location: Location)

        fun setMakersPlaces(locations: List<Location>)

        fun showMessage(msg: String?)
    }

    interface Presenter{

        fun setView(view: MapsMVP.View)

        fun rxUnsubscribe()

        fun loadPlaces(query: String?)

        fun getPosition(): Location

    }

    interface Model{
        fun loadPlaces(query: String, location: Location): Observable<Result>
    }

}