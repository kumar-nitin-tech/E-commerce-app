package com.example.ecommerce.presentation.shopping.cartScreen

import android.app.AlertDialog
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.example.ecommerce.firebaseCommon.FirebaseCommon
import com.example.ecommerce.presentation.navigation.Screen
import com.example.ecommerce.presentation.shopping.order.Order
import com.example.ecommerce.presentation.shopping.order.OrderStatus
import com.example.ecommerce.presentation.shopping.order.OrderViewModel
import com.example.ecommerce.ui.theme.GLightRed
import kotlinx.coroutines.flow.collectLatest


@Composable
fun CartScreen(
    navController: NavHostController
){
    val context = LocalContext.current
    val cartScreenViewModel:CartScreenViewModel = hiltViewModel()
    val cartItemList by cartScreenViewModel.cartItem.collectAsState()
    val totalPrice by cartScreenViewModel.productPrice.collectAsState(initial = 0)
    val orderViewModel:OrderViewModel = hiltViewModel()

    LaunchedEffect(Unit){
        cartScreenViewModel.deleteAlertDialog.collectLatest {
            val alertDialog = AlertDialog.Builder(context).apply {
                setTitle("Delete item from the cart")
                setMessage("Do you want to delete this item from the cart?")
                setNegativeButton("Cancel"){dialog, _ ->
                    dialog.dismiss()
                }
                setPositiveButton("Yes"){dialog, _->
                    cartScreenViewModel.deleteCartProduct(it)
                    dialog.dismiss()
                }
            }
            alertDialog.create()
            alertDialog.show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
    ){
        Column {
            Text(
                text = "My Cart",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontSize = 24.sp,
                    color = Color.Black,
                    fontWeight = FontWeight(600)
                ),
                modifier = Modifier.padding(top = 5.dp, start = 10.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .height(600.dp)
            ) {
                when (cartItemList) {
                    is Resource.Error -> {
                        this.item {
                            Toast.makeText(context, cartItemList.message, Toast.LENGTH_SHORT).show()
                        }
                    }

                    is Resource.Loading -> {
                        this.item {
                            CircularProgressIndicator()
                        }
                    }

                    is Resource.Success -> {
                        if (cartItemList.data!!.isEmpty()) {
                            this.item {
                                ShowEmptyCart()
                            }
                        }
                        this.items(cartItemList.data!!) {
                            CartItem(
                                cartProduct = it,
                                {
                                cartScreenViewModel.changeQuantity(it,FirebaseCommon.QuantityChanged.INCREASE)
                                },{
                                    cartScreenViewModel.changeQuantity(it,FirebaseCommon.QuantityChanged.DECREASE)
                                }
                            )
                        }
                    }

                    else -> Unit
                }
            }
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Total Price:",
                    modifier = Modifier.padding(start = 10.dp),
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontSize = 20.sp,
                        fontWeight = FontWeight(800)
                    )
                )

                Text(
                    text = "₹${totalPrice}",
                    modifier = Modifier.padding(end = 10.dp),
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontSize = 20.sp,
                        fontWeight = FontWeight(800)
                    )
                )
            }

            Button(
                onClick = {
                    val order = Order(OrderStatus.Ordered.status,totalPrice as Float, cartItemList.data!!, Address())
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "order",
                        value = order
                    )
                    navController.navigate(Screen.AddressScreen.route)
                },
                colors = ButtonDefaults.buttonColors(GLightRed),
                shape = RoundedCornerShape(topStart = 16.dp, bottomEnd = 16.dp),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Check Out",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun ShowEmptyCart(){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.empty_cart) ,
            contentDescription = null
        )
    }
}


@Preview
@Composable
fun ShowCartScreen(){
    CartScreen(rememberNavController())
}