package com.example.ecommerce.presentation.shopping.productDetailScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.constants.Constants.firebaseDbPath
import com.example.ecommerce.constants.Resource
import com.example.ecommerce.dataModel.CartProduct
import com.example.ecommerce.firebaseCommon.FirebaseCommon
import com.example.ecommerce.presentation.shopping.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val firebase: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val firebaseCommon: FirebaseCommon
):ViewModel() {

    private val _productDetail = MutableStateFlow<List<Product>>(listOf())
    val productDetail: StateFlow<List<Product>> = _productDetail

    private val _cartProduct = MutableStateFlow<Resource<CartProduct>>(Resource.Unspecified())
    val cartProduct = _cartProduct.asStateFlow()
    fun getProductDetails(productName: String){
        firebase.collection(firebaseDbPath)
            .whereEqualTo("productName",productName)
            .get()
            .addOnSuccessListener {
                _productDetail.value = it.toObjects(Product::class.java)
            }
            .addOnFailureListener {
                it.message?.let { it1 -> Log.e("Error", it1) }
            }
    }

    fun addUpdateCartProduct(cartProduct: CartProduct){
        viewModelScope.launch { _cartProduct.emit(Resource.Loading()) }
        firebase.collection("user")
            .document(auth.uid!!)
            .collection("cart")
            .whereEqualTo("product.productName",cartProduct.product.productName)
            .get()
            .addOnSuccessListener {
                it.documents.let {
                    if(it.isEmpty()){
                        addNewProduct(cartProduct)
                    }else{
                        val product = it.first().toObject(CartProduct::class.java)
                        if(product == cartProduct){//Increase the quantity
                            val documentId = it.first().id
                            increaseProductQuantity(documentId,cartProduct)
                        }else{
                            addNewProduct(cartProduct)
                        }
                    }
                }
            }.addOnFailureListener {
                viewModelScope.launch { _cartProduct.emit(Resource.Error(it.message.toString())) }
            }
    }

    private fun addNewProduct(cartProduct: CartProduct){
        firebaseCommon.addProductToCart(cartProduct){addedProduct, e->
            viewModelScope.launch {
                if(e==null){
                    _cartProduct.emit(Resource.Success(addedProduct!!))
                }else{
                    _cartProduct.emit(Resource.Error(e.message.toString()))
                }
            }
        }
    }

    private fun increaseProductQuantity(documentId:String, cartProduct: CartProduct){
        firebaseCommon.increaseQuantity(documentId){_,e->
            viewModelScope.launch {
                if (e == null) {
                    _cartProduct.emit(Resource.Success(cartProduct))
                } else {
                    _cartProduct.emit(Resource.Error(e.message.toString()))
                }
            }
        }
    }
}