package com.example.collectionmanagement.collection_book.prentation

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.collectionmanagement.collection_book.domain.model.DebtorLoan
import com.example.collectionmanagement.collection_book.domain.model.LoanWithName
import com.example.collectionmanagement.collection_book.prentation.Debtor.CustomIconText
import com.example.collectionmanagement.collection_book.prentation.Loan.DebtorLoneCard
import com.example.collectionmanagement.collection_book.prentation.Loan.LoneState
import com.example.collectionmanagement.collection_book.prentation.Loan.LoneViewModel
import com.example.collectionmanagement.collection_book.prentation.utils.Ams
import com.example.dailymoneyrecord.recorde_Book.domain.util.OrderType
import com.example.dailymoneyrecord.recorde_Book.domain.util.Status
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun LoanPage(
    viewModel: LoneViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    var scope = CoroutineScope(Dispatchers.IO)
    val groupby = state.filtertloneList.groupBy { lone -> lone.timeStamp }
    var debtorName by remember {
        mutableStateOf("")
    }
    val snackbarHostState = remember { SnackbarHostState() }





    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { it ->

                var data = (it.visuals as? Ams.DefaultSnackbar)

                Snackbar(
                    action = {
                        TextButton(onClick = { it.performAction() }) {
                            Text("yes")
                        }
                    }
                ) {
                    data?.let { it1 -> Text(it1.message) }
                }


            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.setAddLoneDialog(true)

            }) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    Modifier.size(60.dp),
                    tint = MaterialTheme.colorScheme.tertiary
                )

            }
        },

        ) {
        Column(
            Modifier
                .padding(it)
                .padding(10.dp)
        ) {
            Ams.CustomSearchBar(
                getDebtor = {
                    viewModel.setSearch(it.name)
                },
                list = state.debtorList,
                query = state.search,
                setQuery = viewModel::setSearch,
                menuContent = {
                    LoanHeaderBox(state, viewModel)
                }
            )


            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                groupby.forEach { timestamp, list ->

                    stickyHeader {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Text(
                                Ams.timeStampToDate(timestamp),
                                style = Ams.getBStyle(fontSize = 18.sp)
                            )
                            Spacer(modifier = Modifier.height(5.dp))

                        }
                    }

                    items(list) {

                        DebtorLoneCard(
                            loneWithName = it, editLone = { loneWithName ->

                                viewModel.setEditLoneWithName(it)
                                viewModel.setOpenDialog(true)
                                println(loneWithName.DebtorName)

                            }, onClickDelete = viewModel::setDeleteLoneData,
                            onClickSwitch = {
                                viewModel.saveUpdateLone(it)
                            }
                        )
                        Spacer(modifier = Modifier.size(10.dp))

                    }
                }
            }

            UpdateDebtorLone(

                status = state.openUpdateDialog,
                loanWithName = state.editLoanWithName,
                setStatusFn = viewModel::setOpenDialog,
                updateLoneFn = viewModel::saveUpdateLone
            )

            AddDebtorLone(
                status = state.openAddDialog,
                setStatusFn = viewModel::setAddLoneDialog,
                onClickAdd = viewModel::saveUpdateLone,
                viewModel=viewModel
            )

            Ams.Waring(
                status = state.isWarning,
                msg = "Are you sure want to delete the lone",
                openDialog = viewModel::setIsWarning,
                onclickSure = {
                    viewModel.deleteLone(state.deleteLoneData)
                    viewModel.setIsWarning(false)
                    scope.launch {
                        val snackbarResult = snackbarHostState.showSnackbar(
                            Ams.DefaultSnackbar(message = "want to undo the last delete"),
                        )

                        when (snackbarResult) {
                            SnackbarResult.Dismissed -> Log.d("SnackbarDemo", "Dismissed")
                            SnackbarResult.ActionPerformed -> viewModel.saveUpdateLone(state.deleteLoneData!!)

                        }

                    }
                },
                onclickCancel = viewModel::setIsWarning
            )


        }

    }


}

