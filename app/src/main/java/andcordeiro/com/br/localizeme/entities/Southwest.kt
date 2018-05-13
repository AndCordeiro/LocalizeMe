package andcordeiro.com.br.localizeme.entities

import java.io.Serializable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Southwest: Serializable {

    @SerializedName("lat")
    var lat: Double? = null
    @SerializedName("lng")
    var lng: Double? = null

    override fun toString(): String {
        return "Southwest(lat=$lat, lng=$lng)"
    }

}
