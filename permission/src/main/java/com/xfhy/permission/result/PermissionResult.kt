package com.xfhy.permission.result

import java.util.LinkedHashSet

class PermissionResult {
    /**
     * 是否全部同意
     */
    var isAllGranted: Boolean = true

    /**
     * 同意的权限
     */
    val grantedPermissions = LinkedHashSet<String>()

    /**
     * 拒绝但还可以继续申请的权限
     */
    val deniedPermissions = LinkedHashSet<String>()

    /**
     * 拒绝且不再提示的权限
     */
    val deniedAndNoMorePromptsPermissions = LinkedHashSet<String>()
}