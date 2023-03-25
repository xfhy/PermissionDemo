package com.xfhy.permission

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.Fragment
import com.xfhy.permission.common.specialPermission
import com.xfhy.permission.req.PermissionContext
import com.xfhy.permission.req.RuntimePermissionRequest
import com.xfhy.permission.req.SystemAlertWindowRequest
import com.xfhy.permission.result.IPermissionCallback

class PermissionsHelper {
    private val mPermissionContext = PermissionContext()

    constructor(activity: FragmentActivity) {
        mPermissionContext.activity = activity
    }

    constructor(fragment: Fragment) {
        mPermissionContext.fragment = fragment
        mPermissionContext.activity = fragment.requireActivity()
    }

    fun requestPermissions(vararg permissions: String, permissionCallback: IPermissionCallback) {
        preprocessPermissions(linkedSetOf(*permissions))
        mPermissionContext.permissionCallback = permissionCallback
        val reqList = listOf(
            RuntimePermissionRequest(mPermissionContext),
            SystemAlertWindowRequest(mPermissionContext)
        )
        reqList[0].reqPermission(reqList, 0)
    }

    private fun preprocessPermissions(permissions: Set<String>) {
        mPermissionContext.runtimePermissions.clear()
        mPermissionContext.specialPermissions.clear()
        for (permission in permissions) {
            if (isSpecialPermission(permission)) {
                mPermissionContext.specialPermissions.add(permission)
            } else {
                mPermissionContext.runtimePermissions.add(permission)
            }
        }
    }

    private fun isSpecialPermission(permission: String) = specialPermission.contains(permission)

    companion object {
        fun init(fragmentActivity: FragmentActivity): PermissionsHelper {
            return PermissionsHelper(fragmentActivity)
        }

        fun init(fragment: Fragment): PermissionsHelper {
            return PermissionsHelper(fragment)
        }

        /**
         * 打开设置页面
         */
        fun startSettingActivity(context: Activity, requestCode: Int) {
            kotlin.runCatching {
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse(
                        "package:" +
                                context.packageName
                    )
                )
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                context.startActivityForResult(intent, requestCode)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}