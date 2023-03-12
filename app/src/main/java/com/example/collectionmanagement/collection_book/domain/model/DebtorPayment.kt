package com.example.collectionmanagement.collection_book.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DebtorPayment(
    @PrimaryKey(autoGenerate = true) val paymentId: Int?,
    val paymentHolder: Int?,
    val amount: Int?,
    val timestamp: Long
)
