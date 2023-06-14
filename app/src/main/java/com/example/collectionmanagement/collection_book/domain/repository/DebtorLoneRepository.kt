package com.example.collectionmanagement.collection_book.domain.repository

import com.example.collectionmanagement.collection_book.domain.model.DebtorLoan
import com.example.collectionmanagement.collection_book.domain.model.LoanWithName
import kotlinx.coroutines.flow.Flow

interface DebtorLoneRepository {

    suspend fun saveUpdateLone(debtorLoan: DebtorLoan);
    suspend fun saveUpdateALLone(loans: List<DebtorLoan>);
    suspend fun getLoanByDebtorId(id:Int): Flow<List<DebtorLoan>>;

    suspend fun deleteLone(debtorLoan: DebtorLoan);

    fun getAllLone():Flow<List<LoanWithName>>

}