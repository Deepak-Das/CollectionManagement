package com.example.collectionmanagement.collection_book.prentation.Loan

import com.example.collectionmanagement.collection_book.domain.model.Debtor
import com.example.collectionmanagement.collection_book.domain.model.DebtorLoan
import com.example.collectionmanagement.collection_book.domain.model.LoanWithName
import com.example.dailymoneyrecord.recorde_Book.domain.util.OrderType
import com.example.dailymoneyrecord.recorde_Book.domain.util.Status

data class LoneState(
    val debtorList:List<Debtor> = emptyList(),
    val loneWithNameList:List<LoanWithName> = emptyList(),
    val filtertloneList:List<LoanWithName> = emptyList(),
    val loneGroup:Map<Long, List<LoanWithName>> = emptyMap(),
    val search: String="",
    val status:Status=Status.Running(OrderType.Descending),
    var isWarning: Boolean=false,
    val deleteLoneData: DebtorLoan? = null,
    val openDialog:Boolean=false,
    val editLoanWithName:LoanWithName? =null
)
