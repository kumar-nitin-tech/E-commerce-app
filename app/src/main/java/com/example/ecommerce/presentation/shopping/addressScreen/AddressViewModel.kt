package com.example.ecommerce.presentation.shopping.addressScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.constants.Resource
import com.example.ecommerce.dataModel.Address
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth:FirebaseAuth
): ViewModel() {

    private val _addNewAddress = MutableStateFlow<Resource<Address>>(Resource.Unspecified())
    val addNewAddress = _addNewAddress.asStateFlow()

    //when we trigger a state or function once we shared flow
    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    private val _address = MutableStateFlow<Resource<List<Address>>>(Resource.Unspecified())
    val address = _address.asStateFlow()

    private var addressDocument = emptyList<DocumentSnapshot>()

    init {
        getUserAddress()
    }
    private fun getUserAddress(){
        viewModelScope.launch {
            _address.emit(Resource.Loading())
        }
        firestore.collection("user")
            .document(auth.uid!!)
            .collection("address")
            .addSnapshotListener{value, error->
                if(error != null){
                    viewModelScope.launch { _address.emit(Resource.Error(error.message.toString())) }
                    return@addSnapshotListener
                }
                else{
                    val addresses = value?.toObjects(Address::class.java)
                    viewModelScope.launch { _address.emit(Resource.Success(addresses!!)) }
                }
            }
    }
    fun addAddress(address: Address){
        val validateInput = validateInput(address)
        if(validateInput) {
            viewModelScope.launch { _addNewAddress.emit(Resource.Loading()) }
            firestore.collection("user")
                .document(auth.uid!!)
                .collection("address")
                .document()
                .set(address)
                .addOnSuccessListener {
                    viewModelScope.launch { _addNewAddress.emit(Resource.Success(address)) }
                }
                .addOnFailureListener {
                    viewModelScope.launch { _addNewAddress.emit(Resource.Error(it.message.toString())) }
                }
        }else{
            viewModelScope.launch { _error.emit("All Fields are required") }
        }
    }

    fun deleteAddress(deleteAddress: Address){
        val index = address.value.data?.indexOf(deleteAddress)
        if(index != null && index != -1){
            val documentId = addressDocument[index].id
            firestore.collection("user").document(auth.uid!!).collection("address")
                .document(documentId).delete()
        }
    }

    private fun validateInput(address: Address): Boolean {
        return address.addressTitle.isNotEmpty() &&
                address.city.isNotEmpty() &&
                address.phone.isNotEmpty() &&
                address.pincode.isNotEmpty() &&
                address.fullName.isNotEmpty() &&
                address.state.isNotEmpty() &&
                address.street.isNotEmpty()
    }
}