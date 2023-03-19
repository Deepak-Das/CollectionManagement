package com.example.collectionmanagement.collection_book.domain.use_case

import com.example.collectionmanagement.collection_book.domain.model.Debtor
import com.example.collectionmanagement.collection_book.domain.repository.DebtorRepository
import javax.inject.Inject

class DeleteDebtor @Inject constructor(private val debtorRepository: DebtorRepository) {

    suspend operator fun invoke(debtor:Debtor){
        return debtorRepository.deleteDebtor(debtor);
    }
}