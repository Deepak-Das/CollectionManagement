package com.example.collectionmanagement.collection_book.prentation.Daily

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.collectionmanagement.R
import com.example.collectionmanagement.collection_book.prentation.Debtor.CustomIconText
import com.example.collectionmanagement.collection_book.prentation.Loan.DebtorLoneCard
import com.example.collectionmanagement.collection_book.prentation.Loan.LoneViewModel
import com.example.collectionmanagement.collection_book.prentation.theme.option4
import com.example.collectionmanagement.collection_book.prentation.utils.Ams
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import kotlinx.coroutines.launch

@Composable
fun DailyScreen(
     viewModel: DailyViewModel = hiltViewModel()
){
     val state=viewModel.state.value
     Scaffold(bottomBar = {
         BottomBarBox(viewModel = viewModel)
     }) {
          Column(
               Modifier
                    .padding(it)
                    .fillMaxSize(),
          horizontalAlignment = Alignment.CenterHorizontally
               ) {

               DatePickComp(date = state.date, f ={ l,d->
                    viewModel.setDate(l,d)
                    viewModel.setTimeStamp(l)
               } )

               LazyColumn(
                    modifier = Modifier
                         .fillMaxSize(),
               ) {


                         items(state.dailyPaysList) {

                              DebtorPayCard(
                                   dailyPayment = it,
                                   editLone = {



                                   }, onClickDelete = {

                                   },

                              )
                              Spacer(modifier = Modifier.size(10.dp))

                         }

               }

          }
     }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBarBox(
     viewModel: DailyViewModel,
     context: Context = LocalContext.current

) {

     val calenderState = rememberSheetState()
     val scope= rememberCoroutineScope()

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
          date= Ams.GLOBLE_DATE
     })

     val focusRequesterName = FocusRequester()
     val focusRequesterAmount = FocusRequester()



     Card(modifier = Modifier.fillMaxWidth()) {
          Column(
               Modifier
                    .padding(vertical = 10.dp, horizontal = 20.dp)
                    .fillMaxWidth(),
               horizontalAlignment = Alignment.CenterHorizontally
          ) {

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
                                                  scope.launch{
                                                       isDebtorExpend = false
                                                       debtorId = it.debtorId
                                                       name = it.name
                                                       focusRequesterName.freeFocus()
                                                       focusRequesterAmount.requestFocus()
                                                       focusRequesterAmount.captureFocus()
                                                  }
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
                                                  scope.launch {
                                                       isDebtorExpend = false
                                                       debtorId = it.debtorId
                                                       name = it.name
                                                       focusRequesterName.freeFocus()
                                                       focusRequesterAmount.requestFocus()
                                                       focusRequesterAmount.captureFocus()


                                                  }
                                             })
                                   }
                              }
                         }
                    }

               }

               OutlinedTextField(
                    modifier = Modifier
                         .onGloballyPositioned {
                              searchFieldsize = it.size.toSize()
                         }
                         .onFocusChanged {
                              isDebtorExpend = it.isFocused
                         }
                         .focusRequester(focusRequesterName),
                    singleLine=true,
                    value = name,
                    maxLines = 1,
                    keyboardActions = KeyboardActions {
                     scope.launch {
                          focusRequesterName.freeFocus()
                          focusRequesterAmount.requestFocus()
                          focusRequesterAmount.captureFocus()
                     }
                    },

                    onValueChange = { name = it },
                    label = { Text(text = "Name") },
//
                    leadingIcon = {
                         Icon(
                              modifier = Modifier.clickable { isDebtorExpend=!isDebtorExpend },
                              imageVector = Icons.Default.Person,
                              contentDescription = null)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
               )

               OutlinedTextField(
                    value = amount,
                    modifier = Modifier.focusRequester(focusRequesterAmount),
                    maxLines = 1,
                    onValueChange = { amount = it },
                    label = { Text(text = "Amount") },
                    leadingIcon = {
                         Icon(Icons.Default.Money, contentDescription = null)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions{
                         scope.launch{
                              name=""
                              debtorId=null
                              amount=""
                              focusRequesterAmount.freeFocus()
                              focusRequesterName.requestFocus()
                              focusRequesterName.captureFocus()
                         }
                    }
               )

          }
     }



}


@Composable
fun DatePickComp(
     date: String,
     f: (Long, String) -> Unit
) {


     val colInteraction = remember { MutableInteractionSource() }
     val calenderState = com.maxkeppeker.sheets.core.models.base.rememberSheetState()

     Ams.CalenderPop(f = f, calenderState = calenderState)

     Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
               .padding(10.dp)
               .fillMaxWidth()
               .background(color = option4, shape = ShapeDefaults.Medium)
               .clickable(
                    onClick = {
                         Log.d(ContentValues.TAG, "HomePage: Click")
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
