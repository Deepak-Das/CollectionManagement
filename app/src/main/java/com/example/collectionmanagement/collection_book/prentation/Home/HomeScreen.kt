package com.example.collectionmanagement.collection_book.prentation.Home

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.collectionmanagement.R
import com.example.collectionmanagement.collection_book.domain.utils.Ams
import com.example.collectionmanagement.collection_book.prentation.Home.HomeViewModel.HomeViewModel
import com.example.collectionmanagement.collection_book.prentation.navigation.Router
import com.example.collectionmanagement.collection_book.prentation.theme.option4
import com.example.collectionmanagement.collection_book.prentation.theme.option5
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    viewModel: HomeViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
    navHostController: NavHostController
) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val selectedItem = remember { mutableStateOf(viewModel.drawerList[0]) }
    val scope = rememberCoroutineScope()


    val homeState = viewModel.state.value





    ModalNavigationDrawer(

        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                Modifier.padding(end = 100.dp)
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
                    text = "Back-Up"
                )
                viewModel.drawerList.forEach { item ->
                    NavigationDrawerItem(
                        icon = {
                            Image(
                                painterResource(item.imgR),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = { Text(item.text) },
                        selected = item == selectedItem.value,
                        onClick = {
                            selectedItem.value = item
                            scope.launch {
                                if (selectedItem.value == viewModel.drawerList[1]) {
                                    viewModel.exportDb(context)
                                } else if (selectedItem.value == viewModel.drawerList[2]) {
                                } else {
                                    viewModel.importDb(
                                        context = context, activity = ComponentActivity()
                                    )
                                }
                                drawerState.close()

                            }
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                            .width(400.dp)
                    )
                }
            }
        },

        ) {
        Scaffold(topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Home") }, navigationIcon = {
                IconButton(onClick = {
                    scope.launch {
                        if (drawerState.isClosed) {
                            drawerState.open()
                        }
                    }

                }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "menu")
                }

            })
        }) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(),
                Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Column(
                    Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        Modifier
                            .width(LocalConfiguration.current.screenWidthDp.dp - 40.dp),
                        Arrangement.SpaceBetween,
                        Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier
                            .clip(shape = MaterialTheme.shapes.medium)
                            .border(
                                border = BorderStroke(width = 1.dp, color = Color.Black),
                                shape = MaterialTheme.shapes.medium
                            ), contentAlignment = Alignment.Center, content = {
                            Image(
                                painterResource(R.drawable.maa_tara),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(100.dp)
                            )
                        })
                        Text(
                            text = "JAI MAA TARA",
                            style = TextStyle(
                                color = Color(0xff962C2C),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 24.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    DatePickComp(
                        date = homeState.date,
//                        f = { t, d ->
//
//                            viewModel.setHomeDateAndTimeStamp(timeStamp = t, date = d)
//                        }
                    f=viewModel::setHomeDateAndTimeStamp
                    )
                }



                Column {
                    viewModel.bodyList.map {
                        customButton(
                            iconResource = it.imgR,
                            text = it.text,
                            color = it.color,
                            f = {
                                scope.launch {
                                    navHostController.navigate(route = Router.DebtorScreen.toString())

                                }
                            },
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                    }

                }

                Spacer(modifier = Modifier.size(40.dp))


            }
        }
    }
}

@Composable
fun customButton(
    iconResource: Int, text: String, color: Color, f: () -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }

    Box(modifier = Modifier
        .size(width = 150.dp, height = 50.dp)
        .border(
            border = BorderStroke(width = 1.dp, color = Color.Black),
            shape = RoundedCornerShape(100)
        )
        .clip(shape = RoundedCornerShape(100))
        .clickable(
            onClick = f,
            interactionSource = interactionSource,
            indication = rememberRipple(color = color),
        ), content = {
        Row(Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            content = {
                Box(modifier = Modifier.size(50.dp), content = {
                    Box(
                        modifier = Modifier
                            .clip(shape = CircleShape)
                            .border(
                                border = BorderStroke(width = 1.dp, color = Color.Black),
                                shape = CircleShape
                            )
                            .background(color = color)
                            .aspectRatio(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painterResource(iconResource),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(35.dp)
                        )
                    }

                })
                Text(
                    text = text, style = TextStyle(
                        color = color, fontSize = 16.sp, fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.size(width = 10.dp, height = 0.dp))
            })
    })

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickComp(
    date: String,
    f: (Long, String) -> Unit
) {


    val colInteraction = remember { MutableInteractionSource() }
    val calenderState = com.maxkeppeker.sheets.core.models.base.rememberSheetState()

    Ams.CalenderPop(f=f,calenderState = calenderState)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(10.dp)
            .width(LocalConfiguration.current.screenWidthDp.dp - 130.dp)
            .background(color = option4, shape = ShapeDefaults.Medium)
            .clickable(
                onClick = {
                    Log.d(TAG, "HomePage: Click")
                    calenderState.show()
                },
                interactionSource = colInteraction,
                indication = null
            )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            Arrangement.SpaceBetween,
            Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.calendar_icon),
                contentDescription = "Calender Icon",
                Modifier.size(40.dp)
            )
            Text(
                text = date, style = TextStyle(
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp
                )
            )

        }
    }
}



