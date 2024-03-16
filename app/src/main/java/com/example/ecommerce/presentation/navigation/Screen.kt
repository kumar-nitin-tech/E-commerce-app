package com.example.ecommerce.presentation.navigation

sealed class Screen(val route: String) {

    object LoginPageScreen: Screen("loginPageScreen")
    object LoginScreen : Screen("loginScreen")
    object RegisterScreen : Screen("registerScreen")

    object BottomNavScreen: Screen("bottomNavScreen")
    object SplashScreen: Screen("splashScreen")
    object ProductDetailScreen: Screen("productDetailScreen")
    object AddressScreen: Screen("addressScreen")
    object AddNewAddressScreen: Screen("addNewAddressScreen")
    object OrderPlaceScreen: Screen("orderPlacedScreen")
    object UpdateProfileScreen: Screen("updateProfileScreen")
    object AllOrderScreen: Screen("allOrderScreen")
    object OrderDetailScreen: Screen("orderDetailScreen")
}