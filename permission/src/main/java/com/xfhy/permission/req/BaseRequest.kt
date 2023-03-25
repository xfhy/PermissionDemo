package com.xfhy.permission.req

abstract class BaseRequest(val permissionContext: PermissionContext) {

    abstract fun reqPermission(reqList: List<BaseRequest>, index: Int)

    fun currentProcessComplete(reqList: List<BaseRequest>, index: Int) {
        if (index == reqList.size - 1) {
            permissionContext.permissionResult.isAllGranted = (permissionContext.permissionResult.deniedPermissions.size == 0 &&
                    permissionContext.permissionResult.deniedAndNoMorePromptsPermissions.size == 0)
            //已处理完成,回调结果出去
            permissionContext.permissionCallback?.onResult(permissionContext.permissionResult)
            return
        }
        reqList[index + 1].reqPermission(reqList, index + 1)
    }

}