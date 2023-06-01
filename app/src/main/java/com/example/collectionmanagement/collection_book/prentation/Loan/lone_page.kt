package com.example.collectionmanagement.collection_book.prentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.collectionmanagement.collection_book.domain.utils.Ams
import com.example.collectionmanagement.collection_book.prentation.Loan.LoneViewModel


@Preview
@Composable
fun LoanPage(
    viewModel: LoneViewModel = hiltViewModel()
) {

    val state=viewModel.state.value


    Scaffold {
        Column(
            Modifier
                .padding(it)
                .padding(10.dp)
        ) {
            Ams.CustomSearchBar(
                getDebtor = {

                },
                list = state.debtorList,
                query = state.search,
                setQuery = viewModel::setSearch,
            )

        }
    }


}









