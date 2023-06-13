package com.example.collectionmanagement.collection_book.domain.use_case

import com.example.collectionmanagement.collection_book.data.repositoryImp.PaymentRepositoryImp
import com.example.collectionmanagement.collection_book.domain.model.DailyPayment
import com.example.collectionmanagement.collection_book.domain.repository.PaymentRepository

import com.example.dailymoneyrecord.recorde_Book.domain.util.OrderBy
import com.example.dailymoneyrecord.recorde_Book.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPaymentsByIdAndTime(
    private val repository: PaymentRepository
) {
    operator fun invoke(id:Int,timestamp: Long,orderBy: OrderBy): Flow<List<DailyPayment>> {
        return repository.getPaymentsByIdAndTimestamp(id,timestamp).map {
            when (orderBy.orderType) {
                is OrderType.Ascending->{
                    when(orderBy){
                        is OrderBy.Id-> it.sortedBy { it.paymentId }
                        is OrderBy.Name->it.sortedBy { it.debtorName.lowercase() }
                        is OrderBy.TimeStamp->it.sortedBy { it.timeStamp }
                    }
                }
                is OrderType.Descending->{
                    when(orderBy){
                        is OrderBy.Id-> it.sortedByDescending { it.paymentId }
                        is OrderBy.Name->it.sortedByDescending { it.debtorName.lowercase() }
                        is OrderBy.TimeStamp->it.sortedByDescending { it.timeStamp }
                    }
                }
            }

        }
    }
}