package com.example.collectionmanagement.collection_book.data.data_source

import androidx.room.*
import com.example.collectionmanagement.collection_book.domain.model.Debtor
import com.example.collectionmanagement.collection_book.domain.model.DebtorLoan
import com.example.collectionmanagement.collection_book.domain.model.DebtorPayment
import com.example.collectionmanagement.collection_book.domain.model.LoanWithName
import kotlinx.coroutines.flow.Flow


@Dao
interface AppDoa {

    //Inserts

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDebtor(debtor: Debtor)
    @Delete()
    suspend fun deleteDebtor(debtor: Debtor)

    @Query("SELECT * FROM Debtor")
    fun getDebtors():Flow<List<Debtor>>;

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLone(lone: DebtorLoan)

    @Query("SELECT L.loneId as loanId,D.debtorId as debtorId ,D.name as DebtorName, L.amount as LoneAmount , L.timestamp as timeStamp, D.color as color,L.status as status FROM DebtorLoan as L INNER JOIN Debtor as D WHERE L.loneHolder== D.debtorId ")
    fun getAllDebtorLoans(): Flow<List<LoanWithName>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePay(debtorPayment: DebtorPayment)

    //Gets




}