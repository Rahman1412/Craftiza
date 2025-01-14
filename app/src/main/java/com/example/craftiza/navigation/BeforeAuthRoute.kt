package com.example.craftiza.navigation

sealed class BeforeAuthRoute(val route: String) {
    object Login: BeforeAuthRoute("login");
    object SingUp: BeforeAuthRoute("signup")
}