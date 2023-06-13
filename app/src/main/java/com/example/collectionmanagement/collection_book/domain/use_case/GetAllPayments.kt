package com.example.dailymoneyrecord.recorde_Book.domain.use_case.cases

import com.example.collectionmanagement.collection_book.domain.model.DailyPayment
import com.example.collectionmanagement.collection_book.domain.repository.PaymentRepository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllPayments(
    private val repository: PaymentRepository
) {

    operator fun invoke():Flow<List<DailyPayment>> {
        return repository.getAllPay()
    }

}