package andcordeiro.com.br.localizeme.histories.maps

import andcordeiro.com.br.localizeme.R
import andcordeiro.com.br.localizeme.entities.Location
import andcordeiro.com.br.localizeme.system.dagger.App
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject


class MapsActivity: AppCompatActivity(), MapsMVP.View, OnMapReadyCallback, SearchView.OnQueryTextListener {

    var map: GoogleMap? = null
    lateinit var toolbar: ActionBar
    var markerMe: Marker? = null

    @Inject
    internal lateinit var presenter: MapsMVP.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        (application as App).getComponent().inject(this)

        toolbar = supportActionBar!!
        val mapFragment = supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onMapReady(gMap: GoogleMap?) {
        Log.e("OnMap", "123")
        map = gMap
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.maps_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.item_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo((componentName)))
        searchView.queryHint = "Location"
        searchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onStart() {
        super.onStart()
        presenter.setView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.rxUnsubscribe()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.e("onQueryTextSubmit", query)
        presenter.loadPlaces(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun createMyMakerPosition(location: Location) {
        markerMe = map?.addMarker(MarkerOptions()
                .position(LatLng(presenter.getPosition().lat!!, presenter.getPosition().lng!!))
                .title("I'm here!"))
        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(markerMe?.position, 14.0F))
    }

    override fun updateMyMakerPosition(location: Location) {
        markerMe?.position = LatLng(location.lat!!, location.lng!!)
        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(markerMe?.position, 14.0F))
    }

    override fun setMakersPlaces(locations: List<Location>) {

    }

    override fun showMessage(msg: String?) {
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

}
