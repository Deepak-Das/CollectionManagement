package com.example.collectionmanagement.collection_book.domain.repository

import com.example.collectionmanagement.collection_book.domain.model.Debtor
import kotlinx.coroutines.flow.Flow

interface DebtorRepository {

    suspend fun saveDebtor(debtor: Debtor);
    suspend fun deleteDebtor(debtor: Debtor);
    fun getAllDebtor(): Flow<List<Debtor>>;
}