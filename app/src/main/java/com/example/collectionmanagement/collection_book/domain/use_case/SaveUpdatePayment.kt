package com.example.collectionmanagement.collection_book.domain.use_case

import com.example.collectionmanagement.collection_book.domain.model.DebtorPayment
import com.example.collectionmanagement.collection_book.domain.repository.PaymentRepository

class SaveUpdatePayment(private val repository: PaymentRepository) {

    suspend operator fun invoke(payment:DebtorPayment){
        repository.saveUpdatePay(payment)
    }
}