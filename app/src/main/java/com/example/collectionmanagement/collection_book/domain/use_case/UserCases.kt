package com.example.collectionmanagement.collection_book.domain.use_case

import com.example.collectionmanagement.collection_book.domain.model.DailyPayment
import com.example.collectionmanagement.collection_book.domain.model.DebtorPayment
import com.example.dailymoneyrecord.recorde_Book.domain.use_case.cases.DeletePayment
import com.example.dailymoneyrecord.recorde_Book.domain.use_case.cases.GetAllPayments
import com.example.dailymoneyrecord.recorde_Book.domain.use_case.cases.GetDailyPayments
import javax.inject.Inject

class UserCases @Inject constructor(
    val getAllDebtor: GetAllDebtor,
    val saveUpdateDebtor: SaveUpdateDebtor,
    val deleteDebtor: DeleteDebtor,
    val getAllLone: GetAllLone,
    val saveUpdateLone: SaveLone,
    val deleteLone: DeleteLone,
    val saveUpdatePayment: SaveUpdatePayment,
    val deletePayment: DeletePayment,
    val paymentsByIdAndTime: GetPaymentsByIdAndTime,
    val allPayments: GetAllPayments,
    val dailyPayment: GetDailyPayments,

)