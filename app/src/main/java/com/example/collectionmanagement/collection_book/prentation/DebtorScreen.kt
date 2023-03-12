package com.example.collectionmanagement.collection_book.prentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DebtorScreen() {
    Scaffold {
        Column(Modifier.padding(it)) {
            CenterAlignedTopAppBar(title = { Text(text = "Debtor", color = Color.White)}, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary))
        }
    }
}