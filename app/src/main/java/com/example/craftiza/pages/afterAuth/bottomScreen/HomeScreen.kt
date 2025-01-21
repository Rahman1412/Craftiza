package com.example.craftiza.pages.afterAuth.bottomScreen

import android.graphics.ImageDecoder
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.example.craftiza.R
import com.example.craftiza.pages.component.CenterCircularProgress
import com.example.craftiza.pages.component.CenterErrorText
import com.example.craftiza.pages.component.ProductTitle
import com.example.craftiza.vm.HomeVM

@Composable
fun HomeScreen(
    paddingValues: PaddingValues
){
    val homevm : HomeVM = hiltViewModel()
    val products = homevm.products.collectAsLazyPagingItems()

    val refresh : () -> Unit = {
        products.refresh();
    }

    Box(
        modifier = Modifier.fillMaxSize().padding(paddingValues)
    ){
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            items(products.itemCount){ index ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(start = 15.dp, end = 15.dp, top = 5.dp , bottom = 5.dp,),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Row {
                        AsyncImage(
                            model = products[index]?.images?.get(0),
                            contentDescription = "Image",
                            placeholder = painterResource(R.drawable.ic_loading),
                            error = painterResource(R.drawable.ic_warning),
                            modifier = Modifier
                                .size(120.dp),
                            contentScale = ContentScale.Crop,
                        )
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            ProductTitle(products[index]?.title ?: "")
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                ("$" + products[index]?.price.toString()) ?: "",
                                style = TextStyle(
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }
                }
            }
            products.apply {
                when{
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            CenterCircularProgress()
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        item {
                            CenterCircularProgress()
                        }
                    }
                    loadState.refresh is LoadState.Error -> {
                        item {
                            CenterErrorText("Data not found",refresh);
                        }
                    }
                }
            }
        }
    }
}