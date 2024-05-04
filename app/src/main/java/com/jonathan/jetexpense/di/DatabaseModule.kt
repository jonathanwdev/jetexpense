package com.jonathan.jetexpense.di

import android.content.Context
import androidx.room.Room
import com.jonathan.jetexpense.data.local.JetExpDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideExpenseDatabase(
        @ApplicationContext context: Context
    ): JetExpDatabase {
        return Room.databaseBuilder(
            context,
            JetExpDatabase::class.java,
            name = "transaction_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideExpenseDao(database: JetExpDatabase) = database.expanseDao

    @Provides
    @Singleton
    fun provideIncomeDao(database: JetExpDatabase) = database.incomeDao
}