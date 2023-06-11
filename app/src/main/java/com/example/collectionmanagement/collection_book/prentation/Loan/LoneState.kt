package com.example.collectionmanagement.collection_book.prentation.Loan

import com.example.collectionmanagement.collection_book.domain.model.Debtor
import com.example.collectionmanagement.collection_book.domain.model.LoanWithName

data class LoneState(
    val debtorList:List<Debtor> = emptyList(),
    val loneWithNameList:List<LoanWithName> = emptyList(),
    val search: String="",
    val openDialog:Boolean=false,
    val editLoanWithName:LoanWithName? =null
)
