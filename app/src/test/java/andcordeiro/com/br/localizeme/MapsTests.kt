package andcordeiro.com.br.localizeme

import andcordeiro.com.br.localizeme.histories.maps.MapsMVP
import andcordeiro.com.br.localizeme.histories.maps.MapsPresenter
import android.content.Context
import android.location.Location
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class MapsTests {

    @Mock
    internal var mockMapsModel: MapsMVP.Model? = null
    @Mock
    internal var mockMapsView: MapsMVP.View? = null
    @Mock
    internal var location: Location? = null
    @Mock
    internal var context: Context? = null
    var mapsPresenter: MapsPresenter? = null

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mapsPresenter = MapsPresenter(mockMapsModel!!)
    }

    @Test
    fun neverCallsPresenterModelMethodTest() {
        mapsPresenter?.loadPlaces("Farmacia")
        verify(mockMapsModel, never())!!.loadPlacesAsync("Farmacia", location)
    }

    @Test
    fun updateMyMarkerPositionTest() {
        mapsPresenter?.setLocation(location)
        mapsPresenter?.setView(mockMapsView!!)
        mapsPresenter?.setMyMarkerCreated(true)
        mapsPresenter?.onLocationChanged(location!!)
        verify(mockMapsView, times(1))!!.updateMyMakerPosition(location)
    }

    @Test
    fun checkInitMyMarkerCreatedTest() {
        assertEquals(mapsPresenter?.getMyMarkerCreated(), false)
    }

    @Test
    fun checkMyMarkerCreatedTest() {
        mapsPresenter?.setMyMarkerCreated(true)
        assertEquals(mapsPresenter?.getMyMarkerCreated(), true)
    }


}
