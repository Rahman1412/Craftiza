package com.example.craftiza.pages.beforeAuth
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.craftiza.R
import com.example.craftiza.navigation.AfterAuthRoute
import com.example.craftiza.navigation.BeforeAuthRoute
import com.example.craftiza.navigation.Route
import com.example.craftiza.pages.component.HeightSpacer
import com.example.craftiza.pages.component.Loader
import com.example.craftiza.vm.LoginVM

@Composable
fun LoginPage(
    navController: NavController
){

    val loginvm : LoginVM = hiltViewModel();
    val email = loginvm.email.collectAsState()
    val password = loginvm.password.collectAsState()
    val token by loginvm.token.observeAsState()
    val isLoading by loginvm.isLoading.collectAsState()

    LaunchedEffect(token) {
        if(token?.accessToken != "" && token?.accessToken != null){
            navController.navigate(AfterAuthRoute.Home.route){
                popUpTo(Route.PreAuth){
                    inclusive = true
                }
            }
        }
    }
    if(isLoading){
        Loader()
    }
    Scaffold { padding->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = "My Logo",
                modifier = Modifier.weight(1f).fillMaxWidth(),
                alignment = Alignment.Center
            )
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()).fillMaxSize().background(color = Color.White, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .padding(top = 40.dp, start = 20.dp, end = 20.dp, bottom = 20.dp).weight(2f)
            ) {
                OutlinedTextField(
                    value = email.value.value,
                    onValueChange = {
                        loginvm.setEmail(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Email")
                    },
                    isError = email.value.isError,
                    supportingText = {
                        Text(email.value.error)
                    }
                )
                HeightSpacer(20)
                OutlinedTextField(
                    value = password.value.value,
                    onValueChange = {
                        loginvm.setPassword(it)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Password")
                    },
                    isError = password.value.isError,
                    supportingText = {
                        Text(password.value.error)
                    }
                )
                HeightSpacer(30)
                Button(
                    onClick = {
                        loginvm.doLogin()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = email.value.isEnable && password.value.isEnable
                ) {
                    Text("Login")
                }
                Button(
                    onClick = {
                        navController.navigate(BeforeAuthRoute.SingUp.route)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Create An Account")
                }
            }
        }
    }
}