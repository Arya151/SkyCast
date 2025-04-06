package com.weather.skycast.home.presentation.component

sealed class HomeAction {
    data class OnSearchTextChange(val text: String) : HomeAction()
    object OnSearchClick : HomeAction()
    data class SubmitSearch(val city: String) : HomeAction()
    data class OnCitySelected(val lat: Double, val lon: Double) : HomeAction()
    object OnBackPressed : HomeAction()

}