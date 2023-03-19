package com.example.collectionmanagement.di

import android.app.Application
import androidx.room.Room
import com.example.collectionmanagement.collection_book.data.data_source.AppDatabase
import com.example.collectionmanagement.collection_book.data.repositoryImp.DebtorRepoImp
import com.example.collectionmanagement.collection_book.domain.repository.DebtorRepository
import com.example.collectionmanagement.collection_book.domain.use_case.DeleteDebtor
import com.example.collectionmanagement.collection_book.domain.use_case.GetAllDebtor
import com.example.collectionmanagement.collection_book.domain.use_case.SaveUpdateDebtor
import com.example.collectionmanagement.collection_book.domain.use_case.UserCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun getDebtorRepo(database: AppDatabase): DebtorRepository {
        return DebtorRepoImp(database.dao);
    }

    @Provides
    @Singleton
    fun getUseCase(
        debtorRepository: DebtorRepository
    ): UserCases {
        return UserCases(
            getAllDebtor = GetAllDebtor(debtorRepository),
            saveUpdateDebtor = SaveUpdateDebtor(debtorRepository ),
            deletedebtor = DeleteDebtor(debtorRepository ),
        )
    }

    @Provides
    @Singleton
    fun getDb(applicationContext: Application): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

}