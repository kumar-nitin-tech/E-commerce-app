package com.example.ecommerce.dataModel

import android.os.Parcelable
import com.example.ecommerce.presentation.shopping.Product
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartProduct(
    val product: Product,
    val quantity: Int
) : Parcelable {
    constructor():this(Product(),0,)
}
