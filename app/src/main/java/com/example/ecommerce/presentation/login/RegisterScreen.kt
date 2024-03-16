package com.example.ecommerce.presentation.login

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ecommerce.R
import com.example.ecommerce.constants.Resource
import com.example.ecommerce.dataModel.User
import com.example.ecommerce.model.loginViewModel.RegisterViewModel
import com.example.ecommerce.presentation.navigation.Screen
import com.example.ecommerce.ui.theme.GLightRed
import com.example.ecommerce.ui.theme.bottomGradient
import com.example.ecommerce.utils.RegisterValidation
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    navController: NavHostController,
    viewModel: RegisterViewModel = hiltViewModel()
){
    var isLoading by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    var errorEmailMessage by remember {
        mutableStateOf("")
    }
    var errorPassMessage by remember{
        mutableStateOf("")
    }
    LaunchedEffect(viewModel.validation){
        viewModel.validation.collect{
            if(it.email is RegisterValidation.Failed){
                errorEmailMessage = it.email.message
            }

            if(it.password is RegisterValidation.Failed){
                errorPassMessage = it.password.message
            }
        }
    }
    val columnSize = with(LocalDensity.current) { 300.dp.toPx() }
    Column(
        Modifier
            .background(
                Brush.linearGradient(
                    colors = listOf(GLightRed, bottomGradient),
                    start = Offset(0f, 0f),
                    end = Offset(columnSize, columnSize)
                )
            )
            .fillMaxSize()
            .fillMaxHeight()
            .padding(top = 26.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create a account...",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
                .padding(start = 25.dp),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.nicomoji_regular)),
                fontSize = 20.sp,
                color = Color.Black
            )
        )
        Row (
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ){
            Text(
                text = "Already have a Account?",
                modifier = Modifier
                    .padding(start = 25.dp),
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    color = Color.Black,
                )
            )
            ClickableText(
                text = AnnotatedString("Sign In"),
                onClick = {
                          navController.navigate(Screen.LoginScreen.route){
                              popUpTo(Screen.RegisterScreen.route){
                                  inclusive = true
                              }
                          }
                },
                modifier = Modifier.padding(start = 5.dp),
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    color = GLightRed,
                    fontWeight = FontWeight(1000)
                )
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            text = " First Name",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 25.dp),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight(500)
            )
        )
        var name by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = name,
            onValueChange = {name = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 40.dp, top = 5.dp),
            shape = RoundedCornerShape(topStart = 14.dp, topEnd = 0.dp, bottomEnd = 14.dp),
            maxLines = 1,
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight(500)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        Spacer(modifier = Modifier.height(23.dp))
        Text(
            text = " Last Name",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 25.dp),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight(500)
            )
        )
        var lastname by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = lastname,
            onValueChange = {lastname = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 40.dp, top = 5.dp),
            shape = RoundedCornerShape(topStart = 14.dp, topEnd = 0.dp, bottomEnd = 14.dp),
            maxLines = 1,
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight(500)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )
        Spacer(modifier = Modifier.height(23.dp))
        Text(
            text = "Email Id",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 25.dp),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight(500)
            )
        )
        var email by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 40.dp, top = 5.dp),
            shape = RoundedCornerShape(topStart = 14.dp, topEnd = 0.dp, bottomEnd = 14.dp),
            maxLines = 1,
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight(500)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = errorEmailMessage.isNotEmpty(),
            supportingText = {
                if(errorEmailMessage.isNotEmpty()){
                    Text(text = errorEmailMessage, color = Color.Red)
                }else{
                    errorEmailMessage = ""
                }
            },
            trailingIcon = {
                if(errorEmailMessage.isNotEmpty()){
                    androidx.compose.material3.Icon(painter = painterResource(id = R.drawable.baseline_error_24), contentDescription ="" )
                }else{
                    errorEmailMessage = ""
                }
            },
            keyboardActions = KeyboardActions{errorEmailMessage.isEmpty()}
        )

        Spacer(modifier = Modifier.height(23.dp))
        Text(
            text = "Password",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 25.dp),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight(500)
            )
        )
        var password by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 40.dp, top = 5.dp),
            shape = RoundedCornerShape(topStart = 14.dp, topEnd = 0.dp, bottomEnd = 14.dp),
            maxLines = 1,
            textStyle = TextStyle(
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight(500)
            ),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = errorPassMessage.isNotEmpty(),
            supportingText = {
                if(errorPassMessage.isNotEmpty()){
                    Text(text = errorPassMessage, color = Color.Red)
                }else{
                    errorPassMessage = ""
                }
            },
            trailingIcon = {
                if(errorPassMessage.isNotEmpty()){
                    androidx.compose.material3.Icon(painter = painterResource(id = R.drawable.baseline_error_24), contentDescription ="" )
                }
                else{
                    errorPassMessage = ""
                }
            }

        )
        Spacer(modifier = Modifier.height(23.dp))
        Button(
            onClick = {
                viewModel.createUserEmailAndPassword(User(name, lastname, email),password)
                viewModel.viewModelScope.launch {
                    viewModel.register.collectLatest {
                        when(viewModel.register.value){
                            is Resource.Error -> {
                                Toast.makeText(context,it.message, Toast.LENGTH_SHORT).show()
                            }
                            is Resource.Loading -> {
                                isLoading = true
                            }
                            is Resource.Success -> {
                                errorEmailMessage = ""
                                errorPassMessage = ""
                                viewModel.saveLoginState(true)
                                navController.navigate("homeNav"){
                                    popUpTo("auth"){
                                        inclusive = true
                                    }
                                }
                            }
                            else-> Unit
                        }
                    }
                }

            },
            colors = ButtonDefaults.buttonColors(GLightRed),
            shape = RoundedCornerShape(topStart = 14.dp, bottomEnd = 14.dp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(210.dp)
        ) {
            if(isLoading){
                CircularProgressIndicator()
            }
            Text(
                text = "Register",
                modifier = Modifier
                    .fillMaxWidth(),
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(R.font.nicomoji_regular)
                    ),
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    color = Color.Black
                )

            )
        }

        Divider(
            color = Color.Black,
            modifier = Modifier.padding(20.dp)
        )

        OutlinedButton(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            shape = RoundedCornerShape(topStart = 14.dp, bottomEnd = 14.dp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(330.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.google),
                contentDescription ="",
                Modifier
                    .size(20.dp)
                    .padding(end = 5.dp)
                )
            Text(
                text = "Continue with Google",
                modifier = Modifier.align(Alignment.CenterVertically),
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(R.font.nicomoji_regular)
                    ),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color.Black
                )

            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            shape = RoundedCornerShape(topStart = 14.dp, bottomEnd = 14.dp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(330.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.facebook),
                contentDescription ="",
                Modifier
                    .size(20.dp)
                    .padding(end = 5.dp)
            )
            Text(
                text = "Continue with Facebook",
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(R.font.nicomoji_regular)
                    ),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = Color.Black
                )

            )
        }

    }
}

@Preview
@Composable
fun ShowPreview(){
    RegisterScreen(rememberNavController())
}

