package com.example.craftiza.pages.beforeAuth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.craftiza.R
import com.example.craftiza.pages.component.HeightSpacer
import com.example.craftiza.vm.SignupVM

@Composable
fun SignupPage(
    navController: NavController
){
    val signupvm : SignupVM = viewModel()

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
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Name")
                    }
                )
                HeightSpacer(30)
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Email")
                    }
                )
                HeightSpacer(30)
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Password")
                    }
                )
                HeightSpacer(30)
                Button(
                    onClick = {

                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Sign Up")
                }
                Button(
                    onClick = {
                        navController.navigateUp()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Already Have An Account? Login")
                }
            }

        }
    }
}