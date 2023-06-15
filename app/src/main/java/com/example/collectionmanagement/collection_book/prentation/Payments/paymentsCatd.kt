package com.example.collectionmanagement.collection_book.prentation.Daily

import com.example.collectionmanagement.collection_book.domain.model.DailyPayment
import com.example.collectionmanagement.collection_book.domain.model.DebtorPayment


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.collectionmanagement.collection_book.prentation.theme.option3
import com.example.collectionmanagement.collection_book.prentation.theme.option4
import com.example.collectionmanagement.collection_book.prentation.utils.Ams

@Composable
fun PaymentCard(
    dailyPayment: DailyPayment,
    onEditClick: (DailyPayment) -> Unit,
    onClickDelete: (DebtorPayment) -> Unit,
    onClickAddLoan: (DailyPayment) -> Unit,
    onSetAllClick: (DebtorPayment) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth().height(intrinsicSize = IntrinsicSize.Min),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )

    ) {


        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Spacer(modifier = Modifier.size(10.dp))

                Box(
                    modifier = Modifier
                        .width(6.dp)
                        .height(80.dp)
                        .background(
                            color = Color(dailyPayment.color),
                            shape = MaterialTheme.shapes.medium
                        ),
                )
                Spacer(modifier = Modifier.size(10.dp))

                Column(Modifier.fillMaxWidth()) {
                    CustomPayIconText(icon = Icons.Default.Person, txt = dailyPayment.debtorName)
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(modifier=Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween

                    ){
                        CustomPayIconText(
                            icon = Icons.Default.DateRange,
                            txt = Ams.timeStampToDate(dailyPayment.timeStamp),
                            textStyle = Ams.getBStyle(option4,18.sp)

                        )
                        CustomPayIconText(
                            icon = Icons.Default.Money,
                            txt = dailyPayment.amount.toString()+"/-",
                            textStyle = Ams.getBStyle(option3,18.sp)
                        )


                    }

                    Spacer(modifier = Modifier.size(10.dp))

                }

            }


        }


    }
}

@Composable
private fun CustomPayIconText(
    icon: ImageVector,
    txt: String,
    textStyle: androidx.compose.ui.text.TextStyle =Ams.getMStyle(),

    ) {
    Row(
//        modifier=Modifier.widthIn(max = 160.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = "", tint = Color.Black)
        Spacer(modifier = Modifier.size(width = 6.dp, height = 0.dp))
        Text(txt, style = textStyle, softWrap = true)
    }
}




