package com.bloomengineering.permissionsfencedemo

import android.Manifest
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.bloomengineering.permissionsfencedemo.utils.permission.FencedPermissionManager

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val permissionManager = FencedPermissionManager(
        application.applicationContext,
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    )
}