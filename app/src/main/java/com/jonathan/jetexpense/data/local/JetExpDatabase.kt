package com.jonathan.jetexpense.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jonathan.jetexpense.data.local.converters.DateConverter
import com.jonathan.jetexpense.data.local.models.Expense
import com.jonathan.jetexpense.data.local.models.Income

@TypeConverters(value = [DateConverter::class])
@Database(entities = [Income::class, Expense::class], exportSchema = false, version = 1)
abstract class JetExpDatabase: RoomDatabase() {
    abstract val expanseDao: ExpenseDao
    abstract val incomeDao: IncomeDao


}