@Composable
private fun LoanHeaderBox(
    state: LoneState,
    viewModel: LoneViewModel
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(IntrinsicSize.Min),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Sort Loan", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                Modifier
                    .widthIn(min = 60.dp)
                    .wrapContentWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RadioButtonText(
                    radioStatus = Status.Running(state.status.orderType),
                    selected = state.status is Status.Running,
                    text = "Running",
                    onClickRadio = viewModel::setStatus
                )
                RadioButtonText(
                    radioStatus = Status.Paid(state.status.orderType),
                    selected = state.status is Status.Paid,
                    text = "Paid",
                    onClickRadio = viewModel::setStatus
                )
                RadioButtonText(
                    radioStatus = Status.All(state.status.orderType),
                    selected = state.status is Status.All,
                    text = "All",
                    onClickRadio = viewModel::setStatus
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                Modifier
                    .widthIn(min = 60.dp)
                    .wrapContentWidth()
                    .padding(horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RadioButtonText(
                    radioStatus = state.status.copy(OrderType.Descending),
                    selected = state.status.orderType is OrderType.Descending,
                    text = "Descending",
                    onClickRadio = viewModel::setStatus
                )
                RadioButtonText(
                    radioStatus = state.status.copy(OrderType.Ascending),
                    selected = state.status.orderType is OrderType.Ascending,
                    text = "Ascending",
                    onClickRadio = viewModel::setStatus
                )

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateDebtorLone(
    status: Boolean,
    loanWithName: LoanWithName?,
    setStatusFn: (Boolean) -> Unit,
    updateLoneFn: (DebtorLoan) -> Unit,

    ) {

    val calenderState = rememberSheetState()

    var name by remember {
        mutableStateOf("")
    }

    var date by remember {
        mutableStateOf("")
    }
    var amount by remember {
        mutableStateOf("")
    }

    LaunchedEffect(loanWithName) {
        if (loanWithName != null) {
            name = loanWithName.DebtorName
            date = Ams.timeStampToDate(loanWithName.timeStamp)
            amount = loanWithName.LoneAmount.toString()


        }
    }


    Ams.CalenderPop(f = { timeStamp, dateStr ->
        date = dateStr
    }, calenderState = calenderState)


    if (status) {
        AlertDialog(
            onDismissRequest = { setStatusFn(false) }
        ) {
            Card {
                Column(
                    Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Add Debtor", style = Ams.getMStyle())
                    Spacer(modifier = Modifier.size(14.dp))

                    OutlinedTextField(
                        value = name,
                        readOnly = true,
                        maxLines = 1,
                        onValueChange = { name = it },
                        label = { Text(text = "Name") },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    OutlinedTextField(
                        readOnly = true,
                        value = date,
                        maxLines = 1,
                        suffix = {
                            Icon(modifier = Modifier.clickable {
                                calenderState.show()
                            }, imageVector = Icons.Default.Update, contentDescription = "")
                        },
                        onValueChange = { date = it },
                        label = { Text(text = "Date") },
                        leadingIcon = {
                            Icon(Icons.Default.Phone, contentDescription = null)
                        },
                    )
                    OutlinedTextField(
                        value = amount,
                        maxLines = 1,
                        onValueChange = { amount = it },
                        label = { Text(text = "Amount") },
                        leadingIcon = {
                            Icon(Icons.Default.Money, contentDescription = null)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = {
                            setStatusFn(false)
                            name = ""
                            amount = ""
                        }) {
                            Text(text = "Cancel", style = Ams.getMStyle())
                        }
                        TextButton(onClick = {
                            setStatusFn(false)
                            updateLoneFn(
                                DebtorLoan(
                                    loneId = loanWithName?.loanId,
                                    amount = amount.toInt(),
                                    loneHolder = loanWithName?.debtorId!!,
                                    timestamp = Ams.dateToTimeStamp(date),
                                    status = loanWithName.status
                                )
                            )
                        }) {
                            Text(text = "Update", style = Ams.getMStyle())
                        }
                    }

                }
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDebtorLone(
    status: Boolean,
    setStatusFn: (Boolean) -> Unit,
    onClickAdd: (DebtorLoan) -> Unit,
    viewModel: LoneViewModel,
    context:Context = LocalContext.current

) {

    val calenderState = rememberSheetState()

    var name by remember {
        mutableStateOf("")
    }

    var date by remember {
        mutableStateOf("")
    }
    var debtorId by remember {
        mutableStateOf<Int?>(null)
    }
    var amount by remember {
        mutableStateOf("")
    }

    var searchFieldsize by remember {
        mutableStateOf(Size.Zero)
    }

    var isDebtorExpend by remember {

        mutableStateOf(false)
    }



    Ams.CalenderPop(f = { timeStamp, dateStr ->
        date = dateStr
    }, calenderState = calenderState)

    LaunchedEffect(key1 = true, block = {
        date=Ams.GLOBLE_DATE
    })




    if (status) {
        AlertDialog(
            onDismissRequest = { setStatusFn(false) }
        ) {
            Card {
                Column(
                    Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Add Debtor", style = Ams.getMStyle())
                    Spacer(modifier = Modifier.size(14.dp))

                    OutlinedTextField(
                        modifier = Modifier
                            .onGloballyPositioned {
                                searchFieldsize = it.size.toSize()
                            }
                            .onFocusChanged {
                                isDebtorExpend = it.isFocused
                            },
                        singleLine=true,
                        value = name,
                        maxLines = 1,
                        keyboardActions = KeyboardActions(
                            onNext = {
                                calenderState.show()
                            }
                        ),

                    onValueChange = { name = it },
                        label = { Text(text = "Name") },
//                        placeholder = {
//                            Text("--select on clicking icon--")
//                        },
                        leadingIcon = {
                            Icon(
                                modifier = Modifier.clickable { isDebtorExpend=!isDebtorExpend },
                                imageVector = Icons.Default.Person,
                                contentDescription = null)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
                    )
                    AnimatedVisibility(visible = isDebtorExpend) {
                        Card(
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.0.dp
                            ),
                            modifier = Modifier
                                .width(searchFieldsize.width.dp)
                                .background(Color.White, shape = MaterialTheme.shapes.small)
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .heightIn(max = 150.dp)
                                    .width(searchFieldsize.width.dp)
                                    .background(Color.White, shape = MaterialTheme.shapes.small)
                            ) {

                                if (viewModel.state.value.debtorList.isNotEmpty()) {
                                    items(viewModel.state.value.debtorList.filter {
//                                        it.name.lowercase().contains(name.lowercase())
                                        it.name.contains(name, true)
                                    }.sortedBy { it.name }
                                    ) {

                                        DropdownMenuItem(text = {
                                            CustomIconText(
                                                icon = Icons.Default.Person,
                                                txt = it.name
                                            )
                                        },
                                            onClick = {
                                                isDebtorExpend = false
                                                debtorId = it.debtorId
                                                name=it.name
                                            })
                                    }

                                } else {
                                    items(viewModel.state.value.debtorList.sortedBy { it.name }) {
                                        DropdownMenuItem(text = {
                                            CustomIconText(
                                                icon = Icons.Default.Person,
                                                txt = it.name
                                            )
                                        },
                                            onClick = {
                                                isDebtorExpend = false
                                                debtorId = it.debtorId
                                                name=it.name

                                            })
                                    }
                                }
                            }
                        }

                    }

                    OutlinedTextField(
                        readOnly = true,
                        value = date,
                        maxLines = 1,
                        suffix = {
                            Icon(modifier = Modifier.clickable {
                                calenderState.show()
                            }, imageVector = Icons.Default.Update, contentDescription = "")
                        },
                        onValueChange = { date = it },
                        label = { Text(text = "Date") },
                        leadingIcon = {
                            Icon(Icons.Default.Phone, contentDescription = null)
                        },
                    )
                    OutlinedTextField(
                        value = amount,
                        maxLines = 1,
                        onValueChange = { amount = it },
                        label = { Text(text = "Amount") },
                        leadingIcon = {
                            Icon(Icons.Default.Money, contentDescription = null)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = {
                            setStatusFn(false)
                            name = ""
                            amount = ""
                            date=""
                            debtorId=null
                        }) {
                            Text(text = "Cancel", style = Ams.getMStyle())
                        }
                        TextButton(onClick = {
                            if (debtorId == null||amount.isEmpty()||date.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "please fill all field",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return@TextButton
                            }
                            setStatusFn(false)
                            onClickAdd(
                                DebtorLoan(
                                    loneId = null,
                                    amount = amount.toInt(),
                                    loneHolder = debtorId!!,
                                    timestamp = Ams.dateToTimeStamp(date),
                                    status = "Running"
                                )
                            )
                            name = ""
                            amount = ""
                            date=""
                            debtorId=null
                        }) {
                            Text(text = "save", style = Ams.getMStyle())
                        }
                    }

                }
            }
        }
    }


}


@Composable
fun RadioButtonText(
    selected: Boolean,
    radioStatus: Status,
    onClickRadio: (Status) -> Unit,
    text: String
) {


    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = selected, onClick = { onClickRadio(radioStatus) })
        Spacer(modifier = Modifier.width(5.dp))
        Text(text, style = Ams.getBStyle(fontSize = 18.sp))
    }
}







