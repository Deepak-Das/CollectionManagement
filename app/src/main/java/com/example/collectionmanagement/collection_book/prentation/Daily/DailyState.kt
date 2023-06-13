package com.example.collectionmanagement.collection_book.prentation.Daily

import com.example.collectionmanagement.collection_book.domain.model.DailyPayment
import com.example.collectionmanagement.collection_book.domain.model.Debtor
import com.example.collectionmanagement.collection_book.domain.model.DebtorPayment
import com.example.collectionmanagement.collection_book.prentation.utils.Ams
import com.example.dailymoneyrecord.recorde_Book.domain.util.OrderBy
import com.example.dailymoneyrecord.recorde_Book.domain.util.OrderType

data class DailyState (
    val debtorList:List<Debtor> = emptyList(),
    val dailyPaysList:List<DailyPayment> = emptyList(),
    val date:String= Ams.GLOBLE_DATE,
    val timeStamp:Long =Ams.GLOBLE_TIMSTAMP,
    var editPayment: DailyPayment? =null,
    var deletePayment: DebtorPayment? =null,
    val orderBy: OrderBy= OrderBy.Id(OrderType.Descending),
    val isFilter: Boolean=false

)