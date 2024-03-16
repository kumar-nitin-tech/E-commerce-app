package com.example.ecommerce.presentation.shopping.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce.R
import com.example.ecommerce.dataModel.CartProduct
import com.example.ecommerce.presentation.shopping.Product
import com.example.ecommerce.presentation.shopping.bottomNavComponents.BottomNavItems
import com.example.ecommerce.presentation.shopping.productDetailScreen.ProductDetailViewModel
import com.example.ecommerce.ui.theme.GLightRed

@Composable
fun ProductCard(
    product: Product,
    navController: NavHostController,
    onClick:(Product)->Unit
){
    val productDetailViewModel: ProductDetailViewModel = hiltViewModel()
    LaunchedEffect(Unit){
        productDetailViewModel.getProductDetails(product.productName)
    }
    val painterResource = rememberAsyncImagePainter(
        model = product.imageUri
    )
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(200.dp)
            .padding(10.dp)
            .clickable { onClick(product) },
    ) {
        Image(
            painter = painterResource,
            contentDescription = null,
            Modifier
                .size(140.dp)
                .padding(start = 16.dp, top = 5.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Fit
        )

        Text(
            text = product.productName,
            modifier = Modifier.padding(start = 16.dp, top = 5.dp),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 18.sp,
                fontWeight = FontWeight(800)
            )
        )
        Text(
            text = "₹${product.productPrice}",
            modifier = Modifier.padding(start = 16.dp, top = 3.dp),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 14.sp,
                fontWeight = FontWeight(400)
            )
        )
        Button(
            onClick = {
                productDetailViewModel.addUpdateCartProduct(CartProduct(product,1))
                      navController.navigate(BottomNavItems.Cart.routes)
            },
            colors = ButtonDefaults.buttonColors(GLightRed),
            shape = RoundedCornerShape(topStart = 16.dp, bottomEnd = 16.dp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
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

@Preview
@Composable
fun PreviewProductCard(){
    ProductCard(
        Product(
        "One Piece",
        "",
            "One Piece Character",
        "One Piece Character",
        "1000"
    ),
        rememberNavController(),
        onClick = {}
    )
}