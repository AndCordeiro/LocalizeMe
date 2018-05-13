package andcordeiro.com.br.localizeme.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Geometry: Serializable {

    @SerializedName("location")
    var location: Location? = null
    @SerializedName("viewport")
    var viewport: Viewport? = null

    override fun toString(): String {
        return "Geometry(location=$location, viewport=$viewport)"
    }
}