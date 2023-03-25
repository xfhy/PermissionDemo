package com.xfhy.permission

import android.content.pm.PackageManager
import android.util.SparseArray
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import java.util.*

class ReqPermissionFragment : Fragment() {
    private val mPermissionCallback = SparseArray<IPermissionCallback>()
    private val mRandom = Random()

    fun requestPermissions(permissions: Array<String>, callback: IPermissionCallback) {
        val requestCode = mRandom.nextInt(Int.MAX_VALUE)
        mPermissionCallback.put(requestCode, callback)
        requestPermissions(permissions, requestCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        handlePermissionCallBack(requestCode, permissions, grantResults)
    }

    private fun handlePermissionCallBack(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        val listener = mPermissionCallback[requestCode] ?: return
        mPermissionCallback.remove(requestCode)
        val length = grantResults.size
        val permissionResult = PermissionResult()
        for (i in 0 until length) {
            val grantResult = grantResults[i]
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                permissionResult.grantedPermissions.add(permissions[i])
            } else {
                permissionResult.isAllGranted = false
                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), permissions[i])) {
                    //拒绝 但没有选择不再提示
                    permissionResult.deniedPermissions.add(permissions[i])
                } else {
                    permissionResult.deniedAndNoMorePromptsPermissions.add(permissions[i])
                }
            }
        }
        listener.onResult(permissionResult)
    }

    companion object {
        @JvmStatic
        fun newInstance(): ReqPermissionFragment {
            return ReqPermissionFragment()
        }
    }
}