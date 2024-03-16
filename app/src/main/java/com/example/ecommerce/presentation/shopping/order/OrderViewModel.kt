package com.example.ecommerce.presentation.shopping.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.constants.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore,
    private val auth: FirebaseAuth
):ViewModel() {

    private val _allOrder = MutableStateFlow<Resource<List<Order>>>(Resource.Unspecified())
    val allOrder = _allOrder.asStateFlow()

    private val _order = MutableStateFlow<Resource<Order>>(Resource.Unspecified())
    val order = _order.asStateFlow()

    private val _orderDetail = MutableStateFlow<Resource<List<Order>>>(Resource.Unspecified())
    val orderDetail = _orderDetail.asStateFlow()
    init {
        getAllOrder()
    }
    private fun getAllOrder(){
        viewModelScope.launch {
            _allOrder.emit(Resource.Loading())
        }

        fireStore.collection("user").document(auth.uid!!).collection("orders").get()
            .addOnSuccessListener {
                val order = it.toObjects(Order::class.java)
                viewModelScope.launch {
                    _allOrder.emit(Resource.Success(order))
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _allOrder.emit(Resource.Error(it.message.toString()))
                }
            }
    }

    fun getOrderDetail(orderId: Long){
        viewModelScope.launch {
            _orderDetail.emit(Resource.Loading())
        }

        fireStore.collection("user").document(auth.uid!!).collection("orders")
            .whereEqualTo("orderId",orderId)
            .get()
            .addOnSuccessListener {
                val orderDetail = it.toObjects(Order::class.java)
                viewModelScope.launch{
                    _orderDetail.emit(Resource.Success(orderDetail))
                }
            }
            .addOnFailureListener {
                viewModelScope.launch {
                    _orderDetail.emit(Resource.Error(it.message.toString()))
                }
            }
    }

    fun placeOrder(order:Order){
        viewModelScope.launch {
            _order.emit(Resource.Loading())
        }

        fireStore.runBatch { batch->
            //add to the user order list
            fireStore.collection("user").document(auth.uid!!)
                .collection("orders")
                .document()
                .set(order)
            //add to the order
            fireStore.collection("orders").document().set(order)

            //delete the order from cart
            fireStore.collection("user").document(auth.uid!!).collection("cart").get()
                .addOnSuccessListener {
                    it.documents.forEach {
                        it.reference.delete()
                    }
                }
        }.addOnSuccessListener {
            viewModelScope.launch {
                _order.emit(Resource.Success(order))
            }
        }.addOnFailureListener {
            viewModelScope.launch {
                _order.emit(Resource.Error(it.message.toString()))
            }
        }
    }
}