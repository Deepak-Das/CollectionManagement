package com.example.collectionmanagement.collection_book.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class DebtorLoan(
    @PrimaryKey(autoGenerate = true) val loneId: Int?,
    val loneHolder: Int,
    val amount: Int,
    val timestamp: Long,
    val status:String
)
data class DebtorWithLoans(
    @Embedded val debtor: Debtor,
    @Relation(
        parentColumn = "debtorId",
        entityColumn = "loneHolder",
    )
    val loan_list: List<DebtorLoan>
)