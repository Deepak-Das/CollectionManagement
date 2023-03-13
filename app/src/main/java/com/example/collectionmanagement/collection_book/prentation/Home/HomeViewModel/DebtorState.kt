package com.example.collectionmanagement.collection_book.prentation.Home.HomeViewModel

import com.example.collectionmanagement.collection_book.domain.model.Debtor

data class DebtorState(
     val addStatus:Boolean=false,
     val updateStatus:Boolean=false,
     val list:List<Debtor> = emptyList(),
     val search:String=""
)
