package com.example.ecommerce.presentation.shopping.order

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ecommerce.R
import com.example.ecommerce.constants.Resource
import com.example.ecommerce.presentation.navigation.Screen
import com.example.ecommerce.ui.theme.GCardBackground

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AllOrderScreen(navController: NavHostController){
    val context = LocalContext.current
    val allOrderViewModel: OrderViewModel = hiltViewModel()
    val allOrders by allOrderViewModel.allOrder.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
    ) {
        Text(
            text = "Orders",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 24.sp,
                fontWeight = FontWeight(800)
            ),
            modifier = Modifier.padding(top = 20.dp, start = 16.dp)
        )

        LazyColumn {
            when(allOrders){
                is Resource.Error -> {
                    Toast.makeText(context,allOrders.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                   this.items(allOrders.data!!){
                       Card (
                           modifier = Modifier
                               .fillMaxWidth()
                               .height(50.dp)
                               .padding(top = 5.dp, end = 8.dp),
                           backgroundColor = GCardBackground,
                           onClick = {
                               navController.currentBackStackEntry?.savedStateHandle?.set(
                                   key = "orderId",
                                   value = it.orderId
                               )
                               navController.navigate(Screen.OrderDetailScreen.route)
                           }
                       ){
                           Row(
                               modifier = Modifier
                                   .fillMaxWidth()
                                   .padding(start = 10.dp, end = 10.dp),
                               horizontalArrangement = Arrangement.SpaceBetween
                           ) {
                               Text(
                                   text = it.orderId.toString(),
                                   style = TextStyle(
                                       fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                       fontSize = 16.sp
                                   )
                               )

                               Text(
                                   text = it.date,
                                   style = TextStyle(
                                       fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                       fontSize = 16.sp
                                   )
                               )
                           }
                       }
                   }
                }
                else-> Unit
            }
        }
    }
}

@Preview
@Composable
fun ShowAllOrderScreen(){
    AllOrderScreen(rememberNavController())
}