package com.example.collectionmanagement.collection_book.prentation.Daily

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collectionmanagement.collection_book.domain.model.DebtorPayment
import com.example.collectionmanagement.collection_book.domain.use_case.UserCases
import com.example.collectionmanagement.collection_book.prentation.Loan.LoneState
import com.example.dailymoneyrecord.recorde_Book.domain.util.OrderBy
import com.example.dailymoneyrecord.recorde_Book.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor(
    private val useCases:UserCases
) :ViewModel() {

    private val _state = mutableStateOf<DailyState>(DailyState())
    val state: State<DailyState> = _state

    var debtorJob: Job?=null
    var paymentJob: Job?=null

    init {
        viewModelScope.launch {
            getAllDebtor()
            getDailypay()
        }
    }

    private fun getAllDebtor(){
        debtorJob?.cancel()
        debtorJob=useCases.getAllDebtor(orderBy = OrderBy.Name(OrderType.Ascending)).onEach {
            _state.value = state.value.copy(
                debtorList = it
            )
        }.launchIn(viewModelScope)
    }

   fun  getDailypay(){
       paymentJob?.cancel()
       paymentJob=useCases.dailyPayment(state.value.timeStamp,state.value.orderBy).onEach {
           _state.value = state.value.copy(
               dailyPaysList = it
           )
       }.launchIn(viewModelScope)
   }

    fun SaveUpdatePays(debtorPayment: DebtorPayment){
        viewModelScope.launch {
            useCases.saveUpdatePayment(debtorPayment)
        }
    }

    fun setDate(timeStamp: Long,date: String) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                date = date,
                timeStamp = timeStamp
            )
            getDailypay()
        }
    }

    fun setTimeStamp(timeStamp: Long) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                timeStamp = timeStamp
            )
        }
    }
}

