package com.example.collectionmanagement.collection_book.prentation.Loan

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collectionmanagement.collection_book.domain.model.Debtor
import com.example.collectionmanagement.collection_book.domain.model.DebtorLoan
import com.example.collectionmanagement.collection_book.domain.model.LoanWithName
import com.example.collectionmanagement.collection_book.domain.use_case.UserCases
import com.example.dailymoneyrecord.recorde_Book.domain.util.OrderBy
import com.example.dailymoneyrecord.recorde_Book.domain.util.OrderType
import com.example.dailymoneyrecord.recorde_Book.domain.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoneViewModel @Inject constructor(
    private val userCases: UserCases

) : ViewModel() {
    private val _state = mutableStateOf<LoneState>(LoneState())
    val state: State<LoneState> = _state;

    var loneJob:Job?=null;
    var debtorJob:Job?=null;

    init {

        getAllDebtor()
        getLone()

    }

/*
    fun onEvent(event: Event) {
        when (event) {
            is Event.StatusFilter -> {

                _loneState.value = loneState.value.copy(
                    status = event.Status
                )
                Log.d("status", "onEvent: ${loneState.value.status}")

                if (loneState.value.flagChange) getLones(event.Status) else getLoanByName(
                    loneState.value.id ?: -1,event.Status
                )
            }
            is Event.Loan -> {
                viewModelScope.launch {
                    useCase.insertLoan(event.debtorLoan)
                }

            }
            is Event.LoneByName -> {
                _loneState.value = loneState.value.copy(
                    id = event.ID
                )
                getLoanByName(event.ID ?: -1,loneState.value.status)
            }
            is Event.DateChange -> {
                _loneState.value = loneState.value.copy(
                    dateStamp = event.date
                )
            }
            is Event.AddLoan -> {
                viewModelScope.launch {
                    useCase.insertLoan(event.DebtorLoan)
                }
            }
            is Event.LoanDelete -> {
                viewModelScope.launch {
                    useCase.deleteLoan(event.DebtorLoan)
                }
            }
        }

    }
*/


    private fun getAllDebtor(){
      debtorJob=userCases.getAllDebtor(orderBy = OrderBy.Name(OrderType.Ascending)).onEach {
          _state.value = state.value.copy(
              debtorList = it
          )
      }.launchIn(viewModelScope)
    }

    private fun getLone() {

        var filter= emptyList<LoanWithName>()
        loneJob?.cancel()
        loneJob=userCases.getAllLone(state.value.status).onEach {loneList->
            if(state.value.search.isNotEmpty()) {
                 filter = loneList.filter {
                    it.DebtorName.contains(state.value.search, ignoreCase = true)
                }

                val groupBy = filter.groupBy { it.timeStamp }
                _state.value=state.value.copy(
                    filtertloneList = filter,
                    loneWithNameList = loneList,
                    loneGroup = groupBy
                )

            }else{
                val groupBy = loneList.groupBy { it.timeStamp }
                _state.value=state.value.copy(
                    filtertloneList = loneList,
                    loneWithNameList = loneList,
                    loneGroup = groupBy
                )

            }

        }.launchIn(viewModelScope)
    }


    fun setDeleteLoneData(debtorLoan: DebtorLoan) {
        viewModelScope.launch {
            _state.value=state.value.copy(
                deleteLoneData = debtorLoan,
                isWarning = true
            )
        }
        //Todo:
    }

    fun setIsWarning(status: Boolean){
        viewModelScope.launch {
            _state.value = state.value.copy(
                isWarning = status
            )
        }
    }

    fun getLoneByDebtor(debtor:Debtor){
        //Todo:
    }

    fun saveUpdateLone(lone:DebtorLoan){
        viewModelScope.launch {
            userCases.saveUpdateLone(debtorLone = lone)
        }
    }

    fun deleteLone(lone:DebtorLoan?){
        viewModelScope.launch {
            lone?.let { userCases.deleteLone(it)
                _state.value=state.value.copy(
                    deleteLoneData = lone
                )
            }

        }
    }



    fun setSearch(query: String) {


        viewModelScope.launch {
            if(query.isNotEmpty()){
                val filter = state.value.loneWithNameList.filter {
                    it.DebtorName.contains(query, ignoreCase = true)
                }
                _state.value = state.value.copy(
                    search = query,
                    filtertloneList = filter
                )
            }else{
                _state.value = state.value.copy(
                    search = query,
                    filtertloneList = state.value.loneWithNameList
                )
            }


        }

    }

    fun setOpendialog(status:Boolean) {
        viewModelScope.launch {
            _state.value=state.value.copy(
                openDialog = status
            );
        }
    }

    fun setEditLoneWithName(it: LoanWithName) {
        viewModelScope.launch {
            _state.value=state.value.copy(
                editLoanWithName = it,
            )
        }
    }

    fun setStatus(status: Status){
        viewModelScope.launch {

            _state.value = state.value.copy(
                status = status
            )

            getLone()
        }

    }


}