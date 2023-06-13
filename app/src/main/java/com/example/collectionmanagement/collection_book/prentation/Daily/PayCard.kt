package com.example.collectionmanagement.collection_book.prentation.Daily

import com.example.collectionmanagement.collection_book.domain.model.DailyPayment
import com.example.collectionmanagement.collection_book.domain.model.DebtorPayment


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.collectionmanagement.R
import com.example.collectionmanagement.collection_book.domain.model.DebtorLoan
import com.example.collectionmanagement.collection_book.domain.model.LoanWithName
import com.example.collectionmanagement.collection_book.prentation.utils.Ams
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebtorPayCard(
    dailyPayment: DailyPayment,
    editLone: (DebtorPayment) -> Unit,
    onClickDelete: (DebtorPayment) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
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
                        .height(100.dp)
                        .background(
                            color = Color(dailyPayment.color),
                            shape = MaterialTheme.shapes.medium
                        ),
                )
                Spacer(modifier = Modifier.size(10.dp))

                Column(Modifier.fillMaxWidth()) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomIconText(icon = Icons.Default.Person, txt = dailyPayment.debtorName)
                        //todo: add Loan
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                    CustomIconText(
                        icon = Icons.Default.DateRange,
                        txt = Ams.timeStampToDate(timeStamp = dailyPayment.timeStamp)
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomIconText(
                            icon = Icons.Default.Money,
                            txt = dailyPayment.amount.toString()
                        )
                        Box(
                            modifier = Modifier.align(alignment = Alignment.Bottom),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceAround,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
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
                                            var dp = DebtorPayment(
                                                paymentId = dailyPayment.paymentId,
                                                paymentHolder  = dailyPayment.debtorId,
                                                amount = dailyPayment.amount,
                                                timestamp = dailyPayment.timeStamp,
                                            )
                                            onClickDelete(dp)
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Image(
                                            painter = painterResource(id = R.drawable.edit),
                                            contentDescription = null,
                                            Modifier.size(15.dp)
                                        )
                                        Spacer(
                                            modifier = Modifier.size(
                                                width = 5.dp,
                                                height = 0.dp
                                            )
                                        )
                                        Text(
                                            text = "Delete",
                                            style = Ams.getMStyle(
                                                color = Color.White,
                                                fontSize = 10.sp
                                            )
                                        )
                                        Spacer(
                                            modifier = Modifier.size(
                                                width = 10.dp,
                                                height = 0.dp
                                            )
                                        )


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
                                            var dp = DebtorPayment(
                                                paymentId = dailyPayment.paymentId,
                                                paymentHolder  = dailyPayment.debtorId,
                                                amount = dailyPayment.amount,
                                                timestamp = dailyPayment.timeStamp,
                                            )
                                            editLone(dp)
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Image(
                                            painter = painterResource(id = R.drawable.edit),
                                            contentDescription = null,
                                            Modifier.size(15.dp)
                                        )
                                        Spacer(
                                            modifier = Modifier.size(
                                                width = 5.dp,
                                                height = 0.dp
                                            )
                                        )
                                        Text(
                                            text = "Edit",
                                            style = Ams.getMStyle(
                                                color = Color.White,
                                                fontSize = 10.sp
                                            )
                                        )
                                        Spacer(
                                            modifier = Modifier.size(
                                                width = 5.dp,
                                                height = 0.dp
                                            )
                                        )

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



