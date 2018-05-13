package andcordeiro.com.br.localizeme.entities

import java.io.Serializable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Photo: Serializable {

    @SerializedName("height")
    var height: Int? = null
    @SerializedName("html_attributions")
    var htmlAttributions: List<String>? = null
    @SerializedName("photo_reference")
    var photoReference: String? = null
    @SerializedName("width")
    var width: Int? = null

    override fun toString(): String {
        return "Photo(height=$height, htmlAttributions=$htmlAttributions, " +
                "photoReference=$photoReference, width=$width)"
    }
}