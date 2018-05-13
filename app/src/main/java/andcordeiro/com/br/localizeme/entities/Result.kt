package andcordeiro.com.br.localizeme.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


class Result: Serializable {

    @SerializedName("html_attributions")
    var htmlAttributions: List<Any>? = null
    @SerializedName("next_page_token")
    var nextPageToken: String? = null
    @SerializedName("results")
    var places: List<Place>? = null
    @SerializedName("status")
    var status: String? = null

    override fun toString(): String {
        return "Result(htmlAttributions=$htmlAttributions, " +
                "nextPageToken=$nextPageToken, places=$places, status=$status)"
    }
}