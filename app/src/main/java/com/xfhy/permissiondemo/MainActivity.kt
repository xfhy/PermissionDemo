package com.xfhy.permissiondemo

import android.Manifest
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.xfhy.permission.PermissionsHelper

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment()
        findViewById<Button>(R.id.btn_req_permission).setOnClickListener {
            PermissionsHelper.init(this@MainActivity)
                .requestEachPermissions(
                    Manifest.permission.READ_CONTACTS, Manifest.permission.READ_CALENDAR
                ) { permissionResult -> permissionResult.logResult() }
        }
    }

    private fun addFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fl_container, TestReqPermissionFragment())
            .commitAllowingStateLoss()
        supportFragmentManager.executePendingTransactions()
    }

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
//        Toast.makeText(this@MainActivity, text, Toast.LENGTH_SHORT).show()
//        Log.d(TAG, "$text")
//    }

}