import com.howest.skyeye.ui.home.modals.WeatherUiState
import com.howest.skyeye.ui.home.modals.weather.WeatherViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WeatherViewModelTest {
    private lateinit var weatherViewModel: WeatherViewModel

    @Before
    fun setup() {
        weatherViewModel = WeatherViewModel()
    }

    @Test
    fun testSetWeatherItemNoWeather() = runTest {
        val weatherItem = "No weather"

        weatherViewModel.setWeatherItem(weatherItem)

        assertEquals(WeatherUiState(weatherItem), weatherViewModel.weatherUiState.value)
    }

    @Test
    fun testSetWeatherItemCloudCoverage() = runTest {
        val weatherItem = "Cloud coverage"

        weatherViewModel.setWeatherItem(weatherItem)

        assertEquals(WeatherUiState(weatherItem), weatherViewModel.weatherUiState.value)
    }

    @Test
    fun testSetWeatherItemRain() = runTest {
        val weatherItem = "Rain"

        weatherViewModel.setWeatherItem(weatherItem)

        assertEquals(WeatherUiState(weatherItem), weatherViewModel.weatherUiState.value)
    }
}