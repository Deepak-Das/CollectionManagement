package com.example.collectionmanagement.collection_book.domain.use_case

import com.example.collectionmanagement.collection_book.domain.model.DebtorLoan
import com.example.collectionmanagement.collection_book.domain.repository.DebtorLoneRepository
import javax.inject.Inject

class SaveLone @Inject constructor(private val repository: DebtorLoneRepository) {
    suspend operator fun invoke(debtorLone:DebtorLoan){
        repository.saveUpdateLone(debtorLone)
    }

}
