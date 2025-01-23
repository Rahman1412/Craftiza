package com.example.craftiza.pages.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CenterErrorText(message: String = "",refresh : () -> Unit,isDisplay:Boolean = true){
    Box(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        contentAlignment = Alignment.Center
    ){
        Column {
            Text(message)
            if(isDisplay) {
                ElevatedButton(
                    onClick = {
                        refresh()
                    }
                ) {
                    Text("Refresh Now")
                }
            }
        }
    }
}