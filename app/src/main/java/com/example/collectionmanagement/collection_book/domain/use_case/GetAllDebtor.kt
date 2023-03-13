package com.example.collectionmanagement.collection_book.domain.use_case

import com.example.collectionmanagement.collection_book.domain.model.Debtor
import com.example.collectionmanagement.collection_book.domain.repository.DebtorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllDebtor @Inject constructor(private val repository: DebtorRepository) {

      operator fun invoke(): Flow<List<Debtor>> {

        return repository.getAllDebtor().map { it ->
            it.sortedByDescending { it.debtorId }
        }
    }

}
