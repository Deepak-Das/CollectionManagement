package com.example.collectionmanagement.collection_book.domain.use_case

import com.example.collectionmanagement.collection_book.domain.model.LoanWithName
import com.example.collectionmanagement.collection_book.domain.repository.DebtorLoneRepository
import com.example.dailymoneyrecord.recorde_Book.domain.util.OrderType
import com.example.dailymoneyrecord.recorde_Book.domain.util.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllLone @Inject constructor(private  val  repository:DebtorLoneRepository) {

    operator fun invoke(status: Status): Flow<List<LoanWithName>> {
        return repository.getAllLone().map {
            when (status.orderType) {
                is OrderType.Ascending -> {
                    when (status) {
                        is Status.Running -> it.filter { it.status == "Running"}.sortedBy { it.timeStamp }
                        is Status.Paid -> it.filter { it.status == "Paid" }.sortedBy { it.timeStamp }
                        is Status.All -> it.sortedByDescending { it.timeStamp }.sortedByDescending { it.status }.sortedBy { it.DebtorName }

                    }
                }
                is OrderType.Descending -> {
                    when (status) {
                        is Status.Running -> it.filter { it.status == "Running"}.sortedByDescending { it.timeStamp }
                        is Status.Paid -> it.filter { it.status == "Paid" }.sortedByDescending { it.timeStamp }
                        is Status.All -> it.sortedByDescending { it.timeStamp }.sortedByDescending { it.status }.sortedByDescending { it.DebtorName }

                    }
                }
            }

        }

    }

}
