package com.example.collectionmanagement.collection_book.prentation.Daily

import android.content.ContentValues
import android.content.Context
import android.util.Log
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
import androidx.compose.material.icons.filled.*
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
import androidx.navigation.NavHostController
import com.example.collectionmanagement.R
import com.example.collectionmanagement.collection_book.domain.model.DebtorPayment
import com.example.collectionmanagement.collection_book.prentation.Debtor.CustomIconText
import com.example.collectionmanagement.collection_book.prentation.theme.option4
import com.example.collectionmanagement.collection_book.prentation.theme.option5
import com.example.collectionmanagement.collection_book.prentation.utils.Ams
import com.example.dailymoneyrecord.recorde_Book.domain.util.OrderBy
import com.example.dailymoneyrecord.recorde_Book.domain.util.OrderType
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import kotlinx.coroutines.launch

@Composable
fun DailyScreen(
     viewModel: DailyViewModel = hiltViewModel(),
     navHostController: NavHostController,
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

               //Todo : on expend debtor List
               /*if(state.is){
                    Spacer(modifier = Modifier.height(20.dp))
               }*/
               DatePickComp(date = state.date, f ={ l, d->
                    viewModel.setDate(l,d)
                    viewModel.setTimeStamp(l)
               } ,onFilterClick=viewModel::setIsFilter,state.isFilter)
               Spacer(modifier = Modifier.width(10.dp))
               AnimatedVisibility(visible =state.isFilter ) {
                    DailyFilterBox( viewModel )
               }


               LazyColumn(
                    modifier = Modifier
                         .fillMaxSize(),
               ) {


                         items(state.dailyPaysList) {

                              DebtorPayCard(
                                   dailyPayment = it,
                                   editLone = {
                                              viewModel.setEditPay(it)
                                   },
                                   onClickDelete = {
                                        viewModel.deletePay(it)

                                   },
                                   onClickAddLoan = {
                                        //todo: Get loan by id

//                                        navHostController.navigate(route = Router.PaymentByIdScreen.toString())



                                   }

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
     context: Context = LocalContext.current,

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
     var editPayState=viewModel.state.value.editPayment

     LaunchedEffect(key1 = viewModel.state.value.editPayment, block = {
          editPayState?.let {
               name=it.debtorName
               amount=it.amount.toString()
               debtorId=it.debtorId

          }
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
                             var  savePay=DebtorPayment(
                                paymentId = null,
                                  paymentHolder = debtorId,
                                  amount=amount.toInt(),
                                  timestamp = viewModel.state.value.timeStamp
                              )
                              viewModel.saveUpdatePay(savePay)
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
     f: (Long, String) -> Unit,
     onFilterClick:(Boolean)->Unit,
     isFilter:Boolean
) {


     val colInteraction = remember { MutableInteractionSource() }
     val calenderState = com.maxkeppeker.sheets.core.models.base.rememberSheetState()

     Ams.CalenderPop(f = f, calenderState = calenderState)

     Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
               .padding(10.dp)
               .fillMaxWidth()
               .height(intrinsicSize = IntrinsicSize.Min)
               .background(color = option4, shape = ShapeDefaults.Medium)
     ) {
          Column(
               Modifier
                    .fillMaxWidth()
                    .height(intrinsicSize = IntrinsicSize.Min)) {
               Row(
                    Modifier
                         .fillMaxWidth()
                         .padding(horizontal = 20.dp, vertical = 10.dp),
                    Arrangement.Center,
                    Alignment.CenterVertically
               ) {
                    Image(modifier = Modifier
                         .clickable(
                              onClick = {
                                   calenderState.show()
                              },
                              interactionSource = colInteraction,
                              indication = null
                         )
                         .size(40.dp),
                         painter = painterResource(id = R.drawable.calendar_icon),
                         contentDescription = "Calender Icon",

                    )
                    Spacer(modifier = Modifier.width(40.dp))
                    Text(modifier = Modifier.clickable(
                         onClick = {
                              Log.d(ContentValues.TAG, "HomePage: Click")
                              calenderState.show()
                         },
                         interactionSource = colInteraction,
                         indication = null
                    ),
                         text = date, style = TextStyle(
                              color = Color.White,
                              fontWeight = FontWeight.SemiBold,
                              fontSize = 24.sp
                         )
                    )
                    Spacer(modifier = Modifier.width(40.dp))
                    Icon(modifier = Modifier.clickable {
                         onFilterClick(!isFilter)
                    },
                         imageVector = if(isFilter) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDownCircle , contentDescription = "")


               }
               Row (
                    Modifier
                         .fillMaxWidth()
                         .height(intrinsicSize = IntrinsicSize.Min),
               horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                    ){
                    Text("54100")
                    Box(
                         modifier = Modifier
                              .width(6.dp)
                              .height(50.dp)
                              .background(
                                   color = Color.White,
                                   shape = MaterialTheme.shapes.medium
                              ),
                    )
                    Text("100")

               }
               Spacer(modifier = Modifier.height(20.dp))
          }
     }
}



@Composable
private fun DailyFilterBox(
     viewModel: DailyViewModel
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

               Text("Sort Loan", style = MaterialTheme.typography.headlineMedium.copy(fontSize = 20.sp))
               Spacer(modifier = Modifier.height(10.dp))
               Row(
                    Modifier
                         .widthIn(min = 60.dp)
                         .wrapContentWidth()
                         .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
               ) {
                    Ams.RadioButtonText(
                         radioObj = OrderBy.Id(viewModel.state.value.orderBy.orderType),
                         selected = viewModel.state.value.orderBy is OrderBy.Id,
                         text = "ID",
                         onClickRadio = viewModel::setOrderBy
                    )
                    Ams.RadioButtonText(
                         radioObj = OrderBy.Name(viewModel.state.value.orderBy.orderType),
                         selected = viewModel.state.value.orderBy is OrderBy.Name,
                         text = "Name",
                         onClickRadio = viewModel::setOrderBy
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
                    Ams.RadioButtonText(
                         radioObj = viewModel.state.value.orderBy.changeOrderType(OrderType.Descending),
                         selected = viewModel.state.value.orderBy.orderType is OrderType.Descending,
                         text = "Descending",
                         onClickRadio = viewModel::setOrderBy
                    )
                    Ams.RadioButtonText(
                         radioObj = viewModel.state.value.orderBy.changeOrderType(OrderType.Ascending),
                         selected = viewModel.state.value.orderBy.orderType is OrderType.Ascending,
                         text = "Ascending",
                         onClickRadio = viewModel::setOrderBy
                    )

               }
          }
     }
}
