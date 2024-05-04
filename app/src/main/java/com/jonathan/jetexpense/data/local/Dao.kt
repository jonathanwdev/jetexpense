package com.jonathan.jetexpense.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jonathan.jetexpense.data.local.models.Expense
import com.jonathan.jetexpense.data.local.models.Income
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * from expense_table WHERE expense_id = :id")
    fun getExpenseById(id: Int): Flow<Expense>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)

    @Query("DELETE FROM expense_table")
    suspend fun deleteAllExpense()


    @Query("DELETE FROM EXPENSE_TABLE WHERE expense_id = :id")
    suspend fun deleteExpense(id: Int): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateExpense(expense: Expense)

    @Query("SELECT * from expense_table")
    fun getAllExpense(): Flow<List<Expense>>
}

@Dao
interface IncomeDao {
    @Query("SELECT * from income_table WHERE income_id = :id")
    fun getIncomeById(id: Int): Flow<Income>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncome(income: Income)

    @Query("DELETE FROM income_table")
    suspend fun deleteAllIncome()


    @Query("DELETE FROM income_table WHERE income_id = :id")
    suspend fun deleteIncome(id: Int): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateIncome(income: Income)

    @Query("SELECT * from income_table")
    fun getAllIncome(): Flow<List<Income>>
}