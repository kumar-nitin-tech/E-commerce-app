package com.example.ecommerce.presentation.shopping

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val animeName: String,
    val imageUri: String,
    val productDescription: String,
    val productName: String,
    val productPrice: String,
) : Parcelable {
    constructor():this("","","","","")
}


