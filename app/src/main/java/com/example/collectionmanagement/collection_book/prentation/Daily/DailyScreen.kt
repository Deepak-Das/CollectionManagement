package com.example.collectionmanagement.collection_book.prentation.Daily

import android.content.Context
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
import com.example.collectionmanagement.R
import com.example.collectionmanagement.collection_book.domain.model.DailyPayment
import com.example.collectionmanagement.collection_book.domain.model.DebtorLoan
import com.example.collectionmanagement.collection_book.domain.model.DebtorPayment
import com.example.collectionmanagement.collection_book.prentation.Debtor.CustomIconText
import com.example.collectionmanagement.collection_book.prentation.theme.option4
import com.example.collectionmanagement.collection_book.prentation.utils.Ams
import com.example.dailymoneyrecord.recorde_Book.domain.util.OrderBy
import com.example.dailymoneyrecord.recorde_Book.domain.util.OrderType
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import kotlinx.coroutines.launch

@Composable
fun DailyScreen(
     viewModel: DailyViewModel = hiltViewModel(),
//     navHostController: NavHostController,
     context: Context = LocalContext.current
){
     val state=viewModel.state.value
     val scope =rememberCoroutineScope()
     Scaffold(
          topBar = {
               DailyRecordHeader(date = state.date, f ={ l, d->
                    viewModel.setDate(l,d)
                    viewModel.setTimeStamp(l)
               } ,onFilterClick=viewModel::setIsFilter,state.isFilter,state.totalCount,state.totalAmount)

          },
          bottomBar = {
         BottomBarBox(viewModel = viewModel)
     }) {
          Column(
               Modifier
                    .padding(it)
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),
          horizontalAlignment = Alignment.CenterHorizontally
               ) {

/*
                              if(state.hasFocus){
                    Spacer(modifier = Modifier.height(70.dp))

               }
*/
               Spacer(modifier = Modifier.width(10.dp))
               AnimatedVisibility(visible =state.isFilter ) {
                    DailyFilterBox( viewModel )
                    Spacer(modifier = Modifier.height(20.dp))
               }




               LazyColumn(
                    modifier = Modifier
                         .fillMaxWidth()
                         .weight(1f),
               ) {


                         items(state.dailyPaysList) {

                              DailyCard(
                                   dailyPayment = it,
                                   onEditClick = {
                                              viewModel.setEditPay(it)
                                        viewModel.setIsDebtorExpended(false)
                                   },
                                   onClickDelete = {
                                        viewModel.deletePay(it)

                                   },
                                   onClickAddLoan = {
                                        viewModel.setIsAddLone(true)
                                        viewModel.setLoneDebtor(it)

//                                        navHostController.navigate(route = Router.PaymentByIdScreen.toString())



                                   },
                                   onSetAllClick = {
                                        viewModel.setIsWarning(true)
                                        viewModel.setDpl(it)
                                   }

                              )
                              Spacer(modifier = Modifier.size(10.dp))

                         }

               }

               Ams.Waring(
                    status = state.isWaring,
                    msg = "Are You sure want to make all loan to be Paid",
                    openDialog =viewModel::setIsWarning ,
                    onclickSure = {
                         scope.launch {
                              viewModel.setAllPaid(state.dpl, context)
                         }
                                  },
                    onclickCancel =viewModel::setIsWarning
               )

               AddDebtorLone(
                    status = state.isAddLone,
                    setStatusFn = viewModel::setIsAddLone,
                    onClickAdd = {
                         viewModel.setIsAddLone(false)

                                 viewModel.saveLoan(it)
                    },
                    dailyPayment = state.dailyLoneDebtor,
                    viewModel = viewModel
               )

          }
     }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddDebtorLone(
     status: Boolean,
     setStatusFn: (Boolean) -> Unit,
     onClickAdd: (DebtorLoan) -> Unit,
     viewModel: DailyViewModel,
     dailyPayment: DailyPayment?,
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

     LaunchedEffect(key1 = dailyPayment, block = {
          date=viewModel.state.value.date
          name= dailyPayment?.debtorName ?:""
          debtorId =dailyPayment?.debtorId?:null
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
                              readOnly = true,
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
                                        modifier = Modifier.clickable { },
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null)
                              },
                              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomBarBox(
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
     var paymentId by remember {
          mutableStateOf<Int?>(null)
     }
     var amount by remember {
          mutableStateOf("")
     }

     var searchFieldsize by remember {
          mutableStateOf(Size.Zero)
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
               paymentId=it.paymentId

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



               AnimatedVisibility(visible = viewModel.state.value.isDebtorExpend) {
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
                                                       viewModel.setIsDebtorExpended(false)
                                                       debtorId = it.debtorId
                                                       name = it.name
                                                       focusRequesterName.freeFocus()
                                                       focusRequesterAmount.requestFocus()
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
                                                       viewModel.setIsDebtorExpended(false)
                                                       debtorId = it.debtorId
                                                       name = it.name
                                                       focusRequesterName.freeFocus()
                                                       focusRequesterAmount.requestFocus()


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
                              viewModel.setIsDebtorExpended(it.isFocused)
                              viewModel.setHasFocus(it.hasFocus)
                         }
                         .focusRequester(focusRequesterName),
                    singleLine=true,
                    value = name,
                    maxLines = 1,
                    keyboardActions = KeyboardActions {
                     scope.launch {
                          focusRequesterName.freeFocus()
                          focusRequesterAmount.requestFocus()
                     }
                    },

                    onValueChange = { name = it },
                    label = { Text(text = "Name") },
//
                    leadingIcon = {
                         Icon(
                              modifier = Modifier.clickable {
                                   scope.launch {
                                        focusRequesterAmount.freeFocus()
                                        focusRequesterName.requestFocus()
                                        viewModel.setIsDebtorExpended(true)
                                   }
                                                            },
                              imageVector = Icons.Default.Person,
                              contentDescription = null)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next)
               )

               OutlinedTextField(
                    value = amount,
                    modifier = Modifier
                         .focusRequester(focusRequesterAmount)
                         .onFocusChanged {
                              viewModel.setHasFocus(it.hasFocus)

                         },
                    maxLines = 1,
                    onValueChange = { amount = it },
                    label = { Text(text = "Amount") },
                    leadingIcon = {
                         Icon(modifier = Modifier.clickable {
                              focusRequesterName.freeFocus()
                         },imageVector = Icons.Default.Money, contentDescription = null)
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions{
                         scope.launch{
                              if(amount==""||debtorId==null){
                                   Toast.makeText(context,"Can't Save",Toast.LENGTH_SHORT).show()
                                   return@launch
                              }
                             var  savePay=DebtorPayment(
                                paymentId = paymentId,
                                  paymentHolder = debtorId,
                                  amount=amount.toInt(),
                                  timestamp = viewModel.state.value.timeStamp
                              )
                              viewModel.saveUpdatePay(savePay)
                              name=""
                              debtorId=null
                              paymentId=null
                              amount=""
                              focusRequesterAmount.freeFocus()
                              focusRequesterName.requestFocus()
                         }
                    }
               )

          }
     }



}


