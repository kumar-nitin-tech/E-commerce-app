package com.example.ecommerce.presentation.shopping.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ecommerce.R
import com.example.ecommerce.presentation.navigation.Screen
import com.example.ecommerce.presentation.shopping.Product

//Product is added from another app with image, price and name to firebase and collected in this app.
@Composable
fun ListProductComponent(
    title:String,
    productItem: List<Product>,
    onClick: ()->Unit,
    navController: NavHostController
){
    Column(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.nicomoji_regular)),
                fontSize = 20.sp
            )
        )
        LazyRow(
            modifier = Modifier.padding(start = 16.dp, top = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ){
            items(productItem){
                ProductCard(product = it, navController = navController) {
                    navController.navigate(Screen.ProductDetailScreen.route + "/${it.productName}")
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewProduct(){
    ListProductComponent(
        "One Piece",
        productItem = listOf(
            Product("One Piece","","One Piece","Luffy", "1000"),
            Product("One Piece","","One Piece","Luffy", "1000"),
            Product("One Piece","","One Piece","Luffy", "1000"),
            Product("One Piece","","One Piece","Luffy", "1000")
        )
        , onClick = { /*TODO*/ },
        navController = rememberNavController()
    )
}