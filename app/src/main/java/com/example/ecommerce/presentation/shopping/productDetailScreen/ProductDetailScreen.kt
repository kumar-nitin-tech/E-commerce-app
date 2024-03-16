package com.example.ecommerce.presentation.shopping.productDetailScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce.R
import com.example.ecommerce.dataModel.CartProduct
import com.example.ecommerce.presentation.shopping.bottomNavComponents.BottomNavItems
import com.example.ecommerce.ui.theme.GLightRed

@Composable
fun ProductDetailScreen(
    productName:String,
    navController: NavHostController
){
    val productDetailViewModel:ProductDetailViewModel = hiltViewModel()
    LaunchedEffect(Unit){
        productDetailViewModel.getProductDetails(productName)
    }
    val product by productDetailViewModel.productDetail.collectAsState(initial = emptyList())
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ){
        items(product) {product->
            Column {
                val painter = rememberAsyncImagePainter(model = product.imageUri)

                Image(
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .width(320.dp)
                        .height(550.dp)
                )

                Text(
                    text = product.productName,
                    modifier = Modifier.padding(start = 16.dp, top = 10.dp),
                    maxLines = 1,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontSize = 24.sp,
                        fontWeight = FontWeight(800)
                    )
                )

                Text(
                    text = "₹${product.productPrice}",
                    modifier = Modifier.padding(start = 16.dp, top = 10.dp),
                    maxLines = 1,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontSize = 18.sp,
                        fontWeight = FontWeight(800)
                    )
                )

                Text(
                    text = "Product Description",
                    modifier = Modifier.padding(start = 16.dp, top = 10.dp),
                    maxLines = 1,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontSize = 18.sp,
                        fontWeight = FontWeight(800)
                    )
                )

                Text(
                    text = product.productDescription,
                    modifier = Modifier.padding(start = 16.dp, top = 10.dp),
                    maxLines = 2,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight(800)
                    )
                )

                Button(
                    onClick = {
                              productDetailViewModel.addUpdateCartProduct(CartProduct(product,1))
                        navController.navigate(BottomNavItems.Cart.routes)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(
                            top = 16.dp, bottom = 10.dp
                        ),
                shape = RoundedCornerShape(topStart = 16.dp, bottomEnd = 16.dp),
                colors = ButtonDefaults.buttonColors(GLightRed)
                ) {
                Text(
                    text = "Add to cart",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                )
            }
            }
        }
    }
}
