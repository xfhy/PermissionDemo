package com.xfhy.permission.req

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.xfhy.permission.common.TAG_FRAGMENT_PERMISSION
import com.xfhy.permission.result.IPermissionCallback
import com.xfhy.permission.result.PermissionResult

class PermissionContext {
    lateinit var activity: FragmentActivity
    var fragment: Fragment? = null
    var runtimePermissions: MutableSet<String> = LinkedHashSet()
    var specialPermissions: MutableSet<String> = LinkedHashSet()
    val permissionResult = PermissionResult()
    var permissionCallback: IPermissionCallback? = null

    val targetSdkVersion: Int
        get() = activity.applicationInfo.targetSdkVersion

    private val reqPermissionFragment: ReqPermissionFragment
        get() {
            val fragmentManager = fragment?.childFragmentManager ?: activity.supportFragmentManager
            var fragment = fragmentManager.findFragmentByTag(TAG_FRAGMENT_PERMISSION) as? ReqPermissionFragment
            if (fragment == null) {
                fragment = ReqPermissionFragment.newInstance()
                fragmentManager
                    .beginTransaction()
                    .add(fragment, TAG_FRAGMENT_PERMISSION)
                    .commitAllowingStateLoss()
                fragmentManager.executePendingTransactions()
            }
            return fragment
        }

    fun reqRuntimePermission(completedBlock: () -> Unit) {
        reqPermissionFragment.reqRuntimePermission(this, completedBlock)
    }

    fun reqSystemAlertWindowPermission(completedBlock: () -> Unit) {
        reqPermissionFragment.reqSystemAlertWindowPermission(this, completedBlock)
    }

}