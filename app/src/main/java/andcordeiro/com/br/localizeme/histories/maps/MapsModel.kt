package andcordeiro.com.br.localizeme.histories.maps

import andcordeiro.com.br.localizeme.R
import andcordeiro.com.br.localizeme.entities.Location
import andcordeiro.com.br.localizeme.entities.Result
import andcordeiro.com.br.localizeme.system.extensions.makeObservable
import andcordeiro.com.br.localizeme.system.retrofit.MapsApiService
import android.content.Context
import rx.Observable
import rx.schedulers.Schedulers
import java.util.concurrent.Callable


class MapsModel(var api: MapsApiService, var context: Context): MapsMVP.Model {

    override fun loadPlaces(query: String, location: Location?): Observable<Result> {
        return makeObservable(Callable {load(query, location)})
                .subscribeOn(Schedulers.computation())
    }

    private fun load(query: String, location: Location?): Result {
        var result = Result()
            val callback =
                    api.getPlaces(location.toString(), query, "distance", "pt-BR",
                    context.getString(R.string.google_maps_key)).execute()
            result = callback.body() as Result
        return result
    }
}