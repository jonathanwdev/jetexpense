package com.jonathan.jetexpense.presentation.transaction.Mocks

import com.jonathan.jetexpense.presentation.navigation.JetExpenseDestination
import com.jonathan.jetexpense.presentation.transaction.TransactionCallback
import com.jonathan.jetexpense.util.Category

class MockTransactionCallback(override val isFieldNotEmpty: Boolean = false) : TransactionCallback {
    override fun onTitleChange(newValue: String) {
        TODO("Not yet implemented")
    }

    override fun onAmountChange(newValue: String) {
        TODO("Not yet implemented")
    }

    override fun onDescriptionChange(newValue: String) {
        TODO("Not yet implemented")
    }

    override fun onTransactionTypeChange(newValue: String) {
        TODO("Not yet implemented")
    }

    override fun onDateChange(newValue: Long?) {
        TODO("Not yet implemented")
    }

    override fun onScreenTypeChange(newValue: JetExpenseDestination) {
        TODO("Not yet implemented")
    }

    override fun onOpenDialog(newValue: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onCategoryChange(category: Category) {
        TODO("Not yet implemented")
    }

    override fun addIncome() {
        TODO("Not yet implemented")
    }

    override fun addExpense() {
        TODO("Not yet implemented")
    }

    override fun getIncome(id: Int) {
        TODO("Not yet implemented")
    }

    override fun getExpense(id: Int) {
        TODO("Not yet implemented")
    }

    override fun updateIncome() {
        TODO("Not yet implemented")
    }

    override fun updateExpense() {
        TODO("Not yet implemented")
    }
}