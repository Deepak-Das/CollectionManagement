package com.example.collectionmanagement.collection_book.domain.use_case

import com.example.collectionmanagement.collection_book.domain.model.Debtor
import com.example.collectionmanagement.collection_book.domain.repository.DebtorRepository
import javax.inject.Inject

class SaveUpdateDebtor @Inject constructor(private val debtorRepository: DebtorRepository) {

    suspend operator fun invoke(debtor:Debtor){
        return debtorRepository.saveDebtor(debtor);
    }
}