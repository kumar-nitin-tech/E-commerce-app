package com.example.ecommerce.presentation.shopping.addressScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ecommerce.R
import com.example.ecommerce.presentation.shopping.bottomNavComponents.BottomNavItems
import kotlinx.coroutines.delay

@Composable
fun OrderPlacedScreen(navController:NavHostController){
    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate(BottomNavItems.Cart.routes){
            popUpTo(BottomNavItems.Cart.routes){
                inclusive = true
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize().fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.order_placed),
            contentDescription = null )
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Order Placed",
            style = TextStyle(
                fontSize = 18.sp
            )
        )
    }
}

@Preview
@Composable
fun ShowOrderPlacedScreen(){
    OrderPlacedScreen(rememberNavController())
}