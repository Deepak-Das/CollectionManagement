package com.example.collectionmanagement.collection_book.prentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.collectionmanagement.collection_book.domain.model.DebtorLoan
import com.example.collectionmanagement.collection_book.domain.model.LoanWithName
import com.example.collectionmanagement.collection_book.prentation.utils.Ams
import com.example.collectionmanagement.collection_book.prentation.Loan.DebtorLoneCard
import com.example.collectionmanagement.collection_book.prentation.Loan.LoneViewModel
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Preview
@Composable
fun LoanPage(
    viewModel: LoneViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    var scope= CoroutineScope(Dispatchers.IO);


    Scaffold {
        Column(
            Modifier
                .padding(it)
                .padding(10.dp)
        ) {
            Ams.CustomSearchBar(
                getDebtor = {

                },
                list = state.debtorList,
                query = state.search,
                setQuery = viewModel::setSearch,
            )


            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                items(state.loneWithNameList) {
                    DebtorLoneCard(loneWithName = it, editLone = {loneWithName->

                        viewModel.setEditLoneWithName(it)
                        viewModel.setOpendialog(true)
                        println(loneWithName.DebtorName)

                    }, deleteLone = {

                    })
                    Spacer(modifier = Modifier.size(10.dp))

                }
            }

            UpdateDebtorLone(

                status = state.openDialog,
                loanWithName= state.editLoanWithName,
                setStatusFn = {

                    viewModel.setOpendialog(it)

                },
                updateLoneFn = {

                }
            )


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
        mutableStateOf(loanWithName?.LoneAmount?:0)
    }

    LaunchedEffect(loanWithName){
        if (loanWithName != null) {
            name=loanWithName.DebtorName
            date= Ams.timeStampToDate(loanWithName.timeStamp)
            amount=loanWithName.LoneAmount


        }
    }


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
                        maxLines = 1,
                        onValueChange = { name = it },
                        label = { Text(text = "Name") },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    OutlinedTextField(
                        modifier = Modifier.clickable {
                            calenderState.show()
                        },
                        readOnly = true,
                        value = date,
                        maxLines = 1,
                        onValueChange = { date = it },
                        label = { Text(text = "Date") },
                        leadingIcon = {
                            Icon(Icons.Default.Phone, contentDescription = null)
                        },
                    )
                    OutlinedTextField(
                        value = amount.toString(),
                        maxLines = 1,
                        onValueChange = { amount = it.toInt() },
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
                            amount = 0
                        }) {
                            Text(text = "Cancel", style = Ams.getMStyle())
                        }
                        TextButton(onClick = {
                            setStatusFn(false)
                            updateLoneFn(
                                DebtorLoan(
                                    loneId = loanWithName?.loanId,
                                    amount = amount,
                                    loneHolder = loanWithName?.debtorId!!,
                                    timestamp = Ams.dateToTimeStamp(date) ,
                                    status = loanWithName.status
                                )
                            )
                        }) {
                            Text(text = "save", style = Ams.getMStyle())
                        }
                    }

                }
            }
        }
    }


}









