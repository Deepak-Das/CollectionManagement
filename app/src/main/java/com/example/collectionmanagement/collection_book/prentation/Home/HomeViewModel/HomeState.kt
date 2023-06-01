package com.example.collectionmanagement.collection_book.prentation.Home.HomeViewModel

import androidx.compose.ui.graphics.Color
import com.example.collectionmanagement.collection_book.domain.utils.Ams
import com.example.collectionmanagement.collection_book.prentation.navigation.Router

import java.time.LocalDate

data class HomeState(
    val progressStatus: Boolean = false,
    val timeStamp: Long = Ams.dateToTimeStamp(Ams.localDateToDate(LocalDate.now())),
    val date: String = Ams.localDateToDate(LocalDate.now())
//    val buttonList: List<ButtonObj>
)

data class ButtonObj(
    val imgR: Int,
    val text: String,
    val color: Color,
    val f: () -> Unit = {},
    val route: Router? = null
)
