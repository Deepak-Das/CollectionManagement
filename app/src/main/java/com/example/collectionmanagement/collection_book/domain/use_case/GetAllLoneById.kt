package com.example.collectionmanagement.collection_book.domain.use_case

import com.example.collectionmanagement.collection_book.domain.model.DebtorLoan
import com.example.collectionmanagement.collection_book.domain.repository.DebtorLoneRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllLoneById @Inject constructor(private val repository: DebtorLoneRepository) {

    suspend operator fun invoke(debtorId:Int): Flow<List<DebtorLoan>> {
        return repository.getLoanByDebtorId(debtorId)
    }
}
