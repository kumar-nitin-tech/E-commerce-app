package com.example.ecommerce.presentation.shopping.profileScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.constants.Resource
import com.example.ecommerce.dataModel.LoginState
import com.example.ecommerce.dataModel.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val firestore:FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val loginState:LoginState
): ViewModel(){

    private val _user = MutableStateFlow<Resource<User>>(Resource.Unspecified())
    val user = _user.asStateFlow()

    private val _updateInfo = MutableStateFlow<Resource<User>>(Resource.Unspecified())
    val updateInfo = _updateInfo.asStateFlow()

    init {
        getUser()
    }

    fun getUser(){
        viewModelScope.launch {
            _user.emit(Resource.Loading())
        }
        firestore.collection("user").document(auth.uid!!).get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)
                user?.let {
                    viewModelScope.launch {
                        _user.emit(Resource.Success(it))
                    }
                }
            }
            .addOnFailureListener{
                viewModelScope.launch{
                    _user.emit(Resource.Error(it.message.toString()))
                }
            }
    }


    fun updateUser(user: User){

        viewModelScope.launch {
            _updateInfo.emit(Resource.Loading())
        }

        firestore.runTransaction { transaction->
            val documentRef = firestore.collection("user").document(auth.uid!!)
            transaction.set(documentRef,user)
        }.addOnSuccessListener {
            viewModelScope.launch {
                _updateInfo.emit(Resource.Success(user))
            }
        }.addOnFailureListener{
            viewModelScope.launch {
                _updateInfo.emit(Resource.Error(it.message.toString()))
            }
        }
    }

    fun logout(){
        auth.signOut()
        viewModelScope.launch {
            loginState.saveLoginState(false)
        }
    }
}