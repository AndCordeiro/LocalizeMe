package andcordeiro.com.br.localizeme.entities

import java.io.Serializable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Northeast: Serializable {

    @SerializedName("lat")
    @Expose
    private var lat: Double? = null
    @SerializedName("lng")
    @Expose
    private var lng: Double? = null

    override fun toString(): String {
        return "Northeast(lat=$lat, lng=$lng)"
    }
}
