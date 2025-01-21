package com.example.craftiza.pages.beforeAuth
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
    var isPasswordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val user by loginvm.user.observeAsState()

    LaunchedEffect(user) {
        if(user != null && user?.id != 0) {
            navController.navigate(Route.PostAuth) {
                popUpTo(Route.PreAuth) {
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
            modifier = Modifier.fillMaxSize().imePadding()
        ) {
            Image(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = "My Logo",
                modifier = Modifier.weight(1f).fillMaxWidth(),
                alignment = Alignment.Center
            )
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()).fillMaxSize()
                    .clip(shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(color = Color.White)
                    .padding(top = 40.dp, start = 20.dp, end = 20.dp, bottom = 40.dp).weight(2f)
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
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true
                )
                HeightSpacer(10)
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
                    },
                    singleLine = true,
                    visualTransformation = if(isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                isPasswordVisible = !isPasswordVisible
                            }
                        ) {
                            Icon(
                                painter = painterResource(
                                    if(isPasswordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                                ),
                                contentDescription = "Toggle Password"
                            )
                        }
                    }
                )
                HeightSpacer(20)
                Button(
                    onClick = {
                        focusManager.clearFocus()
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