@Composable
private fun DailyRecordHeader(
     date: String,
     f: (Long, String) -> Unit,
     onFilterClick:(Boolean)->Unit,
     isFilter:Boolean,
     totalCount:Int,
     totalAmount:Int
) {


     val colInteraction = remember { MutableInteractionSource() }
     val calenderState = rememberSheetState()

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
                         .size(30.dp),
                         painter = painterResource(id = R.drawable.calendar_icon),
                         contentDescription = "Calender Icon",

                    )
                    Spacer(modifier = Modifier.width(40.dp))
                    Text(modifier = Modifier.clickable(
                         onClick = {
                              calenderState.show()
                         },
                         interactionSource = colInteraction,
                         indication = null
                    ),
                         text = date, style = TextStyle(
                              color = Color.White,
                              fontWeight = FontWeight.SemiBold,
                              fontSize = 18.sp
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
                    Text(totalAmount.toString(), style = Ams.getBStyle(color = Color.Green, fontSize = 16.sp))
                    Box(
                         modifier = Modifier
                              .width(6.dp)
                              .height(30.dp)
                              .background(
                                   color = Color.White,
                                   shape = MaterialTheme.shapes.medium
                              ),
                    )
                    Text(totalCount.toString(), style = Ams.getBStyle(color = Color.Green, fontSize = 16.sp))

               }
               Spacer(modifier = Modifier.height(10.dp))
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
               containerColor = MaterialTheme.colorScheme.inversePrimary
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
