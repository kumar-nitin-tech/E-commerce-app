package com.example.ecommerce.dataModel

data class User(
    val firstName: String,
    val lastName: String,
    val emailId: String,
    val imagePath: String = ""
){
    constructor():this(
        "","","",""
    )
}
