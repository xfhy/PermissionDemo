package com.xfhy.permission

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class ReqPermissionFragment : Fragment() {
    private var mPermissionCallback: IPermissionCallback? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { grantResults ->
            handlePermissionCallBack(grantResults)
        }

    fun requestPermissions(permissions: Array<String>, callback: IPermissionCallback) {
        mPermissionCallback = callback
        requestPermissionLauncher.launch(permissions)
    }

    private fun handlePermissionCallBack(grantResults: Map<String, Boolean>) {
        val permissionResult = PermissionResult()
        for ((permission, granted) in grantResults) {
            if (granted) {
                permissionResult.grantedPermissions.add(permission)
            } else {
                val shouldShowRationale = shouldShowRequestPermissionRationale(permission)
                if (shouldShowRationale) {
                    permissionResult.deniedPermissions.add(permission)
                } else {
                    permissionResult.deniedAndNoMorePromptsPermissions.add(permission)
                }
            }
        }
        permissionResult.isAllGranted = permissionResult.grantedPermissions.size == grantResults.size
        mPermissionCallback?.onResult(permissionResult)
    }

    companion object {
        @JvmStatic
        fun newInstance(): ReqPermissionFragment {
            return ReqPermissionFragment()
        }
    }
}