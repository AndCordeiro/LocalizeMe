package andcordeiro.com.br.localizeme.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import com.google.gson.annotations.Expose



class Place: Serializable {

    @SerializedName("geometry")
    @Expose
    private var geometry: Geometry? = null
    @SerializedName("icon")
    @Expose
    private var icon: String? = null
    @SerializedName("id")
    @Expose
    private var id: String? = null
    @SerializedName("name")
    @Expose
    private var name: String? = null
    @SerializedName("photos")
    @Expose
    private var photos: List<Photo>? = null
    @SerializedName("place_id")
    @Expose
    private var placeId: String? = null
    @SerializedName("reference")
    @Expose
    private var reference: String? = null
    @SerializedName("scope")
    @Expose
    private var scope: String? = null
    @SerializedName("types")
    @Expose
    private var types: List<String>? = null
    @SerializedName("vicinity")
    @Expose
    private var vicinity: String? = null
    @SerializedName("rating")
    @Expose
    private var rating: Double? = null
    @SerializedName("opening_hours")
    @Expose
    private var openingHours: OpeningHours? = null
    @SerializedName("price_level")
    @Expose
    private var priceLevel: Int? = null

    override fun toString(): String {
        return "Place(geometry=$geometry, icon=$icon, id=$id, name=$name, photos=$photos, placeId=$placeId, reference=$reference, scope=$scope, types=$types, vicinity=$vicinity, rating=$rating, openingHours=$openingHours, priceLevel=$priceLevel)"
    }
}