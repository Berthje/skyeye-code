package com.howest.skyeye.test

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onParent
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.howest.skyeye.ui.SkyEyeNavHost
import com.howest.skyeye.ui.aircraft.SeeAllAircraftTypesDestination
import com.howest.skyeye.ui.aircraft.detail.AircraftDetailDestination
import com.howest.skyeye.ui.airport.SeeAllAirportsDestination
import com.howest.skyeye.ui.airport.detail.AirportDetailDestination
import com.howest.skyeye.ui.camera.CameraDestination
import com.howest.skyeye.ui.home.HomeDestination
import com.howest.skyeye.ui.settings.SettingsDestination
import com.howest.skyeye.ui.settings.about.AboutDestination
import com.howest.skyeye.ui.settings.account.AccountDestination
import com.howest.skyeye.ui.settings.appearance.AppearanceDestination
import com.howest.skyeye.ui.settings.support.SupportDestination
import com.howest.skyeye.ui.user.ForgotPasswordDestination
import com.howest.skyeye.ui.user.LoginDestination
import com.howest.skyeye.ui.user.RegisterDestination
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

fun NavController.assertCurrentRouteName(expectedRouteName: String) {
    Assert.assertEquals(expectedRouteName, currentBackStackEntry?.destination?.route)
}

/**
 * Helper function to find UI component using a resource string. This can be done with the Context.getString() method.
 *
 * The [onNodeWithText] finder provided by compose ui test API, doesn't support usage of
 * string resource id to find the semantics node. This extension function accesses string resource
 * using underlying activity property and passes it to [onNodeWithText] function as argument and
 * returns the [SemanticsNodeInteraction] object.
 *
 *  More information:
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.onNodeWithStringId(
    @StringRes id: Int
): SemanticsNodeInteraction = onNodeWithText(activity.getString(id))


class SkyEyeScreenNavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController


    @Before
    fun setupCupcakeNavHost() {
        composeTestRule.setContent {
            val context = LocalContext.current
            navController = TestNavHostController(context).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            SkyEyeNavHost(navController = navController, context = context)
        }
    }

    @Test
    fun skyEyeNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(HomeDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickMenuIconTopBar_opensDrawer() {
        val menuButton = composeTestRule.onNodeWithContentDescription("menu")
        menuButton.performClick()
        composeTestRule.onNodeWithText("See all airports").assertExists()
        composeTestRule.onNodeWithText("See all aircraft types").assertExists()
        composeTestRule.onNodeWithText("Search airplanes with AR").assertExists()
    }

    @Test
    fun skyEyeNavHost_clickAccountIconTopBar_opensAccountScreen() {
        val accountButton = composeTestRule.onNodeWithContentDescription("avatar")
        accountButton.performClick()
        navController.assertCurrentRouteName(LoginDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickWeatherIconBottomBar_showsWeatherModal() {
        val weatherButton = composeTestRule.onNodeWithText("Weather")
        weatherButton.performClick()
        composeTestRule.onNodeWithText("Global IR Satellite provides cloud cover displayed on the map.").assertExists()
    }

    @Test
    fun skyEyeNavHost_clickMapTypeIconBottomBar_showsMapTypeModal() {
        val mapTypeButton = composeTestRule.onNodeWithText("MapType")
        mapTypeButton.performClick()
        composeTestRule.onNodeWithText("Normal").assertExists()
    }

    @Test
    fun skyEyeNavHost_clickFiltersIconBottomBar_showsFilterModal() {
        val filtersButton = composeTestRule.onNodeWithText("Filters")
        filtersButton.performClick()
        composeTestRule.onNodeWithText("Min. altitude").assertExists()
        composeTestRule.onNodeWithText("Min. airspeed").assertExists()
    }

    @Test
    fun skyEyeNavHost_clickSearchAirplanesWithARInDrawer_opensCameraScreen() {
        val menuButton = composeTestRule.onNodeWithContentDescription("menu")
        menuButton.performClick()

        val searchAirPlanesWithARButton = composeTestRule.onNodeWithText("Search airplanes with AR")
        searchAirPlanesWithARButton.performClick()

        navController.assertCurrentRouteName(CameraDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickSettingsInDrawer_opensSettingsScreen() {
        val menuButton = composeTestRule.onNodeWithContentDescription("menu")
        menuButton.performClick()

        val settingsButton = composeTestRule.onNodeWithContentDescription("Settings")
        settingsButton.performClick()

        navController.assertCurrentRouteName(SettingsDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickSeeAllAirportsInDrawer_opensAirportsScreen() {
        val menuButton = composeTestRule.onNodeWithContentDescription("menu")
        menuButton.performClick()

        val seeAllAirportsButton = composeTestRule.onNodeWithText("See all airports")
        seeAllAirportsButton.performClick()

        navController.assertCurrentRouteName(SeeAllAirportsDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickSeeAllAircraftTypesInDrawer_opensAircraftTypesScreen() {
        val menuButton = composeTestRule.onNodeWithContentDescription("menu")
        menuButton.performClick()

        val aircraftTypesButton = composeTestRule.onNodeWithText("See all aircraft types")
        aircraftTypesButton.performClick()

        navController.assertCurrentRouteName(SeeAllAircraftTypesDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickSearchAirplanesWithARInBottomBar_opensCameraScreen() {
        val searchAirPlanesWithARButton = composeTestRule.onNodeWithText("Camera")
        searchAirPlanesWithARButton.performClick()

        navController.assertCurrentRouteName(CameraDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickSettingsInBottomBar_opensCameraScreen() {
        val settingsButton = composeTestRule.onNodeWithContentDescription("Settings")
        settingsButton.performClick()

        navController.assertCurrentRouteName(SettingsDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickLoginOrSignUpInSettingsScreen_opensLoginScreen() {
        val settingsButton = composeTestRule.onNodeWithContentDescription("Settings")
        settingsButton.performClick()

        val loginOrSignUpButton = composeTestRule.onNodeWithText("Login or Sign up")
        loginOrSignUpButton.performClick()

        navController.assertCurrentRouteName(LoginDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickAppearanceInSettingsScreen_opensAppearanceScreen() {
        val settingsButton = composeTestRule.onNodeWithContentDescription("Settings")
        settingsButton.performClick()

        val appearanceButton = composeTestRule.onNodeWithText("Appearance")
        appearanceButton.performClick()

        navController.assertCurrentRouteName(AppearanceDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickSupportInSettingsScreen_opensSupportScreen() {
        val settingsButton = composeTestRule.onNodeWithContentDescription("Settings")
        settingsButton.performClick()

        val supportButton = composeTestRule.onNodeWithText("Support")
        supportButton.performClick()

        navController.assertCurrentRouteName(SupportDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickAboutInSettingsScreen_opensAboutScreen() {
        val settingsButton = composeTestRule.onNodeWithContentDescription("Settings")
        settingsButton.performClick()

        val aboutButton = composeTestRule.onNodeWithText("About")
        aboutButton.performClick()

        navController.assertCurrentRouteName(AboutDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickSignUpInLoginScreen_opensSignUpScreen() {
        val menuButton = composeTestRule.onNodeWithContentDescription("menu")
        menuButton.performClick()

        val loginButton = composeTestRule.onNodeWithText("Log in")
        loginButton.performClick()

        val signUpButton = composeTestRule.onNodeWithText("Sign up")
        signUpButton.performClick()

        navController.assertCurrentRouteName(RegisterDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickLogInInSignUpScreen_opensLoginScreen() {
        val menuButton = composeTestRule.onNodeWithContentDescription("menu")
        menuButton.performClick()

        val signUpButton = composeTestRule.onNodeWithText("Register")
        signUpButton.performClick()

        val loginButton = composeTestRule.onNodeWithText("Sign in")
        loginButton.performClick()

        navController.assertCurrentRouteName(LoginDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickForgotPasswordInLoginScreen_opensForgotPasswordScreen() {
        val loginButton = composeTestRule.onNodeWithText("Log in")
        loginButton.performClick()

        val forgotPasswordButton = composeTestRule.onNodeWithText("Forgot password?")
        forgotPasswordButton.performClick()

        navController.assertCurrentRouteName(ForgotPasswordDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickAircraftItemInSeeAllAircraftsScreen_opensAircraftDetailsScreen() {
        val menuButton = composeTestRule.onNodeWithContentDescription("menu")
        menuButton.performClick()

        val aircraftTypesButton = composeTestRule.onNodeWithText("See all aircraft types")
        aircraftTypesButton.performClick()

        composeTestRule.onNodeWithText("A220").performClick()
        composeTestRule.onNodeWithText("A220-100").performClick()

        navController.assertCurrentRouteName(AircraftDetailDestination.routeWithArgs)
    }

    @Test
    fun skyEyeNavHost_clickAirportItemInSeeAllAirportsScreen_opensAirportDetailsScreen() {
        val menuButton = composeTestRule.onNodeWithContentDescription("menu")
        menuButton.performClick()

        val seeAllAirportsButton = composeTestRule.onNodeWithText("See all airports")
        seeAllAirportsButton.performClick()

        composeTestRule.onNodeWithText("China").performClick()
        composeTestRule.onNodeWithText("Hongyuan Airport").performClick()

        navController.assertCurrentRouteName(AirportDetailDestination.routeWithArgs)
    }

    @Test
    fun skyEyeNavHost_clickBackArrowInSettingsScreen_opensHomeScreen() {
        val settingsButton = composeTestRule.onNodeWithContentDescription("Settings")
        settingsButton.performClick()

        val backArrowButton = composeTestRule.onNodeWithContentDescription("Back")
        backArrowButton.performClick()

        navController.assertCurrentRouteName(HomeDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickBackArrowInAircraftDetailScreen_opensSeeAllAircraftsScreen() {
        val menuButton = composeTestRule.onNodeWithContentDescription("menu")
        menuButton.performClick()

        val aircraftTypesButton = composeTestRule.onNodeWithText("See all aircraft types")
        aircraftTypesButton.performClick()

        composeTestRule.onNodeWithText("A220").performClick()
        composeTestRule.onNodeWithText("A220-100").performClick()

        val backArrowButton = composeTestRule.onNodeWithContentDescription("Back")
        backArrowButton.performClick()

        navController.assertCurrentRouteName(SeeAllAircraftTypesDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickBackArrowInAirportDetailScreen_opensSeeAllAirportsScreen() {
        val menuButton = composeTestRule.onNodeWithContentDescription("menu")
        menuButton.performClick()

        val seeAllAirportsButton = composeTestRule.onNodeWithText("See all airports")
        seeAllAirportsButton.performClick()

        composeTestRule.onNodeWithText("China").performClick()
        composeTestRule.onNodeWithText("Hongyuan Airport").performClick()

        val backArrowButton = composeTestRule.onNodeWithContentDescription("Back")
        backArrowButton.performClick()

        navController.assertCurrentRouteName(SeeAllAirportsDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickBackArrowInAboutScreen_opensSettingsScreen() {
        val settingsButton = composeTestRule.onNodeWithContentDescription("Settings")
        settingsButton.performClick()

        val aboutButton = composeTestRule.onNodeWithText("About")
        aboutButton.performClick()

        val backArrowButton = composeTestRule.onNodeWithContentDescription("Back")
        backArrowButton.performClick()

        navController.assertCurrentRouteName(SettingsDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickBackArrowInAppearanceScreen_opensSettingsScreen() {
        val settingsButton = composeTestRule.onNodeWithContentDescription("Settings")
        settingsButton.performClick()

        val appearanceButton = composeTestRule.onNodeWithText("Appearance")
        appearanceButton.performClick()

        val backArrowButton = composeTestRule.onNodeWithContentDescription("Back")
        backArrowButton.performClick()

        navController.assertCurrentRouteName(SettingsDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickBackArrowInSupportScreen_opensSettingsScreen() {
        val settingsButton = composeTestRule.onNodeWithContentDescription("Settings")
        settingsButton.performClick()

        val supportButton = composeTestRule.onNodeWithText("Support")
        supportButton.performClick()

        val backArrowButton = composeTestRule.onNodeWithContentDescription("Back")
        backArrowButton.performClick()

        navController.assertCurrentRouteName(SettingsDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickBackArrowInSeeAllAircraftsScreen_opensHomeScreen() {
        val menuButton = composeTestRule.onNodeWithContentDescription("menu")
        menuButton.performClick()

        val aircraftTypesButton = composeTestRule.onNodeWithText("See all aircraft types")
        aircraftTypesButton.performClick()

        val backArrowButton = composeTestRule.onNodeWithContentDescription("Back")
        backArrowButton.performClick()

        navController.assertCurrentRouteName(HomeDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickBackArrowInSeeAllAirportsScreen_opensHomeScreen() {
        val menuButton = composeTestRule.onNodeWithContentDescription("menu")
        menuButton.performClick()

        val seeAllAirportsButton = composeTestRule.onNodeWithText("See all airports")
        seeAllAirportsButton.performClick()

        val backArrowButton = composeTestRule.onNodeWithContentDescription("Back")
        backArrowButton.performClick()

        navController.assertCurrentRouteName(HomeDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickCloseButtonInLoginScreen_opensHomeScreen() {
        val loginButton = composeTestRule.onNodeWithText("Log in")
        loginButton.performClick()

        val closeButton = composeTestRule.onNodeWithContentDescription("close")
        closeButton.performClick()

        navController.assertCurrentRouteName(HomeDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickCloseButtonInRegisterScreen_opensHomeScreen() {
        val registerButton = composeTestRule.onNodeWithText("Register")
        registerButton.performClick()

        val closeButton = composeTestRule.onNodeWithContentDescription("close")
        closeButton.performClick()

        navController.assertCurrentRouteName(HomeDestination.route)
    }

    @Test
    fun skyEyeNavHost_clickCloseButtonInForgotPasswordScreen_opensHomeScreen() {
        val loginButton = composeTestRule.onNodeWithText("Log in")
        loginButton.performClick()

        val forgotPasswordButton = composeTestRule.onNodeWithText("Forgot password?")
        forgotPasswordButton.performClick()

        val closeButton = composeTestRule.onNodeWithContentDescription("close")
        closeButton.performClick()

        navController.assertCurrentRouteName(HomeDestination.route)
    }
}