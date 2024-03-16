package com.example.ecommerce.dataModel

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.ecommerce.constants.Constants
import com.example.ecommerce.constants.Constants.LOGIN_SUCCESS
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.datastore : DataStore<Preferences> by preferencesDataStore(Constants.LOGIN_DATA)
class LoginState(context:Context) {
    private val datastore = context.datastore

    private object PreferenceKey{
        val loginComplete = booleanPreferencesKey(LOGIN_SUCCESS)
    }
    suspend fun saveLoginState(loginComplete:Boolean){
        datastore.edit {
            it[PreferenceKey.loginComplete] = loginComplete
        }
    }

    suspend fun readLoginState(): Flow<Boolean> {
        return datastore.data
            .catch {exception->
                if(exception is IOException){
                    emit(emptyPreferences())
                }else{
                    throw exception
                }
            }.map {
                val loginState = it[PreferenceKey.loginComplete] ?: false
                loginState
            }
    }
}