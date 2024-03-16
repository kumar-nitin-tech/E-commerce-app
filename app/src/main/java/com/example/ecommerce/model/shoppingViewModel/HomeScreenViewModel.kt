package com.example.ecommerce.model.shoppingViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ecommerce.constants.Constants.firebaseDbPath
import com.example.ecommerce.presentation.shopping.Product
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val firebase: FirebaseFirestore
):ViewModel() {

    private val _productOnePieceItem = MutableStateFlow<List<Product>>(listOf())
    val productOnePieceItem: StateFlow<List<Product>> = _productOnePieceItem

    private val _productNarutoItem = MutableStateFlow<List<Product>>(listOf())
    val productNarutoItem: StateFlow<List<Product>> = _productNarutoItem

    private val _productMHAItem = MutableStateFlow<List<Product>>(listOf())
    val productMHAItem: StateFlow<List<Product>> = _productMHAItem

    private val _productDemonItem = MutableStateFlow<List<Product>>(listOf())
    val productDemonItem: StateFlow<List<Product>> = _productDemonItem

    private val _productBannerItem = MutableStateFlow<List<Product>>(listOf())
    val productBannerItem: StateFlow<List<Product>> = _productBannerItem
    init {
        fetchOnePieceProduct()
        fetchNarutoProduct()
        fetchMHAProduct()
        fetchDemonProduct()
        fetchBannerProduct()
    }

    private fun fetchBannerProduct(){
        firebase.collection(firebaseDbPath)
            .limit(3)
            .get()
            .addOnSuccessListener {
                _productBannerItem.value = it.toObjects(Product::class.java)
            }.addOnFailureListener {
                Log.e("Error",it.toString())
            }
    }
    private fun fetchOnePieceProduct(){
        firebase.collection(firebaseDbPath)
            .whereEqualTo("animeName","One Piece")
            .get()
            .addOnSuccessListener {
                _productOnePieceItem.value = it.toObjects(Product::class.java)
            }.addOnFailureListener {
                Log.e("Error",it.toString())
            }
    }

    private fun fetchNarutoProduct(){
        firebase.collection(firebaseDbPath)
            .whereEqualTo("animeName","Naruto ")
            .get()
            .addOnSuccessListener {
                _productNarutoItem.value = it.toObjects(Product::class.java)
            }.addOnFailureListener {
                Log.e("Error",it.toString())
            }
    }

    private fun fetchMHAProduct(){
        firebase.collection(firebaseDbPath)
            .whereEqualTo("animeName","My Hero Academia ")
            .get()
            .addOnSuccessListener {
                _productMHAItem.value = it.toObjects(Product::class.java)
            }.addOnFailureListener {
                Log.e("Error",it.toString())
            }
    }

    private fun fetchDemonProduct(){
        firebase.collection(firebaseDbPath)
            .whereEqualTo("animeName","Demon Slayer ")
            .get()
            .addOnSuccessListener {
                _productDemonItem.value = it.toObjects(Product::class.java)
            }.addOnFailureListener {
                Log.e("Error",it.toString())
            }
    }
}