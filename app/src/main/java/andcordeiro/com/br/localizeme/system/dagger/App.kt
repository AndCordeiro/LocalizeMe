package andcordeiro.com.br.localizeme.system.dagger

import andcordeiro.com.br.localizeme.histories.maps.MapsModule
import andcordeiro.com.br.localizeme.system.retrofit.RetrofitApiModule
import android.app.Application

class App: Application() {

    private var component: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .mapsModule(MapsModule())
                .retrofitApiModule(RetrofitApiModule())
                .build()
    }

    fun getComponent(): ApplicationComponent {
        return component!!
    }
}