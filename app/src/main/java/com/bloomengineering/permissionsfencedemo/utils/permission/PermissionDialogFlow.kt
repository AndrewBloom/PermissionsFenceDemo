package com.bloomengineering.permissionsfencedemo.utils.permission

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.core.app.ActivityCompat
import com.bloomengineering.permissionsfencedemo.R


val PERMISSION_FLOW_REQUEST_CODE = 0x707

    fun startPermissionRequest(activity: Activity, permission: String) =
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                permission
            )
        ) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
            AlertDialog.Builder(activity).setTitle(R.string.permission_rationale_title)
                .setMessage(R.string.permission_rationale_message)
                .setPositiveButton(android.R.string.ok) { dialogInterface: DialogInterface, i: Int ->
                    ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(permission),
                    PERMISSION_FLOW_REQUEST_CODE
                )}
                .create().show()
        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(permission),
                PERMISSION_FLOW_REQUEST_CODE
            )
        }
