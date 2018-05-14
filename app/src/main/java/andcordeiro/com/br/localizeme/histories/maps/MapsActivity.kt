package andcordeiro.com.br.localizeme.histories.maps

import andcordeiro.com.br.localizeme.R
import andcordeiro.com.br.localizeme.entities.Place
import andcordeiro.com.br.localizeme.entities.Result
import andcordeiro.com.br.localizeme.system.dagger.App
import andcordeiro.com.br.localizeme.system.extensions.isConnected
import andcordeiro.com.br.localizeme.system.receiver.GpsReceiver
import android.Manifest
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import javax.inject.Inject


class MapsActivity : AppCompatActivity(), MapsMVP.View, OnMapReadyCallback,
        SearchView.OnQueryTextListener, PermissionListener, GoogleMap.OnMarkerClickListener,
        SearchView.OnCloseListener{


    var map: GoogleMap? = null
    lateinit var toolbar: ActionBar
    var markerMe: Marker? = null
    private var alertDialog: AlertDialog? = null
    private var gpsReceiver: GpsReceiver? = null
    private var alertDialogPermission: AlertDialog? = null

    @Inject
    internal lateinit var presenter: MapsMVP.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        (application as App).getComponent().inject(this)

        toolbar = supportActionBar!!
        val mapFragment =
                supportFragmentManager.findFragmentById(R.id.maps) as SupportMapFragment
        mapFragment.getMapAsync(this)
        gpsReceiver = GpsReceiver()
        registerReceiver(gpsReceiver, IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION))
    }

    override fun onResume() {
        super.onResume()
        if(!isConnected(this)){
            longShowMessage(getString(R.string.message_connection_internet))
        }
        permissions()
    }

    private fun permissions() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(this)
                .check()
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) = presenter.startGps()


    override fun onPermissionRationaleShouldBeShown(
            permission: PermissionRequest?, token: PermissionToken?) {
        if (alertDialogPermission == null || !alertDialogPermission!!.isShowing) {
            alertDialogPermission = AlertDialog.Builder(this@MapsActivity)
                    .setTitle(android.R.string.dialog_alert_title)
                    .setMessage(getString(R.string.message_premission_to_continue))
                    .setNegativeButton(android.R.string.cancel, { dialogInterface, _ ->
                        dialogInterface.dismiss()
                        token?.cancelPermissionRequest()
                        finish()
                    })
                    .setPositiveButton(android.R.string.ok, { dialogInterface, _ ->
                        dialogInterface.dismiss()
                        token?.continuePermissionRequest()
                    })
                    .setCancelable(false)
                    .show()
        }
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        if (response!!.isPermanentlyDenied) {
            shortShowMessage(getString(R.string.message_permission_to_use_application))
            finish()
        }
    }

    override fun onMapReady(gMap: GoogleMap?) {
        map = gMap
        if (presenter.getLocation() != null && !presenter.getMyMarkerCreated()) {
            createMyMakerPosition(presenter.getLocation()!!)
            presenter.setMyMarkerCreated(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.maps_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.item_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo((componentName)))
        searchView.queryHint = getString(R.string.place_search_hint)
        searchView.setOnQueryTextListener(this)
        searchView.setOnCloseListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onStart() {
        super.onStart()
        presenter.setView(this)
    }

    override fun getContext(): Context = this

    override fun onStop() {
        super.onStop()
        presenter.rxUnsubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(gpsReceiver)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        presenter.loadPlaces(query)
        return true
    }

    override fun onClose(): Boolean {
        if(presenter.getLocation() != null){
            clearMapsMakers()
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean = false

    override fun createMyMakerPosition(location: Location?) {
        markerMe = map?.addMarker(MarkerOptions()
                .position(LatLng(presenter.getLocation()!!.latitude,
                        presenter.getLocation()!!.longitude))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title(getString(R.string.im_here)))

        map?.setOnMarkerClickListener(this)
        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(markerMe?.position, 14.0F))
    }

    override fun updateMyMakerPosition(location: Location?) {
        markerMe?.position = LatLng(location!!.latitude, location.longitude)
    }

    override fun setMakersPlaces(result: Result) {
        val places: MutableList<Place> = result.places as MutableList<Place>
        places.forEach { place: Place ->
            if(places.size > 10 && places[10].equals(place)){
                return
            }
            map?.addMarker(MarkerOptions()
                    .position(LatLng(place.geometry!!.location!!.lat!!,
                            place.geometry!!.location!!.lng!!))
                    .title(place.name))!!
                    .setSnippet(place.vicinity)
        }
        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(markerMe?.position, 11.5F))
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        if(marker!!.equals(markerMe)){
            markerMe!!.showInfoWindow()
            map?.animateCamera(CameraUpdateFactory.newLatLngZoom(markerMe?.position, 14.0F))
            return true
        }
        return false
    }

    override fun clearMapsMakers() {
        map?.clear()
        createMyMakerPosition(presenter.getLocation())
    }

    override fun shortShowMessage(msg: String?) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    override fun longShowMessage(msg: String?) =
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()

    override fun requestProviderEnable() {
        if (alertDialog == null || !alertDialog!!.isShowing()) {
            alertDialog = AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle(this.getString(R.string.request_gps_enable_title))
                    .setMessage(this.getString(R.string.request_gps_enable_msg))
                    .setPositiveButton(this.getString(R.string.request_gps_enable_button),
                            { _, _ -> this.startActivity(
                                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) })
                    .create()
            try {
                alertDialog!!.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
