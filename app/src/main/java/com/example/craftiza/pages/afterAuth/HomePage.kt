package com.example.craftiza.pages.afterAuth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.craftiza.navigation.AfterAuthRoute
import com.example.craftiza.navigation.Route
import com.example.craftiza.vm.HomeVM

@Composable
fun HomePage(
    navController: NavController
){

    val homevm : HomeVM = hiltViewModel()
    val token by homevm.token.observeAsState()

    LaunchedEffect(token) {
        if(token?.accessToken == ""){
            navController.navigate(Route.PreAuth){
                popUpTo(AfterAuthRoute.Home.route){
                    inclusive = true
                }
            }
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier.padding(padding)
        ) {
            Text("${token?.accessToken}")
            Button(
                onClick = {
                    homevm.doLogOut()
                }
            ) {
                Text("LogOut")
            }
        }
    }
}