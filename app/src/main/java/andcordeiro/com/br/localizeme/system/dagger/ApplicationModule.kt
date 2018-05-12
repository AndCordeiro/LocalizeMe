package andcordeiro.com.br.localizeme.system.dagger

import andcordeiro.com.br.localizeme.histories.maps.MapsMVP
import andcordeiro.com.br.localizeme.histories.maps.MapsModel
import andcordeiro.com.br.localizeme.histories.maps.MapsPresenter
import andcordeiro.com.br.localizeme.system.retrofit.MapsApiService
import andcordeiro.com.br.localizeme.system.service.LocationService
import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ApplicationModule(var application: Application){

    @Provides
    @Singleton
    fun provideContext(): Context{
        return application
    }

    @Provides
    fun provideMapsPresenter(mapsModel: MapsMVP.Model, gps: LocationService): MapsMVP.Presenter {
        return MapsPresenter(mapsModel, gps)
    }

    @Provides
    fun provideMapsModel(api: MapsApiService, context: Context): MapsMVP.Model {
        return MapsModel(api, context)
    }

    @Provides
    fun provideLocationService(context: Context): LocationService {
        return LocationService(context)
    }

}