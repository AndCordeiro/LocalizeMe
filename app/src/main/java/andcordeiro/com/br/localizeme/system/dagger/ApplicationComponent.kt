package andcordeiro.com.br.localizeme.system.dagger

import andcordeiro.com.br.localizeme.histories.maps.MapsActivity
import andcordeiro.com.br.localizeme.system.retrofit.RetrofitApiModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, RetrofitApiModule::class))
interface ApplicationComponent {

    fun inject(target: MapsActivity)
}