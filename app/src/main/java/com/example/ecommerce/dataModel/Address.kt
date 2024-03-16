package com.example.ecommerce.dataModel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val addressTitle: String,
    val fullName: String,
    val street: String,
    val phone : String,
    val city: String,
    val state:String,
    val pincode: String
) : Parcelable {
    constructor():this("","","","","","","")
}