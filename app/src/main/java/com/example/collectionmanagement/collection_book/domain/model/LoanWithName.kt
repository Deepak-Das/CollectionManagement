package com.example.collectionmanagement.collection_book.domain.model


data class LoanWithName(
    val loanId:Int,
    val debtorId:Int,
    val DebtorName:String,
    val LoneAmount:Int,
    val timeStamp: Long,
    val color:Int,
    val status:String//Runing
)

//add enum for status
