package andcordeiro.com.br.localizeme.system.retrofit

import andcordeiro.com.br.localizeme.entities.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MapsApiService{

    @GET("nearbysearch/json")
    fun getPlaces(@Query("location") location: String?, @Query("name") name: String?,
                  @Query("rankby") rankby: String?, @Query("language") language: String?,
                  @Query("key") key: String?): Call<Result>

}