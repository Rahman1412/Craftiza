package com.example.craftiza.navigation

sealed class BottomBarRoute(val route:String) {
    object Home : BottomBarRoute("home");
    object Category : BottomBarRoute("category");
    object Profile : BottomBarRoute("profile");
}