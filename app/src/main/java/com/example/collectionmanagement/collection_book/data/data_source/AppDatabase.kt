package com.example.collectionmanagement.collection_book.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.collectionmanagement.collection_book.domain.model.Debtor
import com.example.collectionmanagement.collection_book.domain.model.DebtorLoan
import com.example.collectionmanagement.collection_book.domain.model.DebtorPayment

@Database(
    entities = [
        Debtor::class,
        DebtorLoan::class,
        DebtorPayment::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract val dao: AppDoa

    companion object {
        const val DATABASE_NAME = "debtor_Recorde_DB"
    }
}