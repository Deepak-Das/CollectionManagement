package com.example.collectionmanagement.collection_book.prentation.Daily

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.collectionmanagement.collection_book.prentation.Loan.LoneState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DailyViewModel @Inject constructor() :ViewModel() {

    private val _state = mutableStateOf<DailyState>(DailyState())
    val state: State<DailyState> = _state
}