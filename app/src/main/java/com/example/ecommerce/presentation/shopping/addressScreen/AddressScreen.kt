package com.example.ecommerce.presentation.shopping.addressScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.RadioButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.ecommerce.dataModel.Address
import com.example.ecommerce.presentation.navigation.Screen
import com.example.ecommerce.presentation.shopping.order.Order
import com.example.ecommerce.presentation.shopping.order.OrderViewModel
import com.example.ecommerce.ui.theme.GLightRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreen(
    navController: NavHostController,
    order: Order
){
    val context = LocalContext.current
    val addressViewModel:AddressViewModel = hiltViewModel()
    val orderViewModel :OrderViewModel = hiltViewModel()
    val userAddress by addressViewModel.address.collectAsState()
    var selectedAddress by remember {
        mutableStateOf(Address())
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .fillMaxWidth()
        .padding(top = 10.dp, start = 16.dp, end = 16.dp, bottom = 10.dp)) {
        Column(
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Select Address",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontSize = 24.sp,
                        fontWeight = FontWeight(800)
                    )
                )

                Button(
                    onClick = {
                              navController.navigate(Screen.AddNewAddressScreen.route)
                    },
                    colors = ButtonDefaults.buttonColors(GLightRed),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(
                        start = 10.dp,
                        top = 2.dp,
                        end = 10.dp,
                        bottom = 2.dp
                    ),

                    ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.Black
                    )
                    Text(
                        text = "Add",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontSize = 16.sp,
                            color = Color.Black
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(top = 4.dp)
                    )
                }

            }
                LazyColumn() {
                    when (userAddress) {
                        is Resource.Error -> {
                            this.item {
                                Toast.makeText(context, userAddress.message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                        is Resource.Loading -> {
                            this.item {
                                CircularProgressIndicator()
                            }
                        }

                        is Resource.Success -> {
                            if(userAddress.data.isNullOrEmpty()){
                                this.item {
                                    Text(
                                        text = "Add New Address",
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)
                                            .padding(top = 50.dp, start = 50.dp)
                                    )
                                }
                            }else{
                                this.items(userAddress.data!!){
                                    Card(
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    ) {
                                        Row (
                                            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                                        ){
                                            if(selectedAddress == Address()){
                                                selectedAddress = userAddress.data!![0]
                                            }
                                            RadioButton(
                                                selected = it == selectedAddress,
                                                onClick = {
                                                    selectedAddress = it
                                                }
                                            )
                                            Column {
                                                Text(text = it.fullName)
                                                Text(text = "${it.addressTitle}, ${it.street}, ${it.city}, ${it.state}")
                                                Text(text = "Pincode: ${it.pincode}")
                                                Text(text = "Mobile: ${it.phone}")
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else -> Unit
                    }
                }

        }
        Button(
            onClick = {
                val orders = order.copy(address = selectedAddress)
                orderViewModel.placeOrder(orders)
                navController.navigate(Screen.OrderPlaceScreen.route){
                    popUpTo(Screen.OrderPlaceScreen.route){
                        inclusive = true
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 5.dp)
        ) {
            Text(text = "Place Order")
        }
    }

}

@Preview
@Composable
fun ShowAddressScreen(){
    AddressScreen(rememberNavController(), Order("",0f, emptyList(),Address()))
}