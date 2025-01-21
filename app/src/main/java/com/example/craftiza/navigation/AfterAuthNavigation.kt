package com.example.craftiza.navigation

import androidx.compose.runtime.internal.composableLambda
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.craftiza.pages.afterAuth.HomePage
import com.example.craftiza.pages.afterAuth.UpdateProfilePage


fun NavGraphBuilder.AfterAuthNavigation(
    navController: NavController
){
    navigation(
        route = Route.PostAuth,
        startDestination = AfterAuthRoute.Home.route
    ){
        composable(
            AfterAuthRoute.Home.route
        ) {
            HomePage(navController);
        }

        composable(AfterAuthRoute.UpdateProfile.route){
            UpdateProfilePage(navController)
        }

    }
}