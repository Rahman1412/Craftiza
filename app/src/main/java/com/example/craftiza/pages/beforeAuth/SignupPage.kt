package com.example.craftiza.pages.beforeAuth

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.craftiza.R
import com.example.craftiza.navigation.BeforeAuthRoute
import com.example.craftiza.pages.component.HeightSpacer
import com.example.craftiza.vm.SignupVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SignupPage(
    navController: NavController
){
    val signupvm : SignupVM = viewModel()
    var istrue : Boolean = false
    val coroutineScope = rememberCoroutineScope()

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { uri ->
        Log.d("URL","${uri}")
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
                modifier = Modifier.verticalScroll(rememberScrollState()).fillMaxSize()
                    .background(color = Color.White, shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .padding(top = 40.dp, start = 20.dp, end = 20.dp, bottom = 20.dp).weight(2f)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        painter = painterResource(R.drawable.ic_user),
                        contentDescription = "Artist image",
                        modifier = Modifier.size(120.dp)
                    )
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                withContext(Dispatchers.IO){
                                    cameraLauncher.launch()
                                }
                            }
                        },
                        modifier = Modifier.padding(
                            top = 60.dp,
                            start = 90.dp
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_camera),
                            contentDescription = "Camera"
                        )
                    }
                }
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