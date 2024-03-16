package com.example.ecommerce.presentation.shopping

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.ecommerce.presentation.shopping.bottomNavComponents.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(){

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.DarkGray)
                    .wrapContentSize(Alignment.Center)
            ) {
                Text(
                    text = "Search Screen",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                )
            }
}