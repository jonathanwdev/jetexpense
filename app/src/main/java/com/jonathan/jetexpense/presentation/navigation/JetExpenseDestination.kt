package com.jonathan.jetexpense.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.jonathan.jetexpense.R

sealed class JetExpenseDestination {
    abstract val iconResId: Int
    abstract val routePath: String
    abstract val pageTitle: String
}


object HomeDestination: JetExpenseDestination() {
    override val iconResId: Int
        get() = R.drawable.ic_home
    override val routePath: String
        get() = "home"
    override val pageTitle: String
        get() = "Home"

}

object IncomeDestination: JetExpenseDestination() {
    override val iconResId: Int
        get() = R.drawable.ic_income_dollar
    override val routePath: String
        get() = "income"
    override val pageTitle: String
        get() = "Income"

}

object ExpenseDestination: JetExpenseDestination() {
    override val iconResId: Int
        get() = R.drawable.ic_expense_dollar
    override val routePath: String
        get() = "expense"
    override val pageTitle: String
        get() = "Expense"

}

object TransactionDestination: JetExpenseDestination() {
    override val iconResId: Int
        get() = R.drawable.add_entry
    override val routePath: String
        get() = "transaction"
    override val pageTitle: String
        get() = "Add Transaction"

    const val transactionTypeArg = "Type"
    const val idTypeArg = "id"
    val arguments = listOf(
        navArgument(transactionTypeArg) {
            type = NavType.StringType
            defaultValue = ""
        },
        navArgument(idTypeArg) {
            type = NavType.IntType
            defaultValue = -1
        }
    )
    val routeWithArgs = "$routePath?$transactionTypeArg={$transactionTypeArg}&$idTypeArg={$idTypeArg}"


}