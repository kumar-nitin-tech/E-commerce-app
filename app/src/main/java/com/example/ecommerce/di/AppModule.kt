package com.example.ecommerce.di

import android.content.Context
import com.example.ecommerce.dataModel.LoginState
import com.example.ecommerce.firebaseCommon.FirebaseCommon
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFirebaseAuthentication() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFireStore() = Firebase.firestore

    @Provides
    @Singleton
    fun provideLoginState(@ApplicationContext context: Context) = LoginState(context)

    @Provides
    @Singleton
    fun provideFirebaseCommon() = FirebaseCommon(Firebase.firestore,FirebaseAuth.getInstance())
}