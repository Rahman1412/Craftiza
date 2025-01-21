package com.example.craftiza.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.craftiza.pages.afterAuth.bottomScreen.CategoryScreen
import com.example.craftiza.pages.afterAuth.bottomScreen.HomeScreen
import com.example.craftiza.pages.afterAuth.bottomScreen.ProfileScreen

@Composable
fun BottomBarNavigation(
    navController: NavController,
    bottomNavController: NavHostController,
    paddingValues: PaddingValues
){
    NavHost(
        navController = bottomNavController,
        startDestination = BottomBarRoute.Home.route
    ){
        composable(BottomBarRoute.Home.route){
            HomeScreen(paddingValues)
        }
        composable(BottomBarRoute.Category.route){
            CategoryScreen(paddingValues)
        }
        composable(BottomBarRoute.Profile.route){
            ProfileScreen(
                navController,
                bottomNavController,
                paddingValues
            )
        }
    }
}