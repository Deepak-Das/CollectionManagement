package com.example.collectionmanagement

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.collectionmanagement.collection_book.prentation.Home.goToSetting
import com.example.collectionmanagement.collection_book.prentation.navigation.ComposeNavigation
import com.example.collectionmanagement.collection_book.prentation.utils.MangeStorageTextProvider
import com.example.collectionmanagement.collection_book.prentation.utils.ReadStorageTextProvider
import com.example.collectionmanagement.collection_book.prentation.utils.RequestPermission
import com.example.collectionmanagement.collection_book.prentation.utils.WriteStorageTextProvider
import com.example.compose.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: MainViewModel = viewModel()
                    val viewDialog = viewModel.visiblePermissionDialogQueue

                    val multiplePermissions = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.RequestMultiplePermissions(),
                        onResult = { parms ->
                            parms.keys.forEach { permission ->
                                viewModel.onPermission(
                                    permission = permission,
                                    isGranted = parms[permission] == true
                                )
                            }
                        }
                    )

                    LaunchedEffect(key1 = multiplePermissions, block = {
                        multiplePermissions.launch(
                            arrayOf(

                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,


                                )
                        )
                    })


                    viewDialog.reversed().forEach { permission ->
                        RequestPermission(
                            permissionTextProvider = when (permission) {
                                Manifest.permission.READ_EXTERNAL_STORAGE -> ReadStorageTextProvider()
                                Manifest.permission.WRITE_EXTERNAL_STORAGE -> WriteStorageTextProvider()
                                else -> return@forEach
                            },
                            isPermanentDeclined = !shouldShowRequestPermissionRationale(permission),
                            onDismissClick = viewModel::removeDialoge,
                            onGotoSettingClick = ::goToSetting,
                            onClickOk = {
                                multiplePermissions.launch(
                                    arrayOf(
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                            )
                                )
                            })
                    }



                    ComposeNavigation()
                }
            }
        }
    }

}

fun Activity.goToSetting() {

    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}


