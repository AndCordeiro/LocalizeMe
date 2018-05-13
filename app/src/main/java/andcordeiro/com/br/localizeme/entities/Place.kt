package andcordeiro.com.br.localizeme.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import com.google.gson.annotations.Expose



class Place: Serializable {

    @SerializedName("geometry")
    var geometry: Geometry? = null
    @SerializedName("icon")
    var icon: String? = null
    @SerializedName("id")
    var id: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("photos")
    var photos: List<Photo>? = null
    @SerializedName("place_id")
    var placeId: String? = null
    @SerializedName("reference")
    var reference: String? = null
    @SerializedName("scope")
    var scope: String? = null
    @SerializedName("types")
    var types: List<String>? = null
    @SerializedName("vicinity")
    var vicinity: String? = null
    @SerializedName("rating")
    var rating: Double? = null
    @SerializedName("opening_hours")
    var openingHours: OpeningHours? = null
    @SerializedName("price_level")
    var priceLevel: Int? = null

    override fun toString(): String {
        return "Place(geometry=$geometry, icon=$icon, id=$id, name=$name, " +
                "photos=$photos, placeId=$placeId, reference=$reference, " +
                "scope=$scope, types=$types, vicinity=$vicinity, rating=$rating, " +
                "openingHours=$openingHours, priceLevel=$priceLevel)"
    }
}