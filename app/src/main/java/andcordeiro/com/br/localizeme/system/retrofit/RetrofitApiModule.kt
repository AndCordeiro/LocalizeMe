package andcordeiro.com.br.localizeme.system.retrofit

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RetrofitApiModule {

    val baseUrl = "https://maps.googleapis.com/maps/api/place/"

    @Provides
    fun provideClient(): OkHttpClient{

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    fun provideRetrofit(baseUrl: String, client : OkHttpClient): Retrofit{
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    @Provides
    fun provideApiService(): MapsApiService{
        return provideRetrofit(baseUrl, provideClient()).create(MapsApiService::class.java)
    }

}