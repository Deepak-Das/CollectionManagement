package com.example.collectionmanagement.collection_book.data.repositoryImp

import com.example.collectionmanagement.collection_book.data.data_source.AppDoa
import com.example.collectionmanagement.collection_book.domain.model.DailyPayment
import com.example.collectionmanagement.collection_book.domain.model.DebtorPayment
import com.example.collectionmanagement.collection_book.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PaymentRepositoryImp @Inject constructor(private val appDoa: AppDoa):PaymentRepository {
    override suspend fun saveUpdatePay(debtorPayment: DebtorPayment) {
        appDoa.savePay(debtorPayment)
    }

    override suspend fun deletePay(debtorPayment: DebtorPayment) {
        appDoa.deletePay(debtorPayment)
    }

    override fun getAllPay(): Flow<List<DailyPayment>> {
       return appDoa.getAllPayments()
    }

    override fun getDailyPay(timestamp: Long): Flow<List<DailyPayment>> {
        return appDoa.getDailyPayments(timestamp)
    }

    override fun getPaymentsByIdAndTimestamp(id: Int, timestamp: Long): Flow<List<DailyPayment>> {
        return appDoa.getPaymentsByIdAndTimestamp(id, timestamp)
    }
}