package com.example.ecommerce.firebaseCommon

import com.example.ecommerce.dataModel.CartProduct
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class FirebaseCommon (
    private val firestore:FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    private val cartCollection = firestore.collection("user").document(auth.uid!!).collection("cart")

    fun addProductToCart(cartProduct: CartProduct, onResult: (CartProduct?, Exception?)->Unit){
        cartCollection.document().set(cartProduct)
            .addOnSuccessListener {
                onResult(cartProduct,null)
            }
            .addOnFailureListener {
                onResult(null,it)
            }
    }

    fun increaseQuantity(documentId: String, onResult: (String?, Exception?) -> Unit){
        firestore.runTransaction { transcation ->
            val documentRef = cartCollection.document(documentId)
            val document = transcation.get(documentRef)
            val productObject = document.toObject(CartProduct::class.java)
            productObject?.let {
                val newQuantity = it.quantity + 1
                val newProductObject = it.copy(quantity = newQuantity)
                transcation.set(documentRef, newProductObject)
            }
        }
            .addOnSuccessListener {
                onResult(documentId,null)
            }
            .addOnFailureListener {
                onResult(null,it)
            }
    }

    fun decreaseQuantity(documentId: String, onResult: (String?, Exception?) -> Unit){
        firestore.runTransaction { transcation ->
            val documentRef = cartCollection.document(documentId)
            val document = transcation.get(documentRef)
            val productObject = document.toObject(CartProduct::class.java)
            productObject?.let {
                val newQuantity = it.quantity - 1
                val newProductObject = it.copy(quantity = newQuantity)
                transcation.set(documentRef, newProductObject)
            }
        }
            .addOnSuccessListener {
                onResult(documentId,null)
            }
            .addOnFailureListener {
                onResult(null,it)
            }
    }

    enum class QuantityChanged{
        INCREASE, DECREASE
    }
}