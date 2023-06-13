package com.example.collectionmanagement.collection_book.data.data_source

import androidx.room.*
import com.example.collectionmanagement.collection_book.domain.model.*
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




    //Lone
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLone(lone: DebtorLoan)

    @Delete
    suspend fun deleteLone(debtorLoan: DebtorLoan)

    @Query("SELECT L.loneId as loanId,D.debtorId as debtorId ,D.name as DebtorName, L.amount as LoneAmount , L.timestamp as timeStamp, D.color as color,L.status as status FROM DebtorLoan as L INNER JOIN Debtor as D WHERE L.loneHolder== D.debtorId ")
    fun getAllDebtorLoans(): Flow<List<LoanWithName>>


    @Query("SELECT L.loneId as loanId,D.debtorId as debtorId ,D.name as DebtorName, L.amount as LoneAmount , L.timestamp as timeStamp, D.color as color,L.status as status FROM DebtorLoan as L INNER JOIN Debtor as D WHERE L.loneHolder== D.debtorId AND D.debtorId==:id ")
    fun getLoanByDebtorId(id: Int): Flow<List<LoanWithName>>





    //Payments
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePay(debtorPayment: DebtorPayment)

    @Delete
    suspend fun deletePay(debtorPayment: DebtorPayment)

    @Query(
        "SELECT D.color as color,D.debtorId as debtorId,paymentId as paymentId,D.name as debtorName,P.timestamp as timeStamp ,p.amount as amount FROM DebtorPayment as P INNER JOIN Debtor as D on P.paymentHolder=D.debtorId where p.timestamp== :timestamp "
    )
    fun getDailyPayments(timestamp: Long): Flow<List<DailyPayment>>

    @Query("SELECT D.color as color,D.debtorId as debtorId,paymentId as paymentId,D.name as debtorName,P.timestamp as timeStamp ,p.amount as amount FROM DebtorPayment as P INNER JOIN Debtor as D on P.paymentHolder=D.debtorId where P.paymentHolder==:id AND p.timestamp > :timestamp ")
    fun getPaymentsByIdAndTimestamp(id: Int, timestamp: Long): Flow<List<DailyPayment>>


    @Query("SELECT D.color as color,D.debtorId as debtorId,paymentId as paymentId,D.name as debtorName,P.timestamp as timeStamp ,p.amount as amount FROM DebtorPayment as P INNER JOIN Debtor as D on P.paymentHolder=D.debtorId where P.paymentHolder == :id LIMIT :limit_st,:limit_ed ")
    fun getPaymentsByIdAndBtwLimit(id: Int, limit_st: Int, limit_ed: Int): Flow<List<DailyPayment>>


    @Query(
        "SELECT D.color as color,D.debtorId as debtorId,paymentId as paymentId,D.name as debtorName,P.timestamp as timeStamp ,p.amount as amount FROM DebtorPayment as P INNER JOIN Debtor as D on P.paymentHolder=D.debtorId  ORDER BY D.name ASC , P.timestamp DESC "
    )
    fun getAllPayments():Flow<List<DailyPayment>>








}