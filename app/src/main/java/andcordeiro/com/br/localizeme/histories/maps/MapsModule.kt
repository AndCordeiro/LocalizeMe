package andcordeiro.com.br.localizeme.histories.maps

import andcordeiro.com.br.localizeme.system.retrofit.MapsApiService
import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class MapsModule {

    @Provides
    fun provideMapsPresenter(mapsModel: MapsMVP.Model): MapsMVP.Presenter {
        return MapsPresenter(mapsModel)
    }

    @Provides
    fun provideMapsModel(api: MapsApiService, context: Context): MapsMVP.Model {
        return MapsModel(api, context)
    }
}