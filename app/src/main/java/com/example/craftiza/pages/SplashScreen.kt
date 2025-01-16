package com.example.craftiza.pages
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.craftiza.R
import com.example.craftiza.navigation.AfterAuthRoute
import com.example.craftiza.navigation.BeforeAuthRoute
import com.example.craftiza.navigation.Route
import com.example.craftiza.vm.SplashVM
import kotlinx.coroutines.delay

@Composable
fun SplashPage(
    navController: NavController
){
    val vm : SplashVM = hiltViewModel()
    val token by vm.token.observeAsState()
    val user by vm.user.observeAsState()

    LaunchedEffect(Unit) {
        delay(2000)
        if(token?.accessToken != ""){
            navController.navigate(AfterAuthRoute.Home.route){
                popUpTo(Route.Splash){
                    inclusive = true
                }
            }
        }else{
            navController.navigate(Route.PreAuth){
                popUpTo(Route.Splash){
                    inclusive = true
                }
            }
        }

    }

    Scaffold { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = "Splash Icon"
            )
        }
    }
}