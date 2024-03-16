package com.example.ecommerce.presentation.shopping.cartScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce.R
import com.example.ecommerce.dataModel.CartProduct
import com.example.ecommerce.presentation.shopping.Product

@Composable
fun CartItem(
    cartProduct: CartProduct,
    onIncreaseClick: ()->Unit,
    onDecreaseClick: ()->Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(bottom = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            val painter = rememberAsyncImagePainter(model = cartProduct.product.imageUri)
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
                Text(
                    text = cartProduct.product.productName,
                    style = TextStyle(
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        color = Color.Black,
                        fontSize = 20.sp
                    )
                )
                Text(
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
                    modifier = Modifier.clickable {
                        onIncreaseClick()
                    }
                )
                Text(
                    text = cartProduct.quantity.toString(),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        onDecreaseClick()
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun ShowCartItem(){
    CartItem(
        CartProduct(
            Product(
                "One Piece",
                "","" ,
                "Luffy",
                "2599"
            ),
            1
        ),
        {},
        {}
    )
}