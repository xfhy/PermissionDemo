package com.xfhy.permission

fun interface IPermissionCallback {
    /**
     * 回调结果给外部
     *
     * @param permissionResult 结果data
     */
    fun onResult(permissionResult: PermissionResult)
}