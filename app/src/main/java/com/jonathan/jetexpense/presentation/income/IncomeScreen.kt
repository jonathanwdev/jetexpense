package com.jonathan.jetexpense.presentation.income

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jonathan.jetexpense.data.local.models.Income
import com.jonathan.jetexpense.presentation.components.IncomeRow
import com.jonathan.jetexpense.presentation.components.TransactionStatement
import com.jonathan.jetexpense.util.Util
import com.jonathan.jetexpense.util.getColor
import com.jonathan.jetexpense.util.incomeList
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun IncomeScreen(
    modifier: Modifier = Modifier,
    incomes: List<Income>,
    onIncomeItemClick: (id: Int) -> Unit,
    onIncomeItemDelete: (Int) -> Unit
) {
    TransactionStatement(
        items = incomes,
        colors = {
            getColor(it.incomeAmount.toFloat(), Util.incomeColor)
        },
        amounts = {
            it.incomeAmount.toFloat()
        },
        amountsTotal = incomes.sumOf { it.incomeAmount }.toFloat(),
        circleLabel = "Receive",
        onItemSwiped = {
            onIncomeItemDelete.invoke(it.id)
        },
        key = {
            it.id
        },
        modifier = modifier
    ) {
        IncomeRow(
            name = it.title,
            description = "Receive ${formatDetailDate(it.date)}",
            amount = it.incomeAmount.toFloat(),
            color = getColor(it.incomeAmount.toFloat(), Util.incomeColor),
            modifier = Modifier.clickable {
                onIncomeItemClick.invoke(it.id)
            }
        )
    }
}

fun formatDetailDate(date: Date): String =
    SimpleDateFormat("MM dd", Locale.getDefault()).format(date)


@Preview(showSystemUi = true)
@Composable
private fun PrevIncomeScreen() {
    IncomeScreen(
        incomes = incomeList.mapIndexed { index, income ->
            income.copy(id = index)
        },
        onIncomeItemClick = {},
        onIncomeItemDelete = {},

        )
}