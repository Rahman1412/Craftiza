package com.example.craftiza.pages.component

import android.graphics.Camera
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.craftiza.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    dismissBottomSheet : () -> Unit,
    cameraLauncher : () -> Unit,
    galleryLauncher : () -> Unit
){
    ModalBottomSheet(
        onDismissRequest = {
            dismissBottomSheet()
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier.clickable {
                    cameraLauncher()
                }
            ){
                Icon(
                    painter = painterResource(R.drawable.is_camera_open),
                    contentDescription = "Camera",
                    modifier = Modifier.size(120.dp).padding(20.dp)
                )
            }
            Box(
                modifier = Modifier.clickable {
                        galleryLauncher()
                }
            ){
                Icon(
                    painter = painterResource(R.drawable.ic_gallery_open),
                    contentDescription = "Gallery",
                    modifier = Modifier.size(120.dp).padding(20.dp)
                )
            }
        }
    }
}