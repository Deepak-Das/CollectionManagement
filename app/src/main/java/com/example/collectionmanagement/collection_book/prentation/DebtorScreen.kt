package com.example.collectionmanagement.collection_book.prentation

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.collectionmanagement.collection_book.domain.utils.Ams
import com.example.collectionmanagement.collection_book.prentation.Debtor.CustomSearchBar
import com.example.collectionmanagement.collection_book.prentation.Debtor.DebtorCard
import com.example.collectionmanagement.collection_book.prentation.Debtor.DebtorViewModel

//import androidx.compose.material.icons


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DebtorScreen(
    viewModel: DebtorViewModel = hiltViewModel()
) {

    var scope = rememberCoroutineScope()
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
        }
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
                        DebtorCard(debtor = it)
                        Spacer(modifier = Modifier.size(10.dp))
                    }
                } else {
                    items(state.list) {
                        DebtorCard(debtor = it)
                        Spacer(modifier = Modifier.size(10.dp))
                    }
                }
            }



            AddDebtor(
                status = state.addStatus,
                openDialog = viewModel::setAddStatus,
                setVal = viewModel::saveUpdate
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


    if (status) {
        AlertDialog(
            onDismissRequest = { openDialog(false) }
        ) {
            Card() {
                Column(
                    Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Add Debtor", style = Ams.getMStyle())
                    Spacer(modifier = Modifier.size(14.dp))

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text(text = "Name") },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    OutlinedTextField(
                        value = addr,
                        onValueChange = { addr = it },
                        label = { Text(text = "Phone") },
                        leadingIcon = {
                            Icon(Icons.Default.Phone, contentDescription = null)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )
                    OutlinedTextField(
                        value = addr,
                        onValueChange = { addr = it },
                        label = { Text(text = "Address") },
                        leadingIcon = {
                            Icon(Icons.Default.LocationCity, contentDescription = null)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = { openDialog(false) }) {
                            Text(text = "Cancel", style = Ams.getMStyle())
                        }
                        TextButton(onClick = {
                            openDialog(false)
                            setVal(null, name, addr, System.currentTimeMillis())
                        }) {
                            Text(text = "save", style = Ams.getMStyle())
                        }
                    }

                }
            }
        }
    }


}







