package com.example.collectionmanagement.collection_book.domain.use_case

import javax.inject.Inject

class UserCases @Inject constructor(
    val getAllDebtor: GetAllDebtor,
    val saveUpdateDebtor: SaveUpdateDebtor,
    val deleteDebtor: DeleteDebtor,
    val getAllLone: GetAllLone,
)