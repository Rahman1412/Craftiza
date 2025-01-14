package com.example.craftiza.pages.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HeightSpacer(height:Int = 0){
    Spacer(modifier = Modifier.height(height.dp))
}