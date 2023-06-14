package com.example.collectionmanagement.collection_book.domain.use_case

import com.example.collectionmanagement.collection_book.domain.model.DebtorLoan
import com.example.collectionmanagement.collection_book.domain.repository.DebtorLoneRepository
import javax.inject.Inject

class SaveAllLone @Inject constructor(private val repository: DebtorLoneRepository) {
    suspend operator fun invoke(debtorLone:List<DebtorLoan>){
        repository.saveUpdateALLone(debtorLone)
    }

}
