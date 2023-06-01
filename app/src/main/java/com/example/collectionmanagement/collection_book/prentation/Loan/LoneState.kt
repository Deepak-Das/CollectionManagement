package com.example.collectionmanagement.collection_book.prentation.Loan

import com.example.collectionmanagement.collection_book.domain.model.Debtor

data class LoneState(
    val debtorList:List<Debtor> = emptyList(),
    val search: String=""
)
