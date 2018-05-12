package andcordeiro.com.br.localizeme.entities

import java.io.Serializable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class OpeningHours: Serializable {

    @SerializedName("open_now")
    @Expose
    private var openNow: Boolean? = null
    @SerializedName("weekday_text")
    @Expose
    private var weekdayText: List<Any>? = null

    override fun toString(): String {
        return "OpeningHours(openNow=$openNow, weekdayText=$weekdayText)"
    }
}