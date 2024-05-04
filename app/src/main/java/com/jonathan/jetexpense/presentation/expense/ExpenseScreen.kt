package com.jonathan.jetexpense.presentation.expense

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jonathan.jetexpense.data.local.models.Expense
import com.jonathan.jetexpense.presentation.components.ExpenseRow
import com.jonathan.jetexpense.presentation.components.TransactionStatement
import com.jonathan.jetexpense.util.Util
import com.jonathan.jetexpense.util.expenseList
import com.jonathan.jetexpense.util.getColor
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ExpenseScreen(
    modifier: Modifier = Modifier,
    expenses: List<Expense>,
    onExpenseItemClick: (id: Int) -> Unit,
    onExpenseItemDelete: (Int) -> Unit
) {
    TransactionStatement(
        items = expenses,
        colors = {
            getColor(it.expenseAmount.toFloat(), Util.expenseColor)
        },
        amounts = {
            it.expenseAmount.toFloat()
        },
        amountsTotal = expenses.sumOf { it.expenseAmount }.toFloat(),
        circleLabel = "Pay",
        onItemSwiped = {
            onExpenseItemDelete.invoke(it.id)
        },
        key = {
            it.id
        },
        modifier = modifier
    ) {
        ExpenseRow(
            name = it.title,
            description = "Receive ${formatDetailDate(it.date)}",
            amount = it.expenseAmount.toFloat(),
            color = getColor(it.expenseAmount.toFloat(), Util.expenseColor),
            modifier = Modifier.clickable {
                onExpenseItemClick.invoke(it.id)
            }
        )
    }
}

fun formatDetailDate(date: Date): String =
    SimpleDateFormat("MM dd", Locale.getDefault()).format(date)


@Preview(showSystemUi = true)
@Composable
private fun PrevExpenseScreen() {
    ExpenseScreen(
        expenses = expenseList.mapIndexed { index, income ->
            income.copy(id = index)
        },
        onExpenseItemClick = {},
        onExpenseItemDelete = {}
    )
}