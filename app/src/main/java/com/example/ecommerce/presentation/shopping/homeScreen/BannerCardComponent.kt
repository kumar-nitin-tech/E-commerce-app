package com.example.ecommerce.presentation.shopping.homeScreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce.R
import com.example.ecommerce.presentation.navigation.Screen
import com.example.ecommerce.presentation.shopping.Product
import com.example.ecommerce.ui.theme.GCardBackground
import com.example.ecommerce.ui.theme.GLightRed


@Composable
fun BannerCardComponent(
    product: Product,
    navController: NavHostController
){
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .width(300.dp)
            .height(160.dp)
            .clickable {
                       navController.navigate(Screen.ProductDetailScreen.route + "/${product.productName}")
            },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(GCardBackground),
    ) {
        Row(
            modifier = Modifier.padding(10.dp)
        ){
            val painter = rememberAsyncImagePainter(model = product.imageUri)
            Image(
                painter = painter,
                contentDescription ="Product Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(130.dp)
                    .height(130.dp)
            )
            Column(

            ) {
                Text(
                    text = product.productName,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.nicomoji_regular)),
                        fontWeight = FontWeight(500),
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(top = 5.dp, end = 10.dp, start = 15.dp)
                )
                Text(
                    text = "₹${product.productPrice}",
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.nicomoji_regular)),
                        fontWeight = FontWeight(500),
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier.padding(top = 5.dp,end = 10.dp, start = 15.dp)
                )
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(GLightRed),
                    shape = RoundedCornerShape(topStart = 16.dp, bottomEnd = 16.dp),
                    modifier = Modifier
                        .align(Alignment.End)
                        .wrapContentWidth()
                ) {
                    Text(
                        text = "Add to cart",
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
}

