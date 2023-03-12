package com.example.collectionmanagement.collection_book.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.collectionmanagement.collection_book.prentation.theme.*;

@Entity
data class Debtor(
    @PrimaryKey(autoGenerate = true) val debtorId: Int? = null,
    val name: String,
    val address: String?,
    val timestamp: Long,
    val color: Int


//    val date:Date = Date(System.currentTimeMillis())

) {
    companion object{
        fun colorCode() = listOf(option1, option2, option3, option4, option5, option6).random()
    }
}
