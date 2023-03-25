package com.xfhy.permission.req

import android.os.Build

class RuntimePermissionRequest(permissionContext: PermissionContext) :
    BaseRequest(permissionContext) {
    override fun reqPermission(reqList: List<BaseRequest>, index: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissionContext.targetSdkVersion >= Build.VERSION_CODES.M) {
            permissionContext.reqRuntimePermission {
                currentProcessComplete(reqList, index)
            }
        } else {
            permissionContext.permissionResult.grantedPermissions.addAll(permissionContext.runtimePermissions)
            currentProcessComplete(reqList, index)
        }
    }
}