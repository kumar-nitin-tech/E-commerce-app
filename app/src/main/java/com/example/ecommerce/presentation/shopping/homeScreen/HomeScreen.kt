package com.example.ecommerce.presentation.shopping.homeScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.ecommerce.model.shoppingViewModel.HomeScreenViewModel
import com.example.ecommerce.presentation.shopping.components.ListProductComponent
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController
){
    //NavGraph(navController = rememberNavController())
    val homeScreenViewModel:HomeScreenViewModel = hiltViewModel()
    val listOnePieceProduct by homeScreenViewModel.productOnePieceItem.collectAsState(initial = emptyList())
    val listNarutoProduct by homeScreenViewModel.productNarutoItem.collectAsState(initial = emptyList())
    val listMHAProduct by homeScreenViewModel.productMHAItem.collectAsState(initial = emptyList())
    val listDemonProduct by homeScreenViewModel.productDemonItem.collectAsState(initial = emptyList())


    val pagerState = androidx.compose.foundation.pager.rememberPagerState {
        3
    }
    val bannerImageSlides by homeScreenViewModel.productBannerItem.collectAsState(initial = emptyList())



    LaunchedEffect(Unit){
        while (true){
            yield()
            delay(2000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1)% (pagerState.pageCount)
            )
        }
    }



    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Spacer(modifier = Modifier.height(10.dp))

            HorizontalPager(
                state = pagerState,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if(bannerImageSlides.isNotEmpty())
                        BannerCardComponent(product = bannerImageSlides[it],navController)
                }
            }
        ListProductComponent(
            title = "One Piece",
            productItem = listOnePieceProduct,
            onClick = {

            },
            navController = navController
        )

        ListProductComponent(
            title = "Naruto",
            productItem = listNarutoProduct,
            onClick = { /*TODO*/ },
            navController = navController
        )

        ListProductComponent(
            title = "My Hero Academia",
            productItem = listMHAProduct,
            onClick = { /*TODO*/ },
            navController = navController
        )

        ListProductComponent(
            title = "Demon Slayer",
            productItem = listDemonProduct,
            onClick = { /*TODO*/ },
            navController = navController
        )
    }
}


@Preview
@Composable
fun ShowPreview(){
    HomeScreen(rememberNavController())
}
