package com.bloomengineering.permissionsfencedemo.utils.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * Listens to Runtime Permission Status of provided [permissionToListen] which comes under the
 * category of "Dangerous" and then responds with appropriate state specified in {@link PermissionStatus}
 */
private class PermissionStatus(
    private val context: Context,
    private val permissionToListen: String
) : LiveData<PermissionEvent>() {

    override fun onActive() = handlePermissionCheck()

    private fun handlePermissionCheck() {
        val isPermissionGranted = ActivityCompat.checkSelfPermission(
            context,
            permissionToListen
        ) == PackageManager.PERMISSION_GRANTED

        if (isPermissionGranted)
            postValue(PermissionEvent.GRANTED)
        else
            postValue(PermissionEvent.DENIED)
    }
}

private enum class PermissionEvent {
    GRANTED,
    DENIED,
    BLOCKED
}

/**
 * Stores the user state regarding her position on the Navigation Flow relative
 * to the fenced area.
 * The user can be:
 * - Inside the area that requires the permission
 * - Outside the area that requires the permission
 * The states are:
 * - On the verge of entering the area that requires permission
 * - On the verge of exiting the area that requires permission
 */
private class FencedNavigationStatus : LiveData<PermissionFencePositionEvent>()

private enum class PermissionFencePositionEvent {
    EXIT,
    ENTER,
}

/**
 * Mediator class that considers the position of the user on the navigation flow and the
 * status of the permissions granted to the app, and emits events based on the need to request
 * permissions
 */
private class FencedPermissionLiveData(
    context: Context,
    permissionToListen: String
) : MediatorLiveData<FencedPermissionEvent>() {
    private val permissionStatusListener: PermissionStatus =
        PermissionStatus(context, permissionToListen)
    private val fencedNavigationStatus: FencedNavigationStatus = FencedNavigationStatus()

    init {
        addSource(permissionStatusListener) { update() }
        addSource(fencedNavigationStatus) { update() }
    }

    public fun setFencedNavigationStatusValue(event: PermissionFencePositionEvent) {
        fencedNavigationStatus.value = event
    }

    private fun update() {
        val navValue = fencedNavigationStatus.value
        val permStatus = permissionStatusListener.value
        if (navValue != null && permStatus != null) {
            if (navValue == PermissionFencePositionEvent.ENTER)
                postValue(FencedPermissionEvent.REQUEST_PERMISSION)
        }
    }
}

enum class FencedPermissionEvent {
    REQUEST_PERMISSION
}

/**
 * Manager that owns a FencePermissionLiveData object, allows to interact with the
 * FencedNavigationStatus and stores the callbacks for authorised and denied permission
 */
class FencedPermissionManager(context: Context, permissions: Array<String>) {
    private val map = HashMap<String, FencedPermissionLiveData>()

    init {
        for (permission in permissions) {
            map[permission] = FencedPermissionLiveData(context, permission)
        }
    }

    private val clbckmap = HashMap<String, Triple<(() -> Unit)?, (() -> Unit)?, (() -> Unit)?>>()

    fun requestEnterFence(
        permission: String,
        authorizedCallback: (() -> Unit)?,
        deniedCallback: (() -> Unit)?,
        blockedCallback: (() -> Unit)?
    ) {
        clbckmap[permission] = Triple(authorizedCallback, deniedCallback, blockedCallback)
        map[permission]?.setFencedNavigationStatusValue(PermissionFencePositionEvent.ENTER)
    }

    fun getLiveData(permission: String): LiveData<FencedPermissionEvent> {
        return map[permission]!!
    }

    fun executeDeniedCallback(permission: String) {
        map[permission]?.setFencedNavigationStatusValue(PermissionFencePositionEvent.EXIT)
        clbckmap[permission]?.second?.invoke()
        clbckmap.remove(permission)
    }

    fun executeAuthorizedCallback(permission: String) {
        clbckmap[permission]?.first?.invoke()
        clbckmap.remove(permission)
    }

    fun exitFence(permission: String) {
        map[permission]?.setFencedNavigationStatusValue(PermissionFencePositionEvent.EXIT)
    }
}