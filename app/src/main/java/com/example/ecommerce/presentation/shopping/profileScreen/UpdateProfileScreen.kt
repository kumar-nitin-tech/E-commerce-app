package com.example.ecommerce.presentation.shopping.profileScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ecommerce.R
import com.example.ecommerce.constants.Resource
import com.example.ecommerce.dataModel.User
import com.example.ecommerce.presentation.shopping.bottomNavComponents.BottomNavItems
import kotlinx.coroutines.flow.collectLatest

@Composable
fun UpdateProfileScreen(navController: NavHostController){
    val context = LocalContext.current
    val profileScreenViewModel:ProfileScreenViewModel = hiltViewModel()
    val firstName = remember {
        mutableStateOf("")
    }
    val lastName = remember {
        mutableStateOf("")
    }
    val emailId = remember {
        mutableStateOf("")
    }

    val showLoading = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        profileScreenViewModel.user.collectLatest {
            when (it) {
                is Resource.Error -> {

                }

                is Resource.Loading -> {

                }

                is Resource.Success -> {
                    it.data?.let {user->
                        firstName.value = user.firstName
                        lastName.value = user.lastName
                        emailId.value = user.emailId
                    }
                }

                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if(showLoading.value){
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
            )
        }
        Text(
            text = "My Profile",
            modifier = Modifier.padding(top = 10.dp, start = 16.dp),
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 24.sp,
                fontWeight = FontWeight(800)
            )
        )



        OutlinedTextField(
            value = firstName.value,
            onValueChange = { firstName.value = it },
            label = { Text(text = "First Name")},
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

        OutlinedTextField(
            value = lastName.value,
            onValueChange = { lastName.value = it },
            label = { Text(text = "Last Name")},
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

        OutlinedTextField(
            value = emailId.value,
            onValueChange = { emailId.value = it },
            label = { Text(text = "Email Id")},
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

        Button(
            onClick = {
                val user = User(firstName.value,lastName.value, emailId.value)
                profileScreenViewModel.updateUser(user)
                navController.navigate(BottomNavItems.Profile.routes){
                    popUpTo(BottomNavItems.Profile.routes){
                        inclusive = true
                    }
                }
            },
            modifier = Modifier
                .padding(start = 16.dp, top = 30.dp)
                .align(Alignment.CenterHorizontally),

        ) {
            Text(text = "Save")
        }
    }
}

@Preview
@Composable
fun ShowUpdateProfileScreen(){
    UpdateProfileScreen(rememberNavController())
}