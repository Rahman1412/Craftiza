package com.example.craftiza.pages.component
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun ProductTitle(title:String = ""){
    Text(
        title,
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        ),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}