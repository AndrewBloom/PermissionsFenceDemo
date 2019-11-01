package com.bloomengineering.permissionsfencedemo

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bloomengineering.permissionsfencedemo.utils.permission.FencedPermissionEvent
import com.bloomengineering.permissionsfencedemo.utils.permission.PERMISSION_FLOW_REQUEST_CODE
import com.bloomengineering.permissionsfencedemo.utils.permission.startPermissionRequest

class MainActivity : AppCompatActivity() {


    lateinit var viewModel: MainViewModel

    private val permissionObserver = Observer<FencedPermissionEvent> { status ->
        status?.let {
            //updatePermissionCheckUI(status)
            when (status) {
               FencedPermissionEvent.REQUEST_PERMISSION -> showStoragePermissionNeededDialog()
            }
        }
    }

    fun showStoragePermissionNeededDialog() {
        startPermissionRequest(this, Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.permissionManager.getLiveData(Manifest.permission.READ_EXTERNAL_STORAGE).observe(this, permissionObserver)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_FLOW_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    for (permission in permissions) {
                        viewModel.permissionManager.executeAuthorizedCallback(permission)
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    for (permission in permissions) {
                        viewModel.permissionManager.executeDeniedCallback(permission)
                    }
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }
}

