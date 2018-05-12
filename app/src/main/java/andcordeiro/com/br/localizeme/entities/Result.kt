package andcordeiro.com.br.localizeme.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Result {

    @SerializedName("html_attributions")
    @Expose
    private var htmlAttributions: List<Any>? = null
    @SerializedName("next_page_token")
    @Expose
    private var nextPageToken: String? = null
    @SerializedName("results")
    @Expose
    private var places: List<Place>? = null
    @SerializedName("status")
    @Expose
    private var status: String? = null

    override fun toString(): String {
        return "Result(htmlAttributions=$htmlAttributions, nextPageToken=$nextPageToken, places=$places, status=$status)"
    }
}