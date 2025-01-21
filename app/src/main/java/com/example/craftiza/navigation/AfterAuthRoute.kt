package com.example.craftiza.navigation

sealed class AfterAuthRoute(val route : String) {
    object Home: AfterAuthRoute("home");
    object UpdateProfile : AfterAuthRoute("update-profile")
}