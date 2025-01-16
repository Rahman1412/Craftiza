package com.example.craftiza.navigation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.craftiza.pages.SplashPage
import com.example.craftiza.pages.afterAuth.HomePage

@Composable
fun Navigation(){
    val navController = rememberNavController();

    NavHost(
        navController = navController,
        startDestination = Route.Splash
    ){
        BeforeAuthNavigation(navController)
        composable(Route.Splash){
            SplashPage(navController)
        }
        composable(AfterAuthRoute.Home.route) {
            HomePage(navController)
        }
    }
}