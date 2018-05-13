package andcordeiro.com.br.localizeme.system.dagger

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
        return application.applicationContext
    }

}