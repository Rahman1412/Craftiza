package com.example.craftiza.data

data class FormField(
    val value:String = "",
    val isError: Boolean = false,
    val error:String = "",
    val isEnable: Boolean = false
)
