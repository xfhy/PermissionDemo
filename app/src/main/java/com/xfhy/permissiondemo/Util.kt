package com.xfhy.permissiondemo

import android.util.Log
import com.xfhy.permission.result.PermissionResult

fun PermissionResult.logResult() {
    Log.d(
        "xfhy888", "isAllGranted = $isAllGranted , grantedPermissions = $grantedPermissions , deniedPermissions = $deniedPermissions , " +
                "deniedAndNoMorePromptsPermissions = $deniedAndNoMorePromptsPermissions"
    )
}