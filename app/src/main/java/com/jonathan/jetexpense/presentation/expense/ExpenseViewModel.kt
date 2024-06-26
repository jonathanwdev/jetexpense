package com.jonathan.jetexpense.presentation.expense

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonathan.jetexpense.data.local.models.Expense
import com.jonathan.jetexpense.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {
    var expenseState by mutableStateOf(ExpenseState())
        private set

    init {
        getAllExpenses()
    }

    private fun getAllExpenses() {
        viewModelScope.launch {
            repository.expense.collectLatest {
                expenseState = expenseState.copy(expenses = it)
            }
        }
    }

    fun deleteExpense(id: Int) = viewModelScope.launch {
        repository.deleteExpense(id)
    }


}


data class ExpenseState(
    val expenses: List<Expense> = emptyList()
)