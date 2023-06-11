package com.example.collectionmanagement.collection_book.prentation.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestPermission(
    permissionTextProvider: RequestTextProvider,
    isPermanentDeclined: Boolean,
    onDismissClick: () -> Unit,
    onGotoSettingClick: () -> Unit,
    onClickOk: () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth()

) {

    AlertDialog(
        onDismissRequest = onDismissClick,


        title = { Text(text = "Request Permission") },
        text = { Text(text = permissionTextProvider.getPermissionText(isPermanentDeclined)) },
        buttons = {
            Column {
                Divider()
                Text(
                    text = if (isPermanentDeclined) {
                        "Grant Permission"
                    } else "Ok",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isPermanentDeclined) onGotoSettingClick()
                            else onClickOk()
                        }
                        .padding(16.dp)

                )
            }
        },
        modifier = modifier
    )

}

interface RequestTextProvider {
    fun getPermissionText(isPermanentDeclined: Boolean): String
}

class ReadStorageTextProvider : RequestTextProvider {
    override fun getPermissionText(isPermanentDeclined: Boolean): String {
        return if (isPermanentDeclined) {
            "Is seem you have permanently declined the read storage permission" +
                    "please allow to get  import feature to be working"
        } else {
            "Read Storage need to be allowed for import database"
        }
    }

}

class WriteStorageTextProvider : RequestTextProvider {
    override fun getPermissionText(isPermanentDeclined: Boolean): String {
        return if (isPermanentDeclined) {
            "Is seem you have permanently declined the Write Storage permission" +
                    "please allow to get export feature to be working"
        } else {
            "Write Storage need to be allowed for backup export database"
        }
    }
}

class MangeStorageTextProvider : RequestTextProvider {
    override fun getPermissionText(isPermanentDeclined: Boolean): String {
        return if (isPermanentDeclined) {
            "Is seem you have permanently declined the Mange Storage permission" +
                    "please allow to mange export and import feature to be working"
        } else {
            "Mange Storage need to be allowed for managing export and import database"
        }
    }

}