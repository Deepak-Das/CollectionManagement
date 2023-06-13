package com.example.collectionmanagement.collection_book.prentation.Home.HomeViewModel

import android.content.*
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.os.Process
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collectionmanagement.R
import com.example.collectionmanagement.collection_book.data.data_source.AppDatabase
import com.example.collectionmanagement.collection_book.prentation.navigation.Router
import com.example.collectionmanagement.collection_book.prentation.theme.dailyColor
import com.example.collectionmanagement.collection_book.prentation.theme.debtorColor
import com.example.collectionmanagement.collection_book.prentation.theme.loneColor
import com.example.collectionmanagement.collection_book.prentation.utils.Ams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.sql.Date
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val db: AppDatabase,
) : ViewModel() {

    val bodyList: List<ButtonObj> = listOf(
        ButtonObj(imgR = R.drawable.daily_pay, text = "Daily Pay", color = dailyColor,route=Router.DailyScreen),
        ButtonObj(imgR = R.drawable.people, text = "Debtor", color = debtorColor,route=Router.DebtorScreen),
        ButtonObj(imgR = R.drawable.lone, text = "Lone", color = loneColor,route=Router.LoneScreen),
    )
    val drawerList: List<ButtonObj> = listOf(
        ButtonObj(imgR = R.drawable.home, text = "Home", color = dailyColor),
        ButtonObj(imgR = R.drawable.backup, text = "Export DB", color = dailyColor),
        ButtonObj(imgR = R.drawable.excel, text = "Excel Backup", color = debtorColor),
        ButtonObj(imgR = R.drawable.db_import, text = "Import DB", color = loneColor),
    )
    private val _state = mutableStateOf<HomeState>(HomeState())
    val state: State<HomeState> = _state;


    fun exportDb(context: Context, fileName: String, selectedUri: Uri?) {

        setProgress(true);




        var backupDir = File(Environment.getExternalStorageDirectory(), "MyDatabaseBackup")




        viewModelScope.launch {
            val dbFile = context.getDatabasePath(AppDatabase.DATABASE_NAME)
            backupDir.mkdirs()
            val backupFile = File(backupDir, fileName+".sql")
            dbFile.copyTo(backupFile, true)

            Toast.makeText(context, "Successful backup Db", Toast.LENGTH_SHORT).show()


            setProgress(false);
        }

    }

    fun importDb(context: Context, activity: ComponentActivity,uri: Uri) {
        viewModelScope.launch {


           db.close()




            val dbFile = context.getDatabasePath(AppDatabase.DATABASE_NAME)
            val backupFile=contentUriToFile(context,uri)


            println("fileDB Db: ${dbFile.path}")
            println("fileDB bk: ${backupFile?.path}")
//            return@launch
//            val backupFile = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "MyDatabaseBackup/${AppDatabase.DATABASE_NAME}")

            if(backupFile==null){
                Toast.makeText(context, "File not picked", Toast.LENGTH_SHORT).show()
                return@launch
            }

            try {
                backupFile.copyTo(dbFile, overwrite = true)
                backupFile.delete();
                Toast.makeText(context, "Successful imported Db", Toast.LENGTH_SHORT).show()

            }catch (e:NoSuchFileException){
                Toast.makeText(context, "file not exist", Toast.LENGTH_SHORT).show()
            }

//            delay(1000);

//            closeApp(activity);



            restartApp(context,activity)







        }
    }


   /* fun getPays() {
        job2?.cancel()
        job2 = useCase.getAllPayments().onEach {
            _homeState.value = homesate.value.copy(
                listpay = it
            )
        }.launchIn(viewModelScope)
    }

    fun csvGenerate(mContext: Context) {
        viewModelScope.launch {
            state.value = state.value.copy(
                loanList = mutableListOf(),
                paysList = mutableListOf()
            )

            var file_name =
                "Backup_" + SimpleDateFormat("dd-MM-yy").format(Date(System.currentTimeMillis()))
                    .toString() + ".csv"

            pathCSV = mContext.getExternalFilesDir(null)!!.absolutePath + "/Backup_CSV"
            val dir = File(pathCSV)
            if (!dir.exists()) {
                dir.mkdir()
            }
            homesate.value.listloans.onEachIndexed { index, it ->
                homesate.value.loanList.add(
                    listOf(
                        (index + 1).toString(),
                        it.DebtorName,
                        it.LoneAmount.toString(),
                        SimpleDateFormat("dd-MM-yyyy").format(it.timeStamp),
                        it.status
                    )
                )
            }
//            Log.i(TAG, "csvLoan: ${homesate.value.listloans.toString()}")
            val file = File(pathCSV, file_name)

            csvWriter().open(file) {
                writeRow("", "", "LOAN_RECORD", "", "")
                writeRow("")
                writeRow(listOf("SL NO.", "Name", "Loan_Amount", "Date", "Status"))
                writeRows(homesate.value.loanList)
                writeRow("")
            }

            homesate.value.listpay.onEachIndexed { index, it ->
                homesate.value.paysList.add(
                    listOf(
                        (index + 1).toString(),
                        it.debtorName,
                        it.amount.toString(),
                        SimpleDateFormat("dd-MM-yyyy").format(it.timeStamp)
                    )
                )
//                Log.i(TAG, "csvGenerate: ${homesate.value.paysList[0].toString()}")
            }

//            Log.i(TAG, "csvGenerate: ${homesate.value.listpay}")


            csvWriter().open(file, true) {
                writeRow("", "", "PAYMENTS_RECORD", "", "")
                writeRow("")
                writeRow(listOf("SL NO.", "Name", "Pay_Amount", "Date"))
                writeRows(homesate.value.paysList)
            }


            Toast.makeText(mContext, "Successful write", Toast.LENGTH_SHORT).show()


            val intent = Intent(Intent.ACTION_VIEW);
            intent.data = FileProvider.getUriForFile(
                mContext,
                mContext.packageName.toString() + ".provider",
                file
            )
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            delay(500)
            try {
                mContext.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Log.i("App_Tag", "Exception - " + e.message)
            }
        }
    }*/


    fun contentUriToFile(context: Context, contentUri: Uri): File? {
        val contentResolver: ContentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(contentUri) ?: return null

        val tempFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),AppDatabase.DATABASE_NAME)
        val outputStream = FileOutputStream(tempFile)

        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }

        return tempFile
    }




    private fun closeApp(activity: ComponentActivity) {
        activity.finishAffinity()
        Process.killProcess(Process.myPid())
    }

 private fun restartApp(context: Context,activity: ComponentActivity) {
     val packageManager: PackageManager = context.packageManager
     val intent: Intent = packageManager.getLaunchIntentForPackage(context.packageName)!!
     val componentName: ComponentName = intent.component!!
     val restartIntent: Intent = Intent.makeRestartActivityTask(componentName)
     context.startActivity(restartIntent)
     Runtime.getRuntime().exit(0)
    }

    fun setProgress(status: Boolean) {
        _state.value = state.value.copy(
            progressStatus = status
        )
    }

    fun setHomeDateAndTimeStamp(timeStamp: Long, date: String) {
       viewModelScope.launch {
           _state.value = state.value.copy(
               timeStamp = timeStamp,
               date = date
           )
           Ams.GLOBLE_DATE=date
           Ams.GLOBLE_TIMSTAMP=timeStamp
       }
    }
    fun setIsPick(status:Boolean) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isPick =  status
            )
        }
    } fun setIsExport(status:Boolean) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isExport =  status
            )
        }
    }


}