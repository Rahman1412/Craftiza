package com.example.craftiza.pages.afterAuth.bottomScreen

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.craftiza.R
import com.example.craftiza.navigation.AfterAuthRoute
import com.example.craftiza.pages.component.HeightSpacer
import com.example.craftiza.utils.ToastUtils
import com.example.craftiza.vm.HomeVM
import com.example.craftiza.vm.ProfileVM

@Composable
fun ProfileScreen(
    navController: NavController,
    bottomNavController: NavController,
    paddingValues: PaddingValues
){
    val homevm : HomeVM = hiltViewModel()
    val profilevm : ProfileVM = hiltViewModel()
    val user by profilevm.user.observeAsState()
    val context = LocalContext.current
    val openSetting : () -> Unit = {
        try{
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package",context.packageName, null)
            intent.data = uri
            context.startActivity(intent)
        }catch (e:Exception){
            ToastUtils.displayToast(context,e.message.toString())
        }
    }
    Box(
        modifier = Modifier.fillMaxSize().padding(paddingValues)
    ){
        Column (
            modifier = Modifier.padding(10.dp)
        ){
            Card(
                modifier = Modifier.fillMaxWidth().padding(5.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        AsyncImage(
                            model = user?.avatar,
                            contentDescription = "Image",
                            placeholder = painterResource(R.drawable.ic_user),
                            error = painterResource(R.drawable.ic_user),
                            modifier = Modifier
                                .size(120.dp)
                                .clip(shape = CircleShape),
                            contentScale = ContentScale.Crop,
                        )
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(
                                user?.name ?: "",
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                user?.email ?: "",
                                style = TextStyle(
                                    fontWeight = FontWeight.SemiBold
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    IconButton(
                        onClick = {
                            navController.navigate(AfterAuthRoute.UpdateProfile.route)
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_right_arrow),
                            contentDescription = "Right Arrow Icon"
                        )
                    }
                }
            }
            HeightSpacer(16)
            ElevatedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    homevm.doLogOut();
                }
            ) {
                Text("Logout")
            }
            HeightSpacer(16)
            ElevatedButton(
                onClick = {
                    openSetting()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Grant Permission")
            }
        }
    }
}