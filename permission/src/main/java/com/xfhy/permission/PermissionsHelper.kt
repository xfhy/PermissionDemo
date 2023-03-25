package com.xfhy.permission

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.xfhy.permission.ReqPermissionFragment.Companion.newInstance
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.Fragment
import java.lang.Exception

class PermissionsHelper {
    private var mActivity: FragmentActivity? = null
    private var mFragment: Fragment? = null

    constructor(activity: FragmentActivity) {
        mActivity = activity
    }

    constructor(fragment: Fragment) {
        mFragment = fragment
    }

    fun requestEachPermissions(vararg permissions: String, eachPermissionListener: IPermissionCallback) {
        reqPermissionFragment.requestPermissions(setOf(*permissions).toTypedArray(), eachPermissionListener)
    }

    private val reqPermissionFragment: ReqPermissionFragment
        get() {
            val fragmentManager = mActivity?.supportFragmentManager ?: mFragment?.childFragmentManager
            var fragment = fragmentManager!!.findFragmentByTag(TAG_FRAGMENT_PERMISSION) as? ReqPermissionFragment
            if (fragment == null) {
                fragment = newInstance()
                fragmentManager
                    .beginTransaction()
                    .add(fragment, TAG_FRAGMENT_PERMISSION)
                    .commitAllowingStateLoss()
                fragmentManager.executePendingTransactions()
            }
            return fragment
        }

    companion object {
        private const val TAG_FRAGMENT_PERMISSION = "tag_fragment_permission"

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