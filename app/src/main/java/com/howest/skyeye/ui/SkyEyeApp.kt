package com.howest.skyeye.ui

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.howest.skyeye.ui.aircraft.AircraftsScreen
import com.howest.skyeye.ui.aircraft.SeeAllAircraftTypesDestination
import com.howest.skyeye.ui.aircraft.detail.AircraftDetailDestination
import com.howest.skyeye.ui.aircraft.detail.AircraftDetailScreen
import com.howest.skyeye.ui.airport.AirportsScreen
import com.howest.skyeye.ui.airport.SeeAllAirportsDestination
import com.howest.skyeye.ui.airport.detail.AirportDetailDestination
import com.howest.skyeye.ui.airport.detail.AirportDetailScreen
import com.howest.skyeye.ui.camera.CameraDestination
import com.howest.skyeye.ui.camera.OpenCamera
import com.howest.skyeye.ui.home.HomeDestination
import com.howest.skyeye.ui.home.drawer.Drawer
import com.howest.skyeye.ui.settings.SettingsDestination
import com.howest.skyeye.ui.settings.SettingsScreen
import com.howest.skyeye.ui.settings.about.AboutDestination
import com.howest.skyeye.ui.settings.about.AboutSettingsScreen
import com.howest.skyeye.ui.settings.account.AccountDestination
import com.howest.skyeye.ui.settings.account.AccountSettingsScreen
import com.howest.skyeye.ui.settings.appearance.AppearanceDestination
import com.howest.skyeye.ui.settings.appearance.AppearanceSettingsScreen
import com.howest.skyeye.ui.settings.support.SupportDestination
import com.howest.skyeye.ui.settings.support.SupportSettingsScreen
import com.howest.skyeye.ui.user.UserViewModel
import com.howest.skyeye.ui.user.ForgotPasswordDestination
import com.howest.skyeye.ui.user.ForgotPasswordScreen
import com.howest.skyeye.ui.user.LoginAndRegisterScreen
import com.howest.skyeye.ui.user.LoginDestination
import com.howest.skyeye.ui.user.RegisterDestination

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun SkyEyeApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    SkyEyeNavHost(
        navController = navController,
        context = context
    )
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun SkyEyeNavHost(
    navController: NavHostController,
    context: Context
) {
    val userViewModel : UserViewModel = viewModel(factory = AppViewModelProvider.Factory)

    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
    ) {
        composable(route = HomeDestination.route) {
            Drawer(navigateTo = { navController.navigate(it) }, userViewModel = userViewModel)
        }
        composable(route = LoginDestination.route) {
            LoginAndRegisterScreen(isRegister = false, navigateTo = { navController.navigate(it) }, userViewModel = userViewModel)
        }
        composable(route = RegisterDestination.route) {
            LoginAndRegisterScreen(isRegister = true, navigateTo = { navController.navigate(it) }, userViewModel = userViewModel)
        }
        composable(route = ForgotPasswordDestination.route) {
            ForgotPasswordScreen(navigateTo = { navController.navigate(it) })
        }
        composable(route = CameraDestination.route) {
            OpenCamera(context, navigateTo = { navController.navigate(it) }, navigateBack = { navController.navigateUp() })
        }
        composable(route = SeeAllAircraftTypesDestination.route) {
            AircraftsScreen(navigateTo = { navController.navigate(it) }, navigateBack = { navController.navigateUp() })
        }
        composable(route = SeeAllAirportsDestination.route) {
            AirportsScreen(navigateTo = { navController.navigate(it) }, navigateBack = { navController.navigateUp() })
        }
        composable(SettingsDestination.route) {
            SettingsScreen(userViewModel = userViewModel, navigateTo = { navController.navigate(it) }, navigateBack = { navController.navigateUp() })
        }
        composable(route = AccountDestination.route) {
            AccountSettingsScreen(userViewModel = userViewModel, navigateBack = { navController.navigateUp() }, navigateTo = { navController.navigate(it) })
        }
        composable(route = AppearanceDestination.route) {
            AppearanceSettingsScreen(navigateBack = { navController.navigateUp() })
        }
        composable(route = SupportDestination.route) {
            SupportSettingsScreen(navigateBack = { navController.navigateUp() })
        }
        composable(route = AboutDestination.route) {
            AboutSettingsScreen(navigateBack = { navController.navigateUp() })
        }
        composable(
            route = AirportDetailDestination.routeWithArgs,
            arguments = listOf(
                navArgument(AirportDetailDestination.IcaoArg) { type = NavType.StringType },
                navArgument(AirportDetailDestination.airportNameArg) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val icao = backStackEntry.arguments?.getString(AirportDetailDestination.IcaoArg)
            val airportName = backStackEntry.arguments?.getString(AirportDetailDestination.airportNameArg)
            if (icao != null && airportName != null) {
                AirportDetailScreen(icao, airportName, navigateTo = { navController.navigate(it) }, navigateBack = { navController.navigateUp() })
            }
        }
        composable(
            route = AircraftDetailDestination.routeWithArgs,
            arguments = listOf(
                navArgument(AircraftDetailDestination.aircraftTypeArg) { type = NavType.StringType },
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getString(AircraftDetailDestination.aircraftTypeArg)?.let { aircraftType ->
                AircraftDetailScreen(aircraftType, navigateBack = { navController.navigateUp() })
            }
        }
    }
}