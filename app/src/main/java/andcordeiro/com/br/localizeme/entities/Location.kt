package andcordeiro.com.br.localizeme.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Location(lat: Double, lng: Double): Serializable {

    @SerializedName("lat")
    @Expose
    var lat: Double? = null
    @SerializedName("lng")
    @Expose
    var lng: Double? = null

    init {
        this.lat = lat
        this.lng = lng
    }

    override fun toString(): String {
        return "$lat, $lng"
    }

}