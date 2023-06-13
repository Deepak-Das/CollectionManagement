package com.example.dailymoneyrecord.recorde_Book.domain.use_case.cases

import com.example.collectionmanagement.collection_book.domain.model.DebtorPayment
import com.example.collectionmanagement.collection_book.domain.repository.PaymentRepository


class DeletePayment(private val repository: PaymentRepository) {

    suspend operator fun invoke(payment: DebtorPayment) {
        repository.deletePay(payment)
    }
}