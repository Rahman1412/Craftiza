package com.example.craftiza.pages
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.craftiza.R
import com.example.craftiza.navigation.BeforeAuthRoute
import com.example.craftiza.navigation.Route
import kotlinx.coroutines.delay

@Composable
fun SplashPage(
    navController: NavController
){
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate(Route.PreAuth){
            popUpTo(Route.Splash){
                inclusive = true
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