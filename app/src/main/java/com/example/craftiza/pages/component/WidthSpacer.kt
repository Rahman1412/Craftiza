package com.example.craftiza.pages.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WidthSpacer(width:Int = 0){
    Spacer(
        modifier = Modifier.width(width.dp)
    )
}