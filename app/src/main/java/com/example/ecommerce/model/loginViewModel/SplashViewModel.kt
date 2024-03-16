package com.example.ecommerce.model.loginViewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.dataModel.LoginState
import com.example.ecommerce.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val loginState: LoginState
): ViewModel() {
    private val _startDestination: MutableState<String> = mutableStateOf(Screen.SplashScreen.route)
    val startDestination = _startDestination
    private val _state = mutableStateOf(false)
    val state = _state

    init {
        viewModelScope.launch {
            loginState.readLoginState().collect(){
                _state.value = it
                if(it){
                    _startDestination.value = "homeNav"
                }else{
                    _startDestination.value = "auth"
                }
            }
        }
    }
}