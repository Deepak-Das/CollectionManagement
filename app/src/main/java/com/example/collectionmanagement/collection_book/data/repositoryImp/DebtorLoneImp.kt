package com.example.collectionmanagement.collection_book.data.repositoryImp

import com.example.collectionmanagement.collection_book.data.data_source.AppDoa
import com.example.collectionmanagement.collection_book.domain.model.DebtorLoan
import com.example.collectionmanagement.collection_book.domain.model.LoanWithName
import com.example.collectionmanagement.collection_book.domain.repository.DebtorLoneRepository
import kotlinx.coroutines.flow.Flow

 class DebtorLoneImp(private val appDoa: AppDoa): DebtorLoneRepository {
    override suspend fun saveUpdateLone(debtorLoan: DebtorLoan) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLone(debtorLoan: DebtorLoan) {
        TODO("Not yet implemented")
    }

    override fun getAllLone(): Flow<List<LoanWithName>> {
        return  appDoa.getAllDebtorLoans();
    }

}