package com.example.collectionmanagement.di

import android.app.Application
import androidx.room.Room
import com.example.collectionmanagement.collection_book.data.data_source.AppDatabase
import com.example.collectionmanagement.collection_book.data.repositoryImp.DebtorLoneImp
import com.example.collectionmanagement.collection_book.data.repositoryImp.DebtorRepoImp
import com.example.collectionmanagement.collection_book.data.repositoryImp.PaymentRepositoryImp
import com.example.collectionmanagement.collection_book.domain.repository.DebtorLoneRepository
import com.example.collectionmanagement.collection_book.domain.repository.DebtorRepository
import com.example.collectionmanagement.collection_book.domain.repository.PaymentRepository
import com.example.collectionmanagement.collection_book.domain.use_case.*
import com.example.dailymoneyrecord.recorde_Book.domain.use_case.cases.DeletePayment
import com.example.dailymoneyrecord.recorde_Book.domain.use_case.cases.GetAllPayments
import com.example.dailymoneyrecord.recorde_Book.domain.use_case.cases.GetDailyPayments
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
    fun getDebtorLoneRepo(database: AppDatabase): DebtorLoneRepository {
        return DebtorLoneImp(database.dao);
    }

    @Provides
    @Singleton
    fun getPaymentRepo(database: AppDatabase): PaymentRepository {
        return PaymentRepositoryImp(database.dao);
    }

    @Provides
    @Singleton
    fun getUseCase(
        debtorRepository: DebtorRepository,
        debtorLoneRepository: DebtorLoneRepository,
        paymentRepository: PaymentRepository
    ): UserCases {
        return UserCases(
            getAllDebtor = GetAllDebtor(debtorRepository),
            saveUpdateDebtor = SaveUpdateDebtor(debtorRepository ),
            deleteDebtor = DeleteDebtor(debtorRepository ),
            getAllLone= GetAllLone(debtorLoneRepository),
            saveUpdateLone = SaveLone(debtorLoneRepository),
            deleteLone = DeleteLone(debtorLoneRepository),
            saveUpdatePayment = SaveUpdatePayment(paymentRepository),
            deletePayment= DeletePayment(paymentRepository),
            dailyPayment = GetDailyPayments(paymentRepository),
            allPayments = GetAllPayments(paymentRepository),
            paymentsByIdAndTime = GetPaymentsByIdAndTime(paymentRepository)

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