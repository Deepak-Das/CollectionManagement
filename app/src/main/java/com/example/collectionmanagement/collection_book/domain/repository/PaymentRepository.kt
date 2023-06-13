package com.example.collectionmanagement.collection_book.domain.repository

import com.example.collectionmanagement.collection_book.domain.model.DailyPayment
import com.example.collectionmanagement.collection_book.domain.model.DebtorPayment
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    suspend fun saveUpdatePay(debtorPayment: DebtorPayment)
    suspend fun deletePay(debtorPayment: DebtorPayment)
    fun getAllPay(): Flow<List<DailyPayment>>
    fun getDailyPay(timestamp: Long): Flow<List<DailyPayment>>
    fun getPaymentsByIdAndTimestamp(id:Int ,timestamp: Long): Flow<List<DailyPayment>>
}