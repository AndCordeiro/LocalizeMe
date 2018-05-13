package andcordeiro.com.br.localizeme.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Viewport: Serializable {

    @SerializedName("northeast")
    var northeast: Northeast? = null
    @SerializedName("southwest")
    var southwest: Southwest? = null

    override fun toString(): String {
        return "Viewport(northeast=$northeast, southwest=$southwest)"
    }
}
