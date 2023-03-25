package com.xfhy.permission.result

fun interface IPermissionCallback {
    /**
     * 回调结果给外部
     *
     * @param permissionResult 结果data
     */
    fun onResult(permissionResult: PermissionResult)
}