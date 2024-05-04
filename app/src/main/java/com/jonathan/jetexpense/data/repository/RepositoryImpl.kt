package com.jonathan.jetexpense.data.repository

import com.jonathan.jetexpense.data.local.ExpenseDao
import com.jonathan.jetexpense.data.local.IncomeDao
import com.jonathan.jetexpense.data.local.models.Expense
import com.jonathan.jetexpense.data.local.models.Income
import com.jonathan.jetexpense.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class RepositoryImpl @Inject constructor(
    private val incomeDao: IncomeDao,
    private val expenseDao: ExpenseDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : Repository {
    override val income: Flow<List<Income>>
        get() = incomeDao.getAllIncome()
    override val expense: Flow<List<Expense>>
        get() = expenseDao.getAllExpense()

    override suspend fun insertIncome(income: Income) {
        return withContext(dispatcher) {
            incomeDao.insertIncome(income)
        }
    }

    override suspend fun insertExpense(expense: Expense) {
       return withContext(dispatcher) {
           expenseDao.insertExpense(expense)
       }
    }

    override fun getIncomeById(id: Int): Flow<Income> {
        return incomeDao.getIncomeById(id)
    }

    override fun getExpenseById(id: Int): Flow<Expense> {
        return expenseDao.getExpenseById(id)
    }

    override suspend fun updateIncome(income: Income) {
        return withContext(dispatcher) {
            incomeDao.updateIncome(income)
        }
    }

    override suspend fun updateExpense(expense: Expense) {
        return withContext(dispatcher) {
            expenseDao.updateExpense(expense)
        }
    }

    override suspend fun deleteIncome(id: Int): Int {
        return withContext(dispatcher) {
            incomeDao.deleteIncome(id)
        }
    }

    override suspend fun deleteExpense(id: Int): Int {
        return withContext(dispatcher) {
            expenseDao.deleteExpense(id)
        }
    }

}