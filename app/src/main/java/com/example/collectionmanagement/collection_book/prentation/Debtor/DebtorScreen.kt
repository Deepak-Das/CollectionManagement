package com.example.collectionmanagement.collection_book.prentation

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.collectionmanagement.collection_book.domain.model.Debtor
import com.example.collectionmanagement.collection_book.domain.utils.Ams
import com.example.collectionmanagement.collection_book.prentation.Debtor.CustomSearchBar
import com.example.collectionmanagement.collection_book.prentation.Debtor.DebtorCard
import com.example.collectionmanagement.collection_book.prentation.Debtor.DebtorViewModel
import kotlinx.coroutines.launch


//import androidx.compose.material.icons




@Preview
@Composable
fun DebtorScreen(
    viewModel: DebtorViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val state = viewModel.state.value


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.setAddStatus(true)

            }) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    Modifier.size(60.dp),
                    tint = MaterialTheme.colorScheme.tertiary
                )

            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {it->

               var data=(it.visuals as? Ams.DefaultSnackbar)

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

        ) {
        Column(
            Modifier
                .padding(it)
                .padding(10.dp)
        ) {
            CustomSearchBar(
                getDebtor = {

                },
                list = state.list,
                query = state.search,
                setQuery = viewModel::setSearch,
            )
            Spacer(modifier = Modifier.size(10.dp))

            LazyColumn {
                if (state.list.isNotEmpty()) {
                    items(state.list.filter {
                        it.name.lowercase().contains(state.search.lowercase())
                    }) {
                        DebtorCard(debtor = it, getDebtor = {

                            scope.launch {
                                viewModel.setUpdateStatus(true, it)
                            }
                        },
                            deleteDebtor = {
                                scope.launch {
                                    viewModel.setDeleteDebtor(debtor = it)
                                    viewModel.setWaring(true)
                                }
                            }
                        )


                        Spacer(modifier = Modifier.size(10.dp))
                    }
                } else {
                    items(state.list) {
                        DebtorCard(debtor = it, getDebtor = {
                            scope.launch { viewModel.setUpdateStatus(true, it) }
                        },
                            deleteDebtor = {
                                scope.launch { viewModel.setDeleteDebtor(debtor = it) }
                            }
                        )

                        Spacer(modifier = Modifier.size(10.dp))
                    }
                }
            }



            AddDebtor(
                status = state.addStatus,
                openDialog = viewModel::setAddStatus,
                setVal = viewModel::saveUpdate
            )

            UpdateDebtor(
                status = state.updateStatus,
                openDialog = viewModel::setUpdateStatus,
                debtor = state.updateDebtor,
                setVal = viewModel::saveUpdate
            )

            Ams.Waring(
                msg = "Are u sure want to delete",
                status = state.waring,
                openDialog = viewModel::setWaring,
                onclickSure = {
                    viewModel.deleteDebtor()
                    scope.launch {
                        val snackbarResult = snackbarHostState.showSnackbar(
                            Ams.DefaultSnackbar(message = "want to undo the last delete"),
                        );

                        when (snackbarResult) {
                            SnackbarResult.Dismissed -> Log.d("SnackbarDemo", "Dismissed")
                            SnackbarResult.ActionPerformed -> viewModel.undoDebtor()
                        }

                    }
                },
                onclickCancel = viewModel::setWaring
            )


        }
    }


}


@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun AddDebtor(
    status: Boolean,
    openDialog: (Boolean) -> Unit,
    setVal: (Int?, String, String, Long) -> Unit,

    ) {
    var name by remember {
        mutableStateOf("")
    }
    var addr by remember {
        mutableStateOf("")
    }
    var phone by remember {
        mutableStateOf("")
    }


    if (status) {
        AlertDialog(
            onDismissRequest = { openDialog(false) }
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
                        enabled = false,
                        value = phone,
                        maxLines = 1,
                        onValueChange = { phone = it },
                        label = { Text(text = "Phone") },
                        leadingIcon = {
                            Icon(Icons.Default.Phone, contentDescription = null)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )
                    OutlinedTextField(
                        value = addr,
                        maxLines = 1,
                        onValueChange = { addr = it },
                        label = { Text(text = "Address") },
                        leadingIcon = {
                            Icon(Icons.Default.LocationCity, contentDescription = null)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = {
                            openDialog(false)
                            name = ""
                            addr = ""
                        }) {
                            Text(text = "Cancel", style = Ams.getMStyle())
                        }
                        TextButton(onClick = {
                            openDialog(false)
                            setVal(null, name, addr, System.currentTimeMillis())
                            name = ""
                            addr = ""
                        }) {
                            Text(text = "save", style = Ams.getMStyle())
                        }
                    }

                }
            }
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun UpdateDebtor(
    status: Boolean,
    openDialog: (Boolean, Debtor) -> Unit,
    debtor: Debtor,
    setVal: (Int?, String, String, Long) -> Unit,

    ) {


    var name by remember {
        mutableStateOf("")
    }
    var addr by remember {
        mutableStateOf("")
    }
    var phone by remember {
        mutableStateOf("")
    }

    LaunchedEffect(debtor) {
//        if(debtor.address!=null||debtor.address!=""&&debtor.address!="N/A"){
//            val split=debtor.address?.split(',')
//            phone= split?.get(0).toString()
//            addr = split?.get(1).toString()
//        }else {
//            name = debtor.name
//            phone = ""
//            addr = debtor.address ?: ""
//        }
        name = debtor.name
        phone = ""
        addr = debtor.address ?: ""
    }




    if (status) {
        AlertDialog(
            onDismissRequest = { openDialog(false, debtor) }
        ) {
            Card {
                Column(
                    Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Add Debtor", style = Ams.getMStyle())
                    Spacer(modifier = Modifier.size(14.dp))

                    Text("id: ${debtor.debtorId}", style = Ams.getRStyle(color = Color.Gray))

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(text = "Name") },
                        maxLines = 1,
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    OutlinedTextField(
                        enabled = false,
                        value = phone,
                        readOnly = true,
                        onValueChange = { phone = it },
                        label = { Text(text = "Phone") },
                        maxLines = 1,
                        leadingIcon = {
                            Icon(Icons.Default.Phone, contentDescription = null)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )
                    OutlinedTextField(
                        value = addr,
                        onValueChange = { addr = it },
                        label = { Text(text = "Address") },
                        maxLines = 1,
                        leadingIcon = {
                            Icon(Icons.Default.LocationCity, contentDescription = null)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = { openDialog(false, debtor) }) {
                            Text(text = "Cancel", style = Ams.getMStyle())
                        }
                        TextButton(onClick = {
                            openDialog(false, debtor)
                            setVal(debtor.debtorId, name, addr, debtor.timestamp)

                        }) {
                            Text(text = "save", style = Ams.getMStyle())
                        }
                    }

                }
            }
        }
    }


}







