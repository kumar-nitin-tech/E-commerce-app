package com.example.ecommerce.presentation.shopping.order

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce.R
import com.example.ecommerce.constants.Resource

@Composable
fun OrderDetailScreen(
    orderId: Long
){
    val context = LocalContext.current
    val orderViewModel:OrderViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        orderViewModel.getOrderDetail(orderId)
    }
    val order by orderViewModel.orderDetail.collectAsState()

    LazyColumn(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {
            when(order){
                is Resource.Error -> {
                    Toast.makeText(context,order.message,Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {
                    this.item {
                        CircularProgressIndicator()
                    }
                }
                is Resource.Success -> {
                    this.items(order.data!!) {
                        Text(
                            text = "Order Summary of ${it.orderId}",
                            modifier = Modifier.padding(top = 10.dp, start = 16.dp),
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontSize = 24.sp,
                                color = Color.Black
                            )
                        )
                        Text(
                            text = "Address",
                            modifier = Modifier.padding(top = 10.dp, start = 16.dp),
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                        )

                        Card(
                            modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                            ) {
                                Column {
                                    androidx.compose.material3.Text(text = it.address.fullName)
                                    androidx.compose.material3.Text(text = "${it.address.addressTitle}, ${it.address.street}, ${it.address.city}, ${it.address.state}")
                                    androidx.compose.material3.Text(text = "Pincode: ${it.address.pincode}")
                                    androidx.compose.material3.Text(text = "Mobile: ${it.address.phone}")
                                }
                            }
                        }

                        Text(
                            text = "Order Items",
                            modifier = Modifier.padding(top = 10.dp, start = 16.dp),
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontSize = 18.sp,
                                color = Color.Black
                            )
                        )
                        LazyColumn(modifier = Modifier.height(500.dp)){
                            items(it.products) {cartProduct->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(110.dp)
                                        .padding(bottom = 10.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                    ) {
                                        val painter =
                                            rememberAsyncImagePainter(model = cartProduct.product)
                                        Image(
                                            painter = painter,
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(100.dp)
                                                .padding(2.dp),
                                            contentScale = ContentScale.Fit
                                        )

                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(5.dp),
                                            modifier = Modifier.padding(top = 5.dp, start = 6.dp)
                                        ) {
                                            androidx.compose.material3.Text(
                                                text = cartProduct.product.productName,
                                                style = TextStyle(
                                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                    color = Color.Black,
                                                    fontSize = 20.sp
                                                )
                                            )
                                            androidx.compose.material3.Text(
                                                text = "₹ ${cartProduct.product.productPrice.toDouble() * cartProduct.quantity}",
                                                style = TextStyle(
                                                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                                    color = Color.Black,
                                                    fontSize = 16.sp
                                                )
                                            )
                                        }
                                        Column(
                                            modifier = Modifier
                                                .padding(top = 5.dp, end = 6.dp)
                                                .weight(1f),
                                            horizontalAlignment = Alignment.End,
                                            verticalArrangement = Arrangement.spacedBy(10.dp),
                                        ) {
                                            Icon(
                                                Icons.Default.KeyboardArrowUp,
                                                contentDescription = null,
                                            )
                                            androidx.compose.material3.Text(
                                                text = cartProduct.quantity.toString(),
                                                modifier = Modifier.padding(end = 8.dp)
                                            )
                                            Icon(
                                                Icons.Default.KeyboardArrowDown,
                                                contentDescription = null,
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        Text(
                            text = "Total Price: ${it.totalPrice}",
                            modifier = Modifier.padding(top = 10.dp, start = 16.dp),
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                fontSize = 24.sp,
                                color = Color.Black
                            )
                        )
                    }

                }

                else-> Unit
            }
        }
}

@Preview
@Composable
fun ShowOrderDetailScreen(){
    OrderDetailScreen(0L)
}

