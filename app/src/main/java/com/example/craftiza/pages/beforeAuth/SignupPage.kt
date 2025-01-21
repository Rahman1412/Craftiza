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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.craftiza.R
import com.example.craftiza.navigation.BeforeAuthRoute
import com.example.craftiza.pages.component.BottomSheet
import com.example.craftiza.pages.component.HeightSpacer
import com.example.craftiza.pages.component.Loader
import com.example.craftiza.vm.SignupVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.max
import kotlin.math.sign

@Composable
fun SignupPage(
    navController: NavController
){
    val signupvm : SignupVM = hiltViewModel()
    var istrue : Boolean = false
    val coroutineScope = rememberCoroutineScope()
    val isBottomSheet by signupvm.isBottomSheet.collectAsState()

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let{
            signupvm.uploadImage(bitmap)
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let{
            signupvm.uploadFile(uri);
        }
    }

    val dismissBottomSheet : () -> Unit = {
        signupvm.dismissBottomSheet()
    }

    val launchCamera : () -> Unit = {
        coroutineScope.launch {
            cameraLauncher.launch()
        }
    }

    val launchGallery : () -> Unit = {
        coroutineScope.launch {
            galleryLauncher.launch("image/*")
        }
    }

    if(isBottomSheet){
        BottomSheet(dismissBottomSheet,launchCamera,launchGallery)
    }

    val name by signupvm.name.collectAsState()
    val email by signupvm.email.collectAsState()
    val password by signupvm.password.collectAsState()
    val image by signupvm.image.collectAsState()
    val isLoading by signupvm.isLoading.collectAsState()

    var viewPassword by remember { mutableStateOf(false) }
    val nowLogin by signupvm.nowLoggedIn.collectAsState()
    LaunchedEffect(nowLogin) {
        if(nowLogin){
            navController.navigateUp()
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
            Box(
                modifier = Modifier.weight(2f)
            ){
                Column(
                    modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                        .clip(shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                        .background(color = Color.White)
                        .padding(20.dp)

                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        contentAlignment = Alignment.Center
                    ){
                        AsyncImage(
                            model = image,
                            contentDescription = "Image",
                            placeholder = painterResource(R.drawable.ic_user),
                            error = painterResource(R.drawable.ic_user),
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop,
                        )
                        IconButton(
                            onClick = {
                                signupvm.openBottomSheet()
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
                        value = name.value,
                        onValueChange = {
                            signupvm.setName(it)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("Name")
                        },
                        isError = name.isError,
                        supportingText = {
                            Text(name.error)
                        },
                        singleLine = true
                    )
                    HeightSpacer(15)
                    OutlinedTextField(
                        value = email.value,
                        onValueChange = {
                            signupvm.setEmail(it)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("Email")
                        },
                        isError = email.isError,
                        supportingText = {
                            Text(email.error)
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )
                    HeightSpacer(15)
                    OutlinedTextField(
                        value = password.value,
                        onValueChange = {
                            signupvm.setPassword(it)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text("Password")
                        },
                        visualTransformation = if(viewPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        isError = password.isError,
                        supportingText = {
                            Text(password.error)
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    viewPassword = !viewPassword
                                }
                            ) {
                                Icon(
                                    painter = painterResource(
                                        if(viewPassword) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                                    ),
                                    contentDescription = "Toggle Password"
                                )
                            }
                        }
                    )
                    HeightSpacer(20)
                    Button(
                        onClick = {
                            signupvm.doSignup()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = name.isEnable && email.isEnable && password.isEnable
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
}