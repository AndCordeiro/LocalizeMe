package andcordeiro.com.br.localizeme

import andcordeiro.com.br.localizeme.histories.maps.MapsMVP
import andcordeiro.com.br.localizeme.histories.maps.MapsPresenter
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

    var mapsPresenter: MapsPresenter? = null


    @Before
    fun setup() {

        MockitoAnnotations.initMocks(this)
        mapsPresenter = MapsPresenter(mockMapsModel!!)
    }

    @Test
    fun neverCallsPresenterModelMethodTest() {
        mapsPresenter?.loadPlaces("Teste")
        verify(mockMapsModel, never())!!.loadPlacesAsync("Teste", location)
        mapsPresenter?.setView(mockMapsView!!)
        mapsPresenter?.loadPlaces("Teste")
        verify(mockMapsView, times(1))!!
                .shortShowMessage("Please wait until you find your location!")
    }

    @Test
    fun createdMyMarkerPositionTest() {
        mapsPresenter?.setLocation(location)
        mapsPresenter?.setView(mockMapsView!!)
        mapsPresenter?.onLocationChanged(location!!)
        mapsPresenter?.setMyMarkerCreated(true)
        verify(mockMapsView, times(1))?.createMyMakerPosition(location)
    }

    @Test
    fun updateMyMarkerPositionTest() {
        mapsPresenter?.setLocation(location)
        mapsPresenter?.setView(mockMapsView!!)
        mapsPresenter?.onLocationChanged(location!!)
        mapsPresenter?.setMyMarkerCreated(false)
        verify(mockMapsView, times(1))!!.createMyMakerPosition(location)
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
