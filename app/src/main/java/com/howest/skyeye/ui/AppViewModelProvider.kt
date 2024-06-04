package com.howest.skyeye.ui


import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.howest.skyeye.SkyEyeApplication
import com.howest.skyeye.ui.home.modals.filter.FilterViewModel
import com.howest.skyeye.ui.home.modals.maptype.MapTypeViewModel
import com.howest.skyeye.ui.home.modals.weather.WeatherViewModel
import com.howest.skyeye.ui.user.UserViewModel
import com.howest.skyeye.ui.theme.ThemeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ThemeViewModel(SkyEyeApplication().container.userPreferencesRepositoryInterface)
        }
        initializer {
            UserViewModel(SkyEyeApplication().container.usersRepositoryInterface)
        }
        initializer {
            WeatherViewModel()
        }
        initializer {
            MapTypeViewModel()
        }
        initializer {
            FilterViewModel()
        }
    }
}

fun CreationExtras.SkyEyeApplication(): SkyEyeApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as SkyEyeApplication)
