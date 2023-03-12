package com.example.collectionmanagement.collection_book.prentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.collectionmanagement.R
import com.example.collectionmanagement.collection_book.domain.utils.Ams
import com.example.collectionmanagement.collection_book.prentation.theme.option1

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun DebtorScreen() {
    Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = { Text(text = "Debtor", color = Color.White) },
//                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
//            )
//        }
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        Modifier.size(60.dp),
//                        tint = MaterialTheme.colorScheme.tertiary
                    )
                }

            }
        }
    ) {
        Column(
            Modifier
                .padding(it)
                .padding(10.dp)
        ) {
            CardRow()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CardRow() {
    Card(

        onClick = { /*TODO*/ },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )

    ) {

        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp, end = 20.dp, start = 10.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Spacer(modifier = Modifier.size(10.dp))

                    Box(
                        modifier = Modifier
                            .width(6.dp)
                            .height(100.dp)
                            .background(color = option1, shape = MaterialTheme.shapes.medium),
                    )
                    Spacer(modifier = Modifier.size(10.dp))

                    Column {
                        CustomIconText(icon = Icons.Default.Person, txt = "Samda")
                        Spacer(modifier = Modifier.size(10.dp))
                        CustomIconText(icon = Icons.Default.Phone, txt = "8965237415")
                        Spacer(modifier = Modifier.size(10.dp))
                        CustomIconText(icon = Icons.Default.LocationOn, txt = "High Way")
                    }

                }

                Box(
                    Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .align(alignment = Alignment.Bottom),
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

@Composable
fun CustomIconText(
    icon: ImageVector,
    txt: String
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = "", tint = Color.Black)
        Spacer(modifier = Modifier.size(width = 6.dp, height = 0.dp))
        Text(txt, style = Ams.getMStyle())
    }
}