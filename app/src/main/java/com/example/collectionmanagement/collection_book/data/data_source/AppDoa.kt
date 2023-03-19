package com.example.collectionmanagement.collection_book.data.data_source

import androidx.room.*
import com.example.collectionmanagement.collection_book.domain.model.Debtor
import com.example.collectionmanagement.collection_book.domain.model.DebtorLoan
import com.example.collectionmanagement.collection_book.domain.model.DebtorPayment
import kotlinx.coroutines.flow.Flow


@Dao
interface AppDoa {

    //Inserts

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDebtor(debtor: Debtor)
    @Delete()
    suspend fun deleteDebtor(debtor: Debtor)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLone(lone: DebtorLoan)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePay(debtorPayment: DebtorPayment)


    //Gets
    @Query("SELECT * FROM Debtor")
    fun getDebtors():Flow<List<Debtor>>;
}