package com.jonathan.jetexpense.presentation.transaction

import com.jonathan.jetexpense.presentation.navigation.JetExpenseDestination

interface TransactionCallback {
    val isFieldNotEmpty: Boolean

    fun onTitleChange(newValue: String)
    fun onAmountChange(newValue: String)
    fun onDescriptionChange(newValue: String)
    fun onTransactionTypeChange(newValue: String)
    fun onDateChange(newValue: Long?)
    fun onScreenTypeChange(newValue: JetExpenseDestination)
    fun onOpenDialog(newValue: Boolean)
    fun onCategoryChange(category: com.jonathan.jetexpense.util.Category)
    fun addIncome()
    fun addExpense()
    fun getIncome(id: Int)
    fun getExpense(id: Int)
    fun updateIncome()
    fun updateExpense()


}