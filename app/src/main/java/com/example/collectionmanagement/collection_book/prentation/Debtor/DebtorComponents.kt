package com.example.collectionmanagement.collection_book.prentation.Debtor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.zIndex
import com.example.collectionmanagement.R
import com.example.collectionmanagement.collection_book.domain.model.Debtor
import com.example.collectionmanagement.collection_book.domain.utils.Ams
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebtorCard(
    debtor: Debtor,
    getDebtor: (Debtor) -> Unit,
    deleteDebtor: (Debtor) -> Unit,
) {
    Card(
        modifier=Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )

    ) {


            Row(
                modifier = Modifier.fillMaxSize().padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Spacer(modifier = Modifier.size(10.dp))

                    Box(
                        modifier = Modifier
                            .width(6.dp)
                            .height(100.dp)
                            .background(
                                color = Color(debtor.color),
                                shape = MaterialTheme.shapes.medium
                            ),
                    )
                    Spacer(modifier = Modifier.size(10.dp))

                    Column {
                        CustomIconText(icon = Icons.Default.Person, txt = debtor.name)
                        Spacer(modifier = Modifier.size(10.dp))
                        CustomIconText(icon = Icons.Default.Phone, txt = "+91")
                        Spacer(modifier = Modifier.size(10.dp))
                        Row(
                            modifier=Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                        CustomIconText(
                            icon = Icons.Default.LocationOn,
                            txt = debtor.address.toString()
                        )
                            Box(modifier =Modifier.align (alignment = Alignment.Bottom),
                                contentAlignment = Alignment.Center
                            ){
                                Row(horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        Modifier
                                            .background(
                                                color = MaterialTheme.colorScheme.error,
                                                shape = MaterialTheme.shapes.medium
                                            )
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                            .align(alignment = Alignment.Bottom)
                                            .zIndex(2f)
                                            .clickable {
                                                deleteDebtor(debtor)
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Image(
                                                painter = painterResource(id = R.drawable.edit),
                                                contentDescription = null,
                                                Modifier.size(15.dp)
                                            )
                                            Spacer(modifier = Modifier.size(width = 5.dp, height = 0.dp))
                                            Text(
                                                text = "Delete",
                                                style = Ams.getMStyle(color = Color.White, fontSize = 10.sp)
                                            )
                                            Spacer(modifier = Modifier.size(width = 10.dp, height = 0.dp))


                                        }
                                    }

                                    Spacer(modifier = Modifier.size(10.dp))

                                    Box(
                                        Modifier
                                            .background(
                                                color = MaterialTheme.colorScheme.primary,
                                                shape = MaterialTheme.shapes.medium
                                            )
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                            .align(alignment = Alignment.Bottom)
                                            .zIndex(2f)
                                            .clickable {
                                                getDebtor(debtor)
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Image(
                                                painter = painterResource(id = R.drawable.edit),
                                                contentDescription = null,
                                                Modifier.size(15.dp)
                                            )
                                            Spacer(modifier = Modifier.size(width = 5.dp, height = 0.dp))
                                            Text(
                                                text = "Edit",
                                                style = Ams.getMStyle(color = Color.White, fontSize = 10.sp)
                                            )
                                            Spacer(modifier = Modifier.size(width = 5.dp, height = 0.dp))

                                        }
                                    }
                                }
                            }
                        }
                    }

                }






            }



    }
}

@Composable
fun CustomIconText(
    icon: ImageVector,
    txt: String
) {
    Row(
//        modifier=Modifier.widthIn(max = 160.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = "", tint = Color.Black)
        Spacer(modifier = Modifier.size(width = 6.dp, height = 0.dp))
        Text(txt, style = Ams.getMStyle(), softWrap = true)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun CustomSearchBar(
    getDebtor: (Debtor) -> Unit,
    list: List<Debtor>,
    query: String = "",
    setQuery: (String) -> Unit
) {

    var interactionSource = remember {
        MutableInteractionSource();
    }

    var textfieldsize by remember {
        mutableStateOf(Size.Zero)
    }
    var expend by remember {
        mutableStateOf(false)
    }


    Column(Modifier
        .fillMaxWidth()
        .background(color = Color.Transparent)
        .clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = { expend = true }
        )) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .onGloballyPositioned {
                    textfieldsize = it.size.toSize()
                },
            value = query,
            maxLines=1,
            onValueChange = {
                setQuery(it)

            },
            placeholder = { Text(text = "search") },
            trailingIcon = {
                IconButton(onClick = { expend = !expend }) {
                    Icon(
                        if (expend) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
                        contentDescription = null
                    )
                }

            },
            leadingIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.MoreVert, contentDescription = null)
                }
            },
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.textFieldColors(
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )

        )

        AnimatedVisibility(visible = expend) {
            Card(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.0.dp
                ),
                modifier = Modifier
                    .width(textfieldsize.width.dp)
                    .background(Color.White, shape = MaterialTheme.shapes.small)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .heightIn(max = 150.dp)
                        .width(textfieldsize.width.dp)
                        .background(Color.White, shape = MaterialTheme.shapes.small)
                ) {

                    if (list.isNotEmpty()) {
                        items(list.filter {
                            it.name.lowercase().contains(query.lowercase())
                        }.sortedBy { it.name }
                        ) {

                            DropdownMenuItem(text = {
                                CustomIconText(
                                    icon = Icons.Default.Person,
                                    txt = it.name
                                )
                            },
                                onClick = {
                                    expend = false
                                    getDebtor(it)
                                })
                        }

                    } else {
                        items(list.sortedBy { it.name }) {
                            DropdownMenuItem(text = {
                                CustomIconText(
                                    icon = Icons.Default.Person,
                                    txt = it.name
                                )
                            },
                                onClick = {
                                    expend = false
                                    getDebtor(it)
                                })
                        }
                    }
                }
            }

        }

    }


}


