package com.example.ecommerce.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ecommerce.R
import com.example.ecommerce.presentation.navigation.Screen
import com.example.ecommerce.ui.theme.GLightRed
import com.example.ecommerce.ui.theme.bottomGradient

@Composable
fun LoginPageScreen(
    navController: NavHostController
){
    //App Name: Kawai Corner
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
            .padding(top = 26.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Kawai Corner",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontFamily = FontFamily(
                    Font(R.font.nicomoji_regular)
                ),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = Color.Black
            )
        )
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(id = R.drawable.loginbg),
            contentDescription = "Login Background",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(360.dp)
                .height(495.dp)
        )

        Text(
            text = "Get your Favourite Anime at your home",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            style = TextStyle(
                fontFamily = FontFamily(
                    Font(R.font.nicomoji_regular)
                ),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = Color.Black
            )
        )
        Spacer(modifier = Modifier.height(22.dp))

        Button(
            onClick = {
                      navController.navigate(Screen.RegisterScreen.route)
            },
            colors = ButtonDefaults.buttonColors(GLightRed),
            shape = RoundedCornerShape(topStart = 14.dp, bottomEnd = 14.dp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(210.dp)
            ) {
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

        Spacer(modifier = Modifier.height(25.dp))

        Row (
            modifier = Modifier.align(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.Bottom
        ){
            Text(
                text = "Already have a account?",
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppins_regular, FontWeight(500))),
                    fontSize = 14.sp,
                    color = Color.Black
                )
                )
            Spacer(modifier = Modifier.width(10.dp))
            ClickableText(
                text = AnnotatedString("Sign in"),
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppins_regular, FontWeight(500))),
                    fontSize = 14.sp,
                    color = GLightRed
                ),
                onClick = {
                    navController.navigate(Screen.LoginScreen.route)
                }
                )
        }

    }
}


@Preview
@Composable
fun ShowLoginPageScreen(){
    LoginPageScreen(navController = rememberNavController())
}
