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
fun PaymentsScreen(
    viewModel: PaymentsViewModel = hiltViewModel(),
//     navHostController: NavHostController,
    context: Context = LocalContext.current,
    id: Int?,
    timeStamp: Long?,
    date: String?,

    ){
    val state=viewModel.state.value
    val scope =rememberCoroutineScope()

    LaunchedEffect(key1 = id, block = {
        viewModel.setDateAndId(timeStamp?:0,date?:"NoDate",id?:0)
        viewModel.getDailyPay(id?:0,timeStamp?:0)
    })

    Scaffold(
        topBar = {
           PaymentRecordHeader(date = state.date, f ={ l, d->

            } ,onFilterClick=viewModel::setIsFilter,state.isFilter,state.totalCount,state.totalAmount)

        },
        ) {
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
            Spacer(modifier = Modifier.height(10.dp))

            AnimatedVisibility(visible =state.isFilter ) {
                PaymentFilterBox( viewModel )
            }
            Spacer(modifier = Modifier.height(20.dp))





            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {


                items(state.dailyPaysList) {

                    PaymentCard(
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



        }
    }

}






@Composable
private fun PaymentRecordHeader(
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
//                            calenderState.show()
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
//                        calenderState.show()
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
private fun PaymentFilterBox(
    viewModel: PaymentsViewModel
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
                    radioObj = OrderBy.Name(viewModel.state.value.orderBy.orderType),
                    selected = viewModel.state.value.orderBy is OrderBy.Name,
                    text = "Name",
                    onClickRadio = viewModel::setOrderBy
                )
                Ams.RadioButtonText(
                    radioObj = OrderBy.TimeStamp(viewModel.state.value.orderBy.orderType),
                    selected = viewModel.state.value.orderBy is OrderBy.TimeStamp,
                    text = "TimeStamp",
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
