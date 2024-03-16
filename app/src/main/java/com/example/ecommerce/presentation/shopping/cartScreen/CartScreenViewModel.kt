package com.example.ecommerce.presentation.shopping.cartScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.constants.Resource
import com.example.ecommerce.dataModel.CartProduct
import com.example.ecommerce.firebaseCommon.FirebaseCommon
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    private val firebase: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val firebaseCommon: FirebaseCommon
):ViewModel(){

    private val _cartItem = MutableStateFlow<Resource<List<CartProduct>>>(Resource.Unspecified())
    val cartItem = _cartItem.asStateFlow()

    private val _deleteAlertDialog = MutableSharedFlow<CartProduct>()
    val deleteAlertDialog = _deleteAlertDialog.asSharedFlow()

    private var cartProductDocument = emptyList<DocumentSnapshot>()

    val productPrice = cartItem.map {
        when(it){
            is Resource.Success->{
                calculatePrice(it.data!!)
            }
            else -> Unit
        }
    }

    private fun calculatePrice(data: List<CartProduct>): Float {
        return data.sumOf {
            it.product.productPrice.toDouble() * it.quantity
        }.toFloat()
    }

    fun deleteCartProduct(cartProduct: CartProduct){
        val index = cartItem.value.data?.indexOf(cartProduct)
        if(index != null && index != -1){
            val documentId = cartProductDocument[index].id
            firebase.collection("user").document(auth.uid!!).collection("cart")
                .document(documentId).delete()
        }
    }

    init {
        getCartItem()
    }
    private fun getCartItem(){
        firebase.collection("user")
            .document(auth.uid!!)
            .collection("cart")
            .addSnapshotListener{value,error->
                if(error != null || value == null){
                    viewModelScope.launch { _cartItem.emit(Resource.Error(error?.message.toString())) }
                }else{
                    cartProductDocument = value.documents
                    val cartProduct = value.toObjects(CartProduct::class.java)
                    viewModelScope.launch { _cartItem.emit(Resource.Success(cartProduct)) }
                }
            }
    }

    fun changeQuantity(
        cartProduct: CartProduct,
        quantityyChanging: FirebaseCommon.QuantityChanged
    ){
        val index = cartItem.value.data?.indexOf(cartProduct)
        if(index != null && index != -1) {
            val documentId = cartProductDocument[index].id
            when(quantityyChanging){
                FirebaseCommon.QuantityChanged.INCREASE->{
                    increaseQuantity(documentId)
                }
                FirebaseCommon.QuantityChanged.DECREASE->{
                    if(cartProduct.quantity == 1){
                        viewModelScope.launch { _deleteAlertDialog.emit(cartProduct)}
                        return
                    }
                    decreaseQuantity(documentId)
                }
            }
        }
    }

    private fun decreaseQuantity(documentId: String) {
        firebaseCommon.decreaseQuantity(documentId){result,e->
            if(e != null){
                viewModelScope.launch { _cartItem.emit(Resource.Error(e.message.toString())) }
            }
        }
    }

    private fun increaseQuantity(documentId: String) {
        firebaseCommon.increaseQuantity(documentId){result,e->
            if(e != null){
                viewModelScope.launch { _cartItem.emit(Resource.Error(e.message.toString())) }
            }
        }
    }


}