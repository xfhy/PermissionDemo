package com.xfhy.permission.req

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class ReqPermissionFragment : Fragment() {
    private var completedBlock: (() -> Unit)? = null
    private var mPermissionContext = PermissionContext()

    private val requestRuntimePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { grantResults ->
            handleRuntimePermissionResult(grantResults)
        }
    private val requestSystemAlertWindowLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            handleRequestSystemAlertWindowPermissionResult()
        }

    private fun handleRuntimePermissionResult(grantResults: Map<String, Boolean>) {
        for ((permission, granted) in grantResults) {
            if (granted) {
                mPermissionContext.permissionResult.grantedPermissions.add(permission)
            } else {
                val shouldShowRationale = shouldShowRequestPermissionRationale(permission)
                if (shouldShowRationale) {
                    mPermissionContext.permissionResult.deniedPermissions.add(permission)
                } else {
                    mPermissionContext.permissionResult.deniedAndNoMorePromptsPermissions.add(permission)
                }
            }
        }
        completedBlock?.invoke()
    }

    fun reqRuntimePermission(params: PermissionContext, completedBlock: () -> Unit) {
        this.mPermissionContext = params
        this.completedBlock = completedBlock
        requestRuntimePermissionLauncher.launch(params.runtimePermissions.toTypedArray())
    }

    fun reqSystemAlertWindowPermission(params: PermissionContext, completedBlock: () -> Unit) {
        this.mPermissionContext = params
        this.completedBlock = completedBlock
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(requireContext())) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            intent.data = Uri.parse("package:${requireActivity().packageName}")
            requestSystemAlertWindowLauncher.launch(intent)
        } else {
            handleRequestSystemAlertWindowPermissionResult()
        }
    }

    private fun handleRequestSystemAlertWindowPermissionResult() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(requireContext())) {
                mPermissionContext.permissionResult.grantedPermissions.add(Manifest.permission.SYSTEM_ALERT_WINDOW)
            } else {
                mPermissionContext.permissionResult.deniedPermissions.add(Manifest.permission.SYSTEM_ALERT_WINDOW)
            }
        } else {
            mPermissionContext.permissionResult.grantedPermissions.add(Manifest.permission.SYSTEM_ALERT_WINDOW)
        }
        completedBlock?.invoke()
    }

    companion object {
        @JvmStatic
        fun newInstance(): ReqPermissionFragment {
            return ReqPermissionFragment()
        }
    }
}