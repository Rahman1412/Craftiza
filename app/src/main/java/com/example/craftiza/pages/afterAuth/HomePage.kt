package com.example.craftiza.pages.afterAuth

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.craftiza.R
import com.example.craftiza.data.BottomNavItem
import com.example.craftiza.navigation.AfterAuthRoute
import com.example.craftiza.navigation.BottomBarNavigation
import com.example.craftiza.navigation.BottomBarRoute
import com.example.craftiza.navigation.Route
import com.example.craftiza.vm.HomeVM

@Composable
fun HomePage(
    navController: NavController
){
    val bottomNavController = rememberNavController();
    val backStackEntry = bottomNavController.currentBackStackEntryAsState();
    val homevm: HomeVM = hiltViewModel()
    val token by homevm.token.observeAsState();
    val user by homevm.user.observeAsState()

    LaunchedEffect(user) {
        if(user != null && user?.id == 0){
            navController.navigate(Route.PreAuth){
                popUpTo(Route.PostAuth){
                    inclusive = true
                }
            }
        }
    }

    val navBarItems = listOf(
        BottomNavItem(name = "Home", route = BottomBarRoute.Home.route, icon = R.drawable.ic_home),
        BottomNavItem(name = "Category", route = BottomBarRoute.Category.route, icon = R.drawable.ic_category),
        BottomNavItem(name = "Profile", route = BottomBarRoute.Profile.route, icon = R.drawable.ic_profile)
    );

    Scaffold(
        bottomBar = {
            BottomAppBar {
                navBarItems.forEach{ item->
                    val selected = item.route == backStackEntry.value?.destination?.route;
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            bottomNavController.navigate(item.route){
                                popUpTo(bottomNavController.graph.startDestinationId){
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(painter = painterResource(item.icon), contentDescription = item.name)
                        },
                        label = {
                            Text(item.name)
                        }
                    )
                }
            }
        }
    ){ padding ->
        BottomBarNavigation(
            navController,
            bottomNavController,
            padding
        );
    }
}