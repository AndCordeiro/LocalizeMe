package andcordeiro.com.br.localizeme.histories.maps

import andcordeiro.com.br.localizeme.R
import andcordeiro.com.br.localizeme.entities.Result
import andcordeiro.com.br.localizeme.system.extensions.makeObservable
import andcordeiro.com.br.localizeme.system.retrofit.MapsApiService
import android.content.Context
import android.location.Location
import rx.Observable
import rx.schedulers.Schedulers
import java.util.concurrent.Callable


class MapsModel(var api: MapsApiService, var context: Context): MapsMVP.Model {

    override fun loadPlacesAsync(query: String, location: Location?): Observable<Result> {
        return makeObservable(Callable {loadPlaces(query, location)})
                .subscribeOn(Schedulers.computation())
    }

    private fun loadPlaces(query: String, location: Location?): Result {
        var result = Result()
            val callback =
                    api.getPlaces(context.getString(R.string.location_parameters,
                            location!!.latitude.toString(), location.longitude.toString()),
                            query, context.getString(R.string.distance_search),
                            context.getString(R.string.language_search_pt), context.getString(R.string.google_maps_key)).execute()
            result = callback.body() as Result
        return result
    }
}