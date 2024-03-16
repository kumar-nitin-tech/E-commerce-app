package com.example.ecommerce.presentation.shopping.bottomNavComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ecommerce.NavGraph
import com.example.ecommerce.model.loginViewModel.SplashViewModel
import com.example.ecommerce.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavScreen(navController:NavHostController){
    val splashViewModel:SplashViewModel = hiltViewModel()
    val loginState by splashViewModel.state
    //val bottomNavController = rememberNavController()
    val bottomNavState = rememberSaveable {
        mutableStateOf(true)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    when(navBackStackEntry?.destination?.route){
        Screen.ProductDetailScreen.route + "/{productName}" ->{
            bottomNavState.value = false
        }
        Screen.AddressScreen.route ->{
            bottomNavState.value = false
        }
        Screen.AddNewAddressScreen.route ->{
            bottomNavState.value = false
        }
        BottomNavItems.Home.routes->{
            bottomNavState.value = true
        }
        BottomNavItems.Cart.routes->{
            bottomNavState.value = true
        }
        BottomNavItems.Profile.routes->{
            bottomNavState.value = true
        }
        Screen.UpdateProfileScreen.route->{
            bottomNavState.value = false
        }
        Screen.SplashScreen.route->{
            bottomNavState.value = false
        }
        Screen.OrderDetailScreen.route->{
            bottomNavState.value = false
        }
    }
    Scaffold (
        bottomBar = {
            if(loginState)
                BottomNavBar(navController = navController,bottomNavState)
        }
    ){
       Box(modifier = Modifier.padding(it)){
           NavGraph(navController)
       }
    }

}