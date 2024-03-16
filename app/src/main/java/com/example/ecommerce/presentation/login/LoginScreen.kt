package com.example.ecommerce.presentation.login

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.ecommerce.R
import com.example.ecommerce.constants.Resource
import com.example.ecommerce.model.loginViewModel.LoginViewModel
import com.example.ecommerce.presentation.navigation.Screen
import com.example.ecommerce.ui.theme.GLightRed
import com.example.ecommerce.ui.theme.bottomGradient
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
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
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start)
                .padding(start = 25.dp),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.nicomoji_regular)),
                fontSize = 26.sp,
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
                text = "Create a new Account...",
                modifier = Modifier
                    .padding(start = 25.dp),
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    color = Color.Black,
                )
                )
            ClickableText(
                text = AnnotatedString("Sign Up"),
                onClick = {
                          navController.navigate(Screen.RegisterScreen.route){
                              popUpTo(Screen.LoginScreen.route){
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
        Spacer(modifier = Modifier.height(60.dp))
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
        var emailLogin by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = emailLogin,
            onValueChange = {emailLogin = it},
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
            supportingText = {

            }
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
        var passwordLogin by remember {
            mutableStateOf("")
        }
        OutlinedTextField(
            value = passwordLogin,
            onValueChange = {passwordLogin = it},
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.height(23.dp))
        Button(
            onClick = {
                if(emailLogin.isEmpty() || passwordLogin.isEmpty()){
                    Toast.makeText(context,"Enter the credentials",Toast.LENGTH_SHORT).show()
                }else {
                    viewModel.login(emailLogin, passwordLogin)
                    scope.launch {
                        viewModel.login.collect {
                            when (it) {
                                is Resource.Success -> {
                                    viewModel.saveLoginState(true)
                                    navController.navigate("homeNav"){
                                        popUpTo("auth"){
                                            inclusive = true
                                        }
                                    }
                                }
                                else -> {
                                    viewModel.saveLoginState(false)
                                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                }
                            }
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
            Text(
                text = "Login",
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
        Spacer(modifier = Modifier.height(20.dp))
        Divider(
            color = Color.Black,
            modifier = Modifier.padding(20.dp)
        )
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

//@Preview
//@Composable
//fun ShowPreview(){
//    LoginScreen()
//}