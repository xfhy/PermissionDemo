package com.xfhy.permission.req

import android.Manifest
import android.os.Build
import android.provider.Settings

class SystemAlertWindowRequest(permissionContext: PermissionContext) :
    BaseRequest(permissionContext) {
    override fun reqPermission(reqList: List<BaseRequest>, index: Int) {
        if (permissionContext.specialPermissions.contains(Manifest.permission.SYSTEM_ALERT_WINDOW)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissionContext.targetSdkVersion >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(permissionContext.activity)) {
                    //已有权限
                    permissionContext.permissionResult.grantedPermissions.add(Manifest.permission.SYSTEM_ALERT_WINDOW)
                    currentProcessComplete(reqList, index)
                } else {
                    //去申请
                    permissionContext.reqSystemAlertWindowPermission {
                        currentProcessComplete(reqList, index)
                    }
                }
            } else {
                permissionContext.permissionResult.grantedPermissions.add(Manifest.permission.SYSTEM_ALERT_WINDOW)
                currentProcessComplete(reqList, index)
            }
        } else {
            //跳过  没有该特殊权限的申请
            currentProcessComplete(reqList, index)
        }
    }
}