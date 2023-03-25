package com.xfhy.permissiondemo

import android.Manifest
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.xfhy.permission.PermissionsHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment()
        findViewById<Button>(R.id.btn_req_permission).setOnClickListener {
            reqPermission()
        }
    }

    private fun reqPermission() {
        PermissionsHelper.init(this)
            .requestPermissions(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.SYSTEM_ALERT_WINDOW
            ) { permissionResult ->
                permissionResult.logResult()
            }
    }

    private fun addFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fl_container, TestReqPermissionFragment())
            .commitAllowingStateLoss()
        supportFragmentManager.executePendingTransactions()
    }

}