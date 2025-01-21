package com.example.craftiza.pages.afterAuth

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.craftiza.R
import com.example.craftiza.pages.component.BottomSheet
import com.example.craftiza.pages.component.Loader
import com.example.craftiza.vm.UpdateProfileVM
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfilePage(
    navController: NavController
){
    val vm : UpdateProfileVM = hiltViewModel()

    val name by vm.name.collectAsState()
    val email by vm.email.collectAsState()
    val password by vm.password.collectAsState()
    var isVisible by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()
    val image by vm.image.collectAsState()
    val isLoading by vm.isLoading.collectAsState()
    val focusManager = LocalFocusManager.current
    val isBottomSheet by vm.isBottomSheet.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val dismissBottomSheet : () -> Unit = {
        vm.toggleBottomSheet(false)
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.run {
            dismissBottomSheet()
            vm.uploadFile(bitmap,"bitmap")
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.run {
            dismissBottomSheet()
            vm.uploadFile(uri,"uri")
        }
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

    if(isLoading){
        Loader()
    }

    if(isBottomSheet){
        BottomSheet(dismissBottomSheet,launchCamera,launchGallery)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Update Profile", style = TextStyle(fontWeight = FontWeight.Bold))
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        },
                        enabled = navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_back),
                            contentDescription = "Go Back"
                        )
                    }
                }
            )
        },
        modifier = Modifier.imePadding()
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
                .fillMaxSize().padding(20.dp)
                .verticalScroll(scrollState)
        ){
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
                        vm.toggleBottomSheet(true)
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
                    vm.setName(it)
                },
                label = {
                    Text("Name")
                },
                modifier = Modifier.fillMaxWidth(),
                isError = name.isError,
                supportingText = {
                    Text(name.error)
                },
                singleLine = true
            )
            OutlinedTextField(
                value = email.value,
                onValueChange = {
                    vm.setEmail(it)
                },
                label = {
                    Text("Email")
                },
                modifier = Modifier.fillMaxWidth(),
                isError = email.isError,
                supportingText = {
                    Text(email.error)
                },
                singleLine = true
            )
            OutlinedTextField(
                value = password.value,
                onValueChange = {
                    vm.setPassword(it)
                },
                label = {
                    Text("Password")
                },
                visualTransformation = if(isVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                isError = password.isError,
                supportingText = {
                    Text(password.error)
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            isVisible = !isVisible
                        }
                    ) {
                        Icon(
                            painter = painterResource(if(isVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off),
                            contentDescription = "Toggle Password"
                        )
                    }
                },
                singleLine = true
            )
            Button(
                onClick = {
                    focusManager.clearFocus()
                    vm.updateProfile()
                },
                enabled = name.isEnable && email.isEnable && password.isEnable,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Update")
            }
            Button(
                onClick = {
                    focusManager.clearFocus()
                    navController.navigateUp()
                },
                enabled = navController.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }
        }
    }
}