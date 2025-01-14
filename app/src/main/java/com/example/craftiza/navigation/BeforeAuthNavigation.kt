package com.example.craftiza.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.craftiza.pages.beforeAuth.LoginPage
import com.example.craftiza.pages.beforeAuth.SignupPage
import com.example.craftiza.vm.LoginVM

fun NavGraphBuilder.BeforeAuthNavigation(
    navController: NavController
){
    navigation(
        route = Route.PreAuth,
        startDestination =  BeforeAuthRoute.Login.route
    ){
        composable(
            BeforeAuthRoute.Login.route
        ) {
            LoginPage(navController)
        }

        composable(
            BeforeAuthRoute.SingUp.route
        ){
            SignupPage(navController)
        }
    }
}