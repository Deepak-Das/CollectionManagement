package com.example.collectionmanagement.collection_book.data.repositoryImp

import androidx.room.Dao
import com.example.collectionmanagement.collection_book.data.data_source.AppDoa
import com.example.collectionmanagement.collection_book.domain.model.Debtor
import com.example.collectionmanagement.collection_book.domain.repository.DebtorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DebtorRepoImp (private val appDoa: AppDoa) : DebtorRepository {
    override suspend fun saveDebtor(debtor: Debtor) {
        appDoa.saveDebtor(debtor);
    }

    override suspend fun deleteDebtor(debtor: Debtor) {
        appDoa.deleteDebtor(debtor)
    }

    override  fun getAllDebtor(): Flow<List<Debtor>> {
        return appDoa.getDebtors();
    }
}