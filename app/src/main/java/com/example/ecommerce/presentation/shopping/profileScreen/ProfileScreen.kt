package com.example.ecommerce.presentation.shopping.profileScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ecommerce.R
import com.example.ecommerce.presentation.navigation.Screen
import com.example.ecommerce.ui.theme.GCardBackground

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen(navController: NavHostController){
    val profileScreenViewModel:ProfileScreenViewModel = hiltViewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 30.dp)
    ) {
        Text(
            text = "Account",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular))
            ),
            modifier = Modifier.padding(start = 10.dp, bottom = 2.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = GCardBackground,
            onClick = {
                navController.navigate(Screen.UpdateProfileScreen.route)
            }
        ) {
            Row {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 10.dp, bottom = 2.dp)
                )
                Text(
                    text = "Profile",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_regular))
                    ),
                    modifier = Modifier.padding(start = 10.dp, bottom = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Order",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.poppins_regular))
            ),
        modifier = Modifier.padding(start = 10.dp, bottom = 2.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = GCardBackground,
            onClick = {
                navController.navigate(Screen.AllOrderScreen.route){
                    popUpTo(Screen.AllOrderScreen.route){
                        inclusive = true
                    }
                }
            }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                Icon(Icons.Default.ShoppingCart, contentDescription = null, modifier = Modifier.padding(start = 10.dp, bottom = 2.dp))
                Text(
                    text = "All Order",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_regular))
                    ),
                    modifier = Modifier.padding(start = 10.dp, bottom = 2.dp)
                )
                Spacer(modifier = Modifier.width(230.dp))
                Icon(
                    Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = GCardBackground,
            onClick = {

                profileScreenViewModel.logout()
                navController.navigate("auth"){
                    popUpTo("homeNav"){
                        inclusive = true
                    }
                }
                //profileScreenViewModel.logout()
//                val intent = Intent(LocalContext.current, LoginPageScreen::class.java)
//                LocalContext.current.startActivity(intent)
            }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ){
                Text(
                    text = "Logout",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_regular))
                    ),
                    modifier = Modifier.padding(start = 10.dp, bottom = 2.dp)
                )
            }
        }

    }
}

@Preview
@Composable
fun ShowProfileScreen(){
    ProfileScreen(rememberNavController())
}