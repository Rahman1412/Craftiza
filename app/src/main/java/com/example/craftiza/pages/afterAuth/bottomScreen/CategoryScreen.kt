package com.example.craftiza.pages.afterAuth.bottomScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.craftiza.pages.component.GridItemCategory
import com.example.craftiza.vm.CategoryVM

@Composable
fun CategoryScreen(
    paddingValues: PaddingValues
){
    val categoryvm : CategoryVM = hiltViewModel()
    val categories by categoryvm.categories.observeAsState(emptyList())
    Box(
        modifier = Modifier.fillMaxSize().padding(paddingValues)
    ){
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(categories){
                GridItemCategory(it)
            }
        }
    }
}