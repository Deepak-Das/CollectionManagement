package com.example.collectionmanagement.collection_book.prentation.Home.HomeViewModel

import android.content.Context
import android.os.Environment
import android.os.Process
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collectionmanagement.R
import com.example.collectionmanagement.collection_book.data.data_source.AppDatabase
import com.example.collectionmanagement.collection_book.prentation.theme.dailyColor
import com.example.collectionmanagement.collection_book.prentation.theme.debtorColor
import com.example.collectionmanagement.collection_book.prentation.theme.loneColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val db: AppDatabase,
) : ViewModel() {

    val bodyList: List<ButtonObj> = listOf(
        ButtonObj(imgR = R.drawable.daily_pay, text = "Daily Pay", color = dailyColor),
        ButtonObj(imgR = R.drawable.people, text = "Debtor", color = debtorColor),
        ButtonObj(imgR = R.drawable.lone, text = "Lone", color = loneColor),
    )
    val drawerList: List<ButtonObj> = listOf(
        ButtonObj(imgR = R.drawable.home, text = "Home", color = dailyColor),
        ButtonObj(imgR = R.drawable.backup, text = "Export DB", color = dailyColor),
        ButtonObj(imgR = R.drawable.excel, text = "Excel Backup", color = debtorColor),
        ButtonObj(imgR = R.drawable.db_import, text = "Import DB", color = loneColor),
    )
    private val _state = mutableStateOf<HomeState>(HomeState())
    val state: State<HomeState> = _state;


    fun exportDb(context: Context) {
        setProgress(true);
        val backupDir =
            File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "MyDatabaseBackup")
        println(backupDir.absolutePath)

        viewModelScope.launch {
            val dbFile = context.getDatabasePath(AppDatabase.DATABASE_NAME)
            backupDir.mkdirs()
            val backupFile = File(backupDir, dbFile.name)
            dbFile.copyTo(backupFile, true)

            Toast.makeText(context, "Successful backup Db", Toast.LENGTH_SHORT).show()

            setProgress(false);
        }

    }

    fun importDb(context: Context, activity: ComponentActivity) {
        viewModelScope.launch {


            db.close()


            val dbFile = context.getDatabasePath(AppDatabase.DATABASE_NAME)
//            if (!dbFile.exists()) return@launch

            val backupFile = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "MyDatabaseBackup/${AppDatabase.DATABASE_NAME}")

            backupFile.copyTo(dbFile, true)

            Toast.makeText(context, "Successful imported Db", Toast.LENGTH_SHORT).show()
            delay(1000);

            closeApp(activity);


        }
    }

    private fun closeApp(activity: ComponentActivity) {
        activity.finishAffinity()
        Process.killProcess(Process.myPid())
    }

    fun setProgress(status: Boolean) {
        _state.value = state.value.copy(
            progressStatus = status
        )
    }

    fun setHomeDateAndTimeStamp(timeStamp: Long, date: String) {
        _state.value = state.value.copy(
            timeStamp = timeStamp,
            date = date
        )
    }
}