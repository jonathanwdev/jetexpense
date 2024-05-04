package com.jonathan.jetexpense.presentation.income

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonathan.jetexpense.data.local.models.Income
import com.jonathan.jetexpense.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    var incomeState by mutableStateOf(IncomeState())
        private set

    init {
        getAllIncome()
    }

    private fun getAllIncome() {
        viewModelScope.launch {
            repository.income.collectLatest {
                incomeState = incomeState.copy(incomes = it)
            }
        }
    }

    fun deleteIncome(id: Int) = viewModelScope.launch {
        repository.deleteIncome(id)
    }


}


data class IncomeState(
    val incomes: List<Income> = emptyList()
)