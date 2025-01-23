package com.example.craftiza.vm

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.craftiza.utils.ToastUtils
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class PermissionVM @Inject constructor(
    @ApplicationContext private val context : Context
) : ViewModel() {

    fun launchCamera(
        cameraLauncher : () -> Unit
    ){
        Dexter
            .withContext(context)
            .withPermission(android.Manifest.permission.CAMERA)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    cameraLauncher()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    p0?.run {
                        if(p0.isPermanentlyDenied){
                            ToastUtils.displayToast(context,"Please grant permission from profile page, after that you will be able to access camera!")
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }
            }).check()
    }

    fun launchGallery(
        galleryLauncher : () -> Unit
    ){
        Log.d("SDK INT","${Build.VERSION.SDK_INT}")
        Log.d("VERSION CODE","${Build.VERSION_CODES.TIRAMISU}");
        Dexter
            .withContext(context)
            .withPermission(
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    android.Manifest.permission.READ_MEDIA_IMAGES
                }else{
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                }
            ).withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    galleryLauncher()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    p0?.run {
                        if(p0.isPermanentlyDenied){
                            ToastUtils.displayToast(context,"Please grant permission from profile page, after that you will be able to access gallery!")
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }
            }).check()
    }
}