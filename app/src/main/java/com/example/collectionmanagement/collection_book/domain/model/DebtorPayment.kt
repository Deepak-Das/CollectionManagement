package com.example.collectionmanagement.collection_book.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class DebtorPayment(
    @PrimaryKey(autoGenerate = true) val paymentId: Int?,
    val paymentHolder: Int?,
    val amount: Int?,
    val timestamp: Long
)


data class DebtorWithPayments(
    @Embedded val debtor: Debtor,
    @Relation(
        parentColumn = "debtorId",
        entityColumn = "paymentHolder"
    )
    val payments: List<DebtorPayment>
)

