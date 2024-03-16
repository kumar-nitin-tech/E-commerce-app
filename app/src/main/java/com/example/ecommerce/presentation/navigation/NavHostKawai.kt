package com.example.ecommerce.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ecommerce.presentation.SplashScreen
import com.example.ecommerce.presentation.login.LoginPageScreen
import com.example.ecommerce.presentation.login.LoginScreen
import com.example.ecommerce.presentation.login.RegisterScreen
import com.example.ecommerce.presentation.shopping.bottomNavComponents.BottomNavScreen

@Composable
fun NavHostKawai(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.LoginPageScreen.route) {
            LoginPageScreen(navController)
        }

        composable(Screen.RegisterScreen.route) {
            RegisterScreen(navController)
            //navController.popBackStack()
        }

        composable(Screen.LoginScreen.route) {
            LoginScreen(navController)
            //navController.popBackStack()
        }
        composable(Screen.BottomNavScreen.route){
            BottomNavScreen(navController)
        }
        composable(Screen.SplashScreen.route){
            SplashScreen(navController)
        }

    }
}