package com.example.collectionmanagement

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MainViewModel:ViewModel() {


    val  visiblePermissionDialogQueue = mutableStateListOf<String>()

    fun removeDialoge(){
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermission(permission:String,isGranted:Boolean){
        println("permission: $permission status:$isGranted")
        if(!isGranted) visiblePermissionDialogQueue.add(permission)
    }

}