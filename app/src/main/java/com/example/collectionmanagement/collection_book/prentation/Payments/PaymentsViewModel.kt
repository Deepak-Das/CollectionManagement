package com.example.collectionmanagement.collection_book.prentation.Daily

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collectionmanagement.collection_book.domain.model.DailyPayment
import com.example.collectionmanagement.collection_book.domain.model.DebtorLoan
import com.example.collectionmanagement.collection_book.domain.model.DebtorPayment
import com.example.collectionmanagement.collection_book.domain.use_case.GetPaymentsByIdAndTime
import com.example.collectionmanagement.collection_book.domain.use_case.UserCases
import com.example.dailymoneyrecord.recorde_Book.domain.util.OrderBy
import com.example.dailymoneyrecord.recorde_Book.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentsViewModel @Inject constructor(
    private val useCases:UserCases
) :ViewModel() {

    private val _state = mutableStateOf<PaymentsState>(PaymentsState())
    val state: State<PaymentsState> = _state

    var debtorJob: Job?=null
    var paymentJob: Job?=null
    var loanJob: Job?=null

    init {
        viewModelScope.launch {
            getAllDebtor()
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

   fun  getDailyPay(id:Int,timeStamp: Long){
       paymentJob?.cancel()
       paymentJob= useCases.paymentsByIdAndTime(id,timeStamp,state.value.orderBy).onEach {
           var totalamount=it.sumOf {
               it.amount
           }
           _state.value = state.value.copy(
               dailyPaysList = it,
               totalCount = it.size,
               totalAmount = totalamount
           )




       }.launchIn(viewModelScope)
   }



    fun setDateAndId(timeStamp: Long,date: String,id:Int) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                date = date,
                timeStamp = timeStamp,
                id = id
            )
        }
    }


    fun setIsFilter(value: Boolean) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isFilter = value
            )
        }
    }
    fun setOrderBy(orderBy: OrderBy) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                orderBy = orderBy
            )
            getDailyPay(state.value.id,state.value.timeStamp)
        }
    }

    fun saveUpdatePay(payObj: DebtorPayment) {


        viewModelScope.launch {
            useCases.saveUpdatePayment(payObj)
        }
    }

    fun setEditPay(dailyPayment: DailyPayment) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                editPayment = dailyPayment
            )
        }
    }

    fun deletePay(payment: DebtorPayment) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                deletePayment = payment
            )
            useCases.deletePayment(payment)
        }
    }

    fun setIsDebtorExpended(value: Boolean) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isDebtorExpend=value
            )
        }
    }

    fun setHasFocus(value: Boolean) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                hasFocus = value
            )
        }
    }

    fun setIsAddLone(value: Boolean) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isAddLone=value

            )
        }
    }

    fun setLoneDebtor(dailyDebtor:DailyPayment) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                dailyLoneDebtor=dailyDebtor
            )
        }
    }

    fun saveLoan(loan: DebtorLoan) {
        viewModelScope.launch {
            useCases.saveUpdateLone(loan)
        }
    }
    fun setIsWarning(value: Boolean) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                isWaring=value,
            )
        }
    }
    fun setDpl(dpl:DebtorPayment) {
        viewModelScope.launch {
            _state.value = state.value.copy(
                dpl = dpl
            )
        }
    }

    suspend fun setAllPaid(dp: DebtorPayment?,context: Context) {

        println(dp.toString())

        loanJob?.cancel()
        loanJob= useCases.getAllLoneById(dp?.paymentHolder?:0).onEach {
           /* it.forEach {
                println(it.toString())
            }*/
            val toList = it.map {
                it.copy(status = "Paid")
            }.toList()

            if(toList.isNotEmpty()){
                useCases.saveAllLone(toList)
            }else{
                Toast.makeText(context,"No pending loan!!",Toast.LENGTH_SHORT)
            }

            _state.value=state.value.copy(
                isWaring = false
            )


        }.launchIn(viewModelScope)
    }

}



