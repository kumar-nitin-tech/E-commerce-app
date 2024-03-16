package com.example.ecommerce.model.loginViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.constants.Constants.USER_COLLECTION
import com.example.ecommerce.constants.Resource
import com.example.ecommerce.constants.checkValidation
import com.example.ecommerce.dataModel.LoginState
import com.example.ecommerce.dataModel.User
import com.example.ecommerce.utils.RegisterFailedState
import com.example.ecommerce.utils.validateEmail
import com.example.ecommerce.utils.validatePassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val loginState: LoginState
): ViewModel() {

    //We don't want to expose the state of the app to public we create a copy of private for public
    private val _register = MutableStateFlow<Resource<User>>(Resource.Loading())
    val register = _register.asStateFlow()

    private val _validation = Channel<RegisterFailedState>()
    val validation = _validation.receiveAsFlow()
    fun createUserEmailAndPassword(user:User, password: String) {
        if (checkValidation(user, password)) {
            firebaseAuth.createUserWithEmailAndPassword(user.emailId, password)
                .addOnSuccessListener { result ->
                    result.user?.let {
                        saveUserInfo(it.uid,user)
                        //_register.value = Resource.Success()
                    }
                }.addOnFailureListener {
                    viewModelScope.launch {
                        _register.emit(Resource.Error(it.message.toString()))
                    }
                }
        }
        else{
            val registerFailedState = RegisterFailedState(
                validateEmail(user.emailId),
                validatePassword(password)
            )
            runBlocking {
                _validation.send(registerFailedState)
            }
        }
    }

    private fun saveUserInfo(userUid: String,user:User) {
        db.collection(USER_COLLECTION)
            .document(userUid)
            .set(user)
            .addOnSuccessListener {
                viewModelScope.launch {
                    _register.emit(Resource.Success(user))
                }
            }
            .addOnFailureListener {
                viewModelScope.launch {
                    _register.emit(Resource.Error(it.message.toString()))
                }
            }
    }

    fun saveLoginState(loginSuccess: Boolean){
        viewModelScope.launch {
            loginState.saveLoginState(loginSuccess)
        }
    }
}

