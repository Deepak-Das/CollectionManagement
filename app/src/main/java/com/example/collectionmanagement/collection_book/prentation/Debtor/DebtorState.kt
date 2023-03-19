package com.example.collectionmanagement.collection_book.prentation.Debtor

import androidx.compose.ui.graphics.toArgb
import com.example.collectionmanagement.collection_book.domain.model.Debtor

data class DebtorState(
     val addStatus:Boolean=false,
     val waring:Boolean=false,
     val updateStatus:Boolean=false,
     val updateDebtor: Debtor = Debtor(debtorId = null, name = "",address="", timestamp = System.currentTimeMillis(), color = Debtor.colorCode().toArgb()),
     val debtorForDelete: Debtor? =null,
     val list:List<Debtor> = emptyList(),
     val search:String=""
)
