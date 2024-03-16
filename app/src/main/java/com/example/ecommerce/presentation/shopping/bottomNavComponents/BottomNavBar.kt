package com.example.ecommerce.presentation.shopping.bottomNavComponents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController

@Composable
fun BottomNavBar(
    navController: NavHostController,
    bottomNavState: MutableState<Boolean>
) {
    val navItems = listOf(BottomNavItems.Home, BottomNavItems.Cart, BottomNavItems.Profile)
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    AnimatedVisibility(
        visible = bottomNavState.value
    ) {
        NavigationBar {
            navItems.forEachIndexed { index, bottomNavItems ->
                NavigationBarItem(
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        navController.navigate(bottomNavItems.routes){
                            popUpTo(bottomNavItems.routes){
                                inclusive = true
                            }
                        }
                    },
                    icon = { Icon(bottomNavItems.icon, contentDescription = null)}
                )
            }
        }
    }

}