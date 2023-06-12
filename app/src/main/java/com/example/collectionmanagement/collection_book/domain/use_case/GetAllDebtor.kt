package com.example.collectionmanagement.collection_book.domain.use_case

import com.example.collectionmanagement.collection_book.domain.model.Debtor
import com.example.collectionmanagement.collection_book.domain.repository.DebtorRepository
import com.example.dailymoneyrecord.recorde_Book.domain.util.OrderBy
import com.example.dailymoneyrecord.recorde_Book.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllDebtor @Inject constructor(private val repository: DebtorRepository) {

    operator fun invoke(orderBy: OrderBy): Flow<List<Debtor>> {
        return repository.getAllDebtor().map { debtors ->

            when (orderBy.orderType) {
                is OrderType.Ascending->{
                    when(orderBy){
                        is OrderBy.Id-> debtors.sortedBy { it.debtorId }
                        is OrderBy.Name->debtors.sortedBy { it.name.lowercase() }
                        is OrderBy.TimeStamp->debtors.sortedBy { it.timestamp }
                    }
                }
                is OrderType.Descending->{
                    when(orderBy){
                        is OrderBy.Id-> debtors.sortedByDescending { it.debtorId }
                        is OrderBy.Name->debtors.sortedByDescending { it.name.lowercase() }
                        is OrderBy.TimeStamp->debtors.sortedByDescending { it.timestamp }
                    }
                }
            }
        }
    }


}
