package com.jonathan.jetexpense.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonathan.jetexpense.data.local.models.Expense
import com.jonathan.jetexpense.data.local.models.Income
import com.jonathan.jetexpense.data.repository.Repository
import com.jonathan.jetexpense.util.expenseList
import com.jonathan.jetexpense.util.incomeList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    val income = repository.income
    val expense = repository.expense

    var homeUiState by mutableStateOf(HomeUiState())
        private set

    init {
        viewModelScope.launch {
            combine(income, expense) { incomeList: List<Income>, expenseList: List<Expense> ->
                val sumExpense = expenseList.sumOf { it.expenseAmount }.toFloat()
                val sumIncome = incomeList.sumOf { it.incomeAmount }.toFloat()

                homeUiState.copy(
                    income = incomeList,
                    expense = expenseList,
                    totalExpense = sumExpense,
                    totalIncome = sumIncome
                )
            }.collectLatest {
                homeUiState = it
            }
        }
    }

    fun insertIncome() = viewModelScope.launch {
        repository.insertIncome(incomeList.random())
    }


    fun insertExpense() = viewModelScope.launch {
        repository.insertExpense(expenseList.random())
    }
}



data class HomeUiState(
    val income: List<Income> = emptyList(),
    val expense: List<Expense> = emptyList(),
    val totalIncome: Float = 0f,
    val totalExpense: Float = 0f,

)