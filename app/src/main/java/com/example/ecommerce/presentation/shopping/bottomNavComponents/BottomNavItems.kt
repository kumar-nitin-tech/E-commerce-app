package com.example.ecommerce.presentation.shopping.bottomNavComponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItems(val routes: String, val icon: ImageVector) {
    object Home: BottomNavItems("home", Icons.Default.Home)
    object Search: BottomNavItems("search", Icons.Default.Search)
    object Cart: BottomNavItems("cart", Icons.Default.ShoppingCart)
    object Profile: BottomNavItems("profile", Icons.Default.Person)
}