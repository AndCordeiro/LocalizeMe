package andcordeiro.com.br.localizeme.entities

import java.io.Serializable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Photo: Serializable {

    @SerializedName("height")
    @Expose
    private var height: Int? = null
    @SerializedName("html_attributions")
    @Expose
    private var htmlAttributions: List<String>? = null
    @SerializedName("photo_reference")
    @Expose
    private var photoReference: String? = null
    @SerializedName("width")
    @Expose
    private var width: Int? = null

    override fun toString(): String {
        return "Photo(height=$height, htmlAttributions=$htmlAttributions, photoReference=$photoReference, width=$width)"
    }
}