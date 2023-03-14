package com.example.collectionmanagement.collection_book.prentation.Debtor

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.collectionmanagement.collection_book.domain.model.Debtor
import com.example.collectionmanagement.collection_book.domain.use_case.UserCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DebtorViewModel @Inject constructor(
    private val userCases: UserCases
) : ViewModel() {
    private val _state = mutableStateOf<DebtorState>(DebtorState())
    val state: State<DebtorState> = _state;

    init {
        viewModelScope.launch {
            userCases.getAllDebtor().collectLatest {
                _state.value = state.value.copy(
                    list = it
                )
            }
        }
    }

    fun setAddStatus(status: Boolean) {
        val TAG = "DebtorModel"
        Log.d(TAG, "setAddStatus: ${status}")
        viewModelScope.launch {
            _state.value = state.value.copy(
                addStatus = status
            )

        }

    }
    fun setUpdateStatus(status: Boolean,debtor: Debtor) {
        val TAG = "DebtorModel"
        Log.d(TAG, "update: ${status}")
        viewModelScope.launch {
            _state.value = state.value.copy(
                updateStatus = status,
                updateDebtor=debtor
            )

        }

    }
    fun setSearch(query: String) {

        viewModelScope.launch {
            _state.value = state.value.copy(
                search = query
            )

        }

    }

    fun saveUpdate(id: Int? = null, name: String, addr: String, timeStamp: Long) {
        viewModelScope.launch {
            userCases.saveUpdateDebtor(
                Debtor(
                    debtorId = id,
                    name = name,
                    address = addr,
                    timestamp = timeStamp,
                    color = Debtor.colorCode().toArgb()
                )
            )
        }
    }
}