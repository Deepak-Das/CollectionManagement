package com.example.collectionmanagement.collection_book.prentation.Loan

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collectionmanagement.collection_book.domain.model.Debtor
import com.example.collectionmanagement.collection_book.domain.model.DebtorLoan
import com.example.collectionmanagement.collection_book.domain.model.LoanWithName
import com.example.collectionmanagement.collection_book.domain.use_case.UserCases
import com.example.dailymoneyrecord.recorde_Book.domain.util.OrderType
import com.example.dailymoneyrecord.recorde_Book.domain.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoneViewModel @Inject constructor(
    private val userCases: UserCases

) : ViewModel() {
    private val _state = mutableStateOf<LoneState>(LoneState())
    val state: State<LoneState> = _state;


    init {
        viewModelScope.launch {
            getAllDebtor()
            getLone()
        }
    }

    private suspend fun getAllDebtor(){
        viewModelScope.launch {
            userCases.getAllDebtor().collectLatest {
                _state.value = state.value.copy(
                    debtorList = it
                )
            }
        }

    }

    private suspend fun getLone(status: Status =Status.Running(orderType = OrderType.Descending)) {

        viewModelScope.launch {
            userCases.getAllLone(status =status ).collectLatest {
                _state.value = state.value.copy(
                    loneWithNameList = it
                )
            }
        }
    }

    fun getPaidLone() {
        //Todo:
    }

    fun getAllLone() {
        //Todo:
    }

    fun getLoneByDebtor(debtor:Debtor){
        //Todo:
    }

    fun saveUpdateLone(lone:DebtorLoan){
        //todo:
    }

    fun deleteLone(lone:DebtorLoan){
        //todo:
    }

    fun setSearch(query: String) {

        viewModelScope.launch {
            _state.value = state.value.copy(
                search = query
            )

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


}