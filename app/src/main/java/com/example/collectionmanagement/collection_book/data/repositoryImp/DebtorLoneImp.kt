package com.example.collectionmanagement.collection_book.data.repositoryImp

import com.example.collectionmanagement.collection_book.data.data_source.AppDoa
import com.example.collectionmanagement.collection_book.domain.model.DebtorLoan
import com.example.collectionmanagement.collection_book.domain.model.LoanWithName
import com.example.collectionmanagement.collection_book.domain.repository.DebtorLoneRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DebtorLoneImp @Inject constructor (private val appDoa: AppDoa): DebtorLoneRepository {
    override suspend fun saveUpdateLone(debtorLoan: DebtorLoan) {
        appDoa.saveLone(debtorLoan)
    }

    override suspend fun saveUpdateALLone(loans: List<DebtorLoan>) {
        appDoa.saveAllLone(loans)
    }

    override suspend fun getLoanByDebtorId(id: Int): Flow<List<DebtorLoan>> {
        return appDoa.getLoanByDebtorId(id)
    }

    override suspend fun deleteLone(debtorLoan: DebtorLoan) {
        appDoa.deleteLone(debtorLoan)
    }

    override fun getAllLone(): Flow<List<LoanWithName>> {
        return  appDoa.getAllDebtorLoans();
    }

}