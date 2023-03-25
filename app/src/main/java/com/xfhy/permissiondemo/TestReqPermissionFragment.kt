package com.xfhy.permissiondemo

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.xfhy.permission.PermissionResult
import com.xfhy.permission.PermissionsHelper


class TestReqPermissionFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_req_permission, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.btn_req_permission2).setOnClickListener {
            reqPermission()
        }
    }

    private fun reqPermission() {
        PermissionsHelper.init(this)
            .requestEachPermissions(
                Manifest.permission.READ_CONTACTS, Manifest.permission.READ_CALENDAR
            ) { permissionResult -> permissionResult.logResult() }
    }
//
//    private fun show(permissionResult: PermissionResult) {
//        if (permissionResult.isGranted) {
//            show("授予权限 ：" + permissionResult.permissionName)
//        } else {
//            if (permissionResult.shouldShowRequestPermissionRationale) {
//                show("没有勾选不再提醒，拒绝权限 ：" + permissionResult.permissionName)
//            } else {
//                show("勾选不再提醒，拒绝权限 ：" + permissionResult.permissionName)
//            }
//        }
//    }
//
//    fun show(text: CharSequence) {
//        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
//    }

}