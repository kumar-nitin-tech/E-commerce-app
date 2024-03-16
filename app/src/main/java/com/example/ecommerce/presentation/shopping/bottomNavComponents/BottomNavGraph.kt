package com.example.ecommerce.presentation.shopping.bottomNavComponents

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ecommerce.presentation.navigation.Screen
import com.example.ecommerce.presentation.shopping.SearchScreen
import com.example.ecommerce.presentation.shopping.addressScreen.AddNewAddressScreen
import com.example.ecommerce.presentation.shopping.addressScreen.AddressScreen
import com.example.ecommerce.presentation.shopping.addressScreen.OrderPlacedScreen
import com.example.ecommerce.presentation.shopping.cartScreen.CartScreen
import com.example.ecommerce.presentation.shopping.homeScreen.HomeScreen
import com.example.ecommerce.presentation.shopping.order.AllOrderScreen
import com.example.ecommerce.presentation.shopping.order.Order
import com.example.ecommerce.presentation.shopping.order.OrderDetailScreen
import com.example.ecommerce.presentation.shopping.productDetailScreen.ProductDetailScreen
import com.example.ecommerce.presentation.shopping.profileScreen.ProfileScreen
import com.example.ecommerce.presentation.shopping.profileScreen.UpdateProfileScreen


@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItems.Home.routes){
        composable(BottomNavItems.Home.routes){
            HomeScreen(navController)
        }
        composable(
            BottomNavItems.Cart.routes
        ){
            CartScreen(navController)
        }
        composable(BottomNavItems.Profile.routes){
            ProfileScreen(navController)
        }
        composable(BottomNavItems.Search.routes){
            SearchScreen()
        }
        composable(
            Screen.ProductDetailScreen.route + "/{productName}", arguments = listOf(
            navArgument("productName"){
                type = NavType.StringType
            }
        )){
            ProductDetailScreen(it.arguments?.getString("productName").toString(),navController)
        }

        composable(Screen.AddressScreen.route){
            val order = navController.previousBackStackEntry?.savedStateHandle?.get<Order>("order")
            if(order != null) {
                AddressScreen(navController = navController, order)
            }
        }

        composable(Screen.AddNewAddressScreen.route){
            AddNewAddressScreen(navController = navController)
        }
        
        composable(Screen.OrderPlaceScreen.route){
            OrderPlacedScreen(navController = navController)
        }

        composable(Screen.UpdateProfileScreen.route){
            UpdateProfileScreen(navController)
        }
        
        composable(
            Screen.AllOrderScreen.route
        ){

            AllOrderScreen(navController = navController)
        }


        composable(Screen.OrderDetailScreen.route){
            val orderId = navController.previousBackStackEntry?.savedStateHandle?.get<Long>("orderId")
            if(orderId != null)
                OrderDetailScreen(orderId)
        }

    }
}