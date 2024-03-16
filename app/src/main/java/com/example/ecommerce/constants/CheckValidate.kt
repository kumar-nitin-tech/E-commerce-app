package com.example.ecommerce.constants

import com.example.ecommerce.dataModel.User
import com.example.ecommerce.utils.RegisterValidation
import com.example.ecommerce.utils.validateEmail
import com.example.ecommerce.utils.validatePassword

fun checkValidation(user: User, password: String): Boolean {
    val emailValidation = validateEmail(user.emailId)
    val passwordValidation = validatePassword(password)

    return emailValidation is RegisterValidation.Success && passwordValidation is RegisterValidation.Success
}