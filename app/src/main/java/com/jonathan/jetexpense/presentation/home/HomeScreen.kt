package com.jonathan.jetexpense.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jonathan.jetexpense.presentation.components.AccountCard
import com.jonathan.jetexpense.presentation.components.ExpenseCard
import com.jonathan.jetexpense.presentation.components.IncomeCard
import com.jonathan.jetexpense.ui.theme.JetExpenseTheme
import com.jonathan.jetexpense.util.expenseList
import com.jonathan.jetexpense.util.formatAmount
import com.jonathan.jetexpense.util.incomeList

@Composable
fun HomeScreen(
    state: HomeUiState,
    modifier: Modifier,
    onIncomeClick: (Int) -> Unit,
    onClickSeeAllIncome: () -> Unit,
    onExpenseClick: (Int) -> Unit,
    onClickSeeAllExpense: () -> Unit,
    onInsertExpense: () -> Unit,
    onInsertIncome: () -> Unit,
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Column {
                Row {
                    val balance = state.totalIncome - state.totalExpense

                    Text(text = "Your total balance: ")
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = "$${formatAmount(balance)}",
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AccountCard(
                        cardTitle = "TOTAL INCOME",
                        amount = "+$${formatAmount(state.totalIncome)}",
                        cardIcon = Icons.Default.ArrowDownward,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp, top = 8.dp, bottom = 8.dp)
                    )
                    AccountCard(
                        cardTitle = "TOTAL EXPENSE",
                        amount = "-$${formatAmount(state.totalExpense)}",
                        cardIcon = Icons.Default.ArrowUpward,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 4.dp, top = 8.dp, bottom = 8.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.size(12.dp))
        }
        item {
            IncomeCard(
                account = state,
                onClickSeeAll = onClickSeeAllIncome,
                onIncomeClick = onIncomeClick
            )
            Spacer(modifier = Modifier.size(12.dp))

        }
        item {
            ExpenseCard(
                account = state,
                onClickSeeAll = onClickSeeAllExpense,
                onExpenseClick = onExpenseClick
            )
            Spacer(modifier = Modifier.size(12.dp))
        }
        item {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                ElevatedButton(onClick = onInsertIncome) {
                    Text(text = "Insert Income")
                }
                ElevatedButton(onClick = onInsertExpense) {
                    Text(text = "Insert Expense")
                }
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomePreview() {
    JetExpenseTheme {
        HomeScreen(
            state = HomeUiState(income = incomeList, expense = expenseList),
            modifier = Modifier,
            onIncomeClick = {},
            onClickSeeAllIncome = {},
            onExpenseClick = {},
            onClickSeeAllExpense = {},
            onInsertExpense = {},
            onInsertIncome = {}
        )

    }
}

@Preview(showSystemUi = true)
@Composable
fun HomePreviewDark() {
    JetExpenseTheme(
        darkTheme = true
    ) {
        HomeScreen(
            state = HomeUiState(income = incomeList, expense = expenseList),
            modifier = Modifier,
            onIncomeClick = {},
            onClickSeeAllIncome = {},
            onExpenseClick = {},
            onClickSeeAllExpense = {},
            onInsertExpense = {},
            onInsertIncome = {}
        )

    }
}