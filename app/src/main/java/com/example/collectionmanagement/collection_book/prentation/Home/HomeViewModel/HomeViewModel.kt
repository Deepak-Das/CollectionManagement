package com.example.collectionmanagement.collection_book.prentation.Home.HomeViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor()
    :ViewModel() {

    private val _state= mutableStateOf(HomeState(1));
    val state: State<HomeState> = _state;

    fun increment() {
        viewModelScope.launch {
            _state.value=state.value.copy(
                count = state.value.count+1
            )
            return@launch
        }
    }
}