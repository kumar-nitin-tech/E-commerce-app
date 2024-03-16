package com.example.ecommerce.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ecommerce.R
import com.example.ecommerce.model.loginViewModel.SplashViewModel
import com.example.ecommerce.presentation.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController){
    val splashViewModel:SplashViewModel = hiltViewModel()
    val startDestination by splashViewModel.startDestination
    LaunchedEffect(key1 = true) {
        delay(1200L)
        navController.navigate(startDestination){
            popUpTo(Screen.SplashScreen.route){
                inclusive = true
            }
        }

    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.padding(65.dp)
        )
    }
}

@Preview
@Composable
fun Show(){
    SplashScreen(rememberNavController())
}