package com.example.ecommerce.presentation.shopping.addressScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ecommerce.R
import com.example.ecommerce.constants.Resource
import com.example.ecommerce.dataModel.Address
import com.example.ecommerce.presentation.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun AddNewAddressScreen(
    navController: NavHostController
){
    val context = LocalContext.current
    val addressViewModel:AddressViewModel = hiltViewModel()
    var showLoading by remember{ mutableStateOf(false) }

    val inputErrorState by addressViewModel.error.collectAsState(initial = "")
    var showError by remember {
        mutableStateOf("")
    }

    if(showLoading){
        CircularProgressIndicator()
    }
    if(showError != ""){
        Toast.makeText(context,showError,Toast.LENGTH_SHORT).show()
    }

    if(inputErrorState.isNotEmpty()){
        Toast.makeText(context,inputErrorState,Toast.LENGTH_SHORT).show()
    }

    var addressLocation by remember{
        mutableStateOf("")
    }
    var fullName by remember {
        mutableStateOf("")
    }
    var street by remember {
        mutableStateOf("")
    }
    var phoneNumber by remember {
        mutableStateOf("")
    }
    var city by remember {
        mutableStateOf("")
    }
    var state by remember {
        mutableStateOf("")
    }
    var pincode by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Add New Address",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, top = 10.dp),
                style = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontSize = 24.sp,
                    color = Color.Black,
                    fontWeight = FontWeight(800)
                )
            )

            OutlinedTextField(
                value = addressLocation,
                onValueChange = { addressLocation = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, end = 40.dp, top = 5.dp),
                shape = RoundedCornerShape(topStart = 14.dp, topEnd = 0.dp, bottomEnd = 14.dp),
                textStyle = TextStyle(
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight(500)
                ),
                label = {
                    Text(
                        text = "Address location Ex. Home",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontSize = 16.sp
                        )
                    )
                }
            )

            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
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
                label = {
                    Text(
                        text = "Full Name",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontSize = 16.sp
                        )
                    )
                }
            )
            OutlinedTextField(
                value = street,
                onValueChange = { street = it },
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
                label = {
                    Text(
                        text = "Street Name",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontSize = 16.sp
                        )
                    )
                }
            )
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
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
                label = {
                    Text(
                        text = "Phone Number",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontSize = 16.sp
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
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
                label = {
                    Text(
                        text = "City Name",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontSize = 16.sp
                        )
                    )
                }
            )
            OutlinedTextField(
                value = state,
                onValueChange = { state = it },
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
                label = {
                    Text(
                        text = "State Name",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontSize = 16.sp
                        )
                    )
                }
            )
            OutlinedTextField(
                value = pincode,
                onValueChange = { pincode = it },
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
                label = {
                    Text(
                        text = "Pin Code",
                        style = TextStyle(
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            fontSize = 16.sp
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = {

            }) {
                Text(text = "Delete")
            }
            Button(onClick = {
                addressViewModel.addAddress(Address(addressLocation,fullName,street,phoneNumber,city,state,pincode))
                addressViewModel.viewModelScope.launch{
                    when(addressViewModel.addNewAddress.value){
                        is Resource.Error -> {
                            showError = addressViewModel.addNewAddress.value.message.toString()
                        }
                        is Resource.Loading -> {
                            showLoading = true
                        }
                        is Resource.Success -> {
                            showLoading = false
                            navController.navigate(Screen.AddressScreen.route){
                                popUpTo(Screen.AddressScreen.route){
                                    inclusive = true
                                }
                            }
                        }
                        else -> Unit
                    }
                }
                navController.navigate(Screen.AddressScreen.route){
                    popUpTo(Screen.AddNewAddressScreen.route){
                        inclusive = true
                    }
                }
            }) {
                Text(text = "Save")
            }
        }
    }
}

@Preview
@Composable
fun ShowAddNewAddressScreen(){
    AddNewAddressScreen(rememberNavController())
}