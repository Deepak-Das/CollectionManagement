package com.example.collectionmanagement.collection_book.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DebtorLoan(
    @PrimaryKey(autoGenerate = true) val loneId: Int?,
    val loneHolder: Int,
    val amount: Int,
    val timestamp: Long,
    val status:String
)