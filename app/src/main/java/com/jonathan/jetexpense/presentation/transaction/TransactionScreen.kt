package com.jonathan.jetexpense.presentation.transaction

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jonathan.jetexpense.R
import com.jonathan.jetexpense.presentation.navigation.ExpenseDestination
import com.jonathan.jetexpense.presentation.navigation.IncomeDestination
import com.jonathan.jetexpense.presentation.navigation.JetExpenseDestination
import com.jonathan.jetexpense.presentation.transaction.Mocks.MockTransactionCallback
import com.jonathan.jetexpense.util.Category
import com.jonathan.jetexpense.util.formatDate
import java.util.Date

@Composable
fun TransactionScreen(
    modifier: Modifier = Modifier,
    transactionId: Int,
    transactionType: String,
    assistedFactory: TransactionAssistedFactory,
    navigateUp: () -> Unit
) {
    val viewModel = viewModel(
        modelClass = TransactionViewModel::class.java,
        factory = TransactionViewModelFactory(
            id = transactionId,
            transactionType = transactionType,
            assistedFactory = assistedFactory
        )
    )

    TransactionScreen(
        modifier = modifier,
        state = viewModel.state,
        transactionCallback = viewModel,
        navigateUp = navigateUp
    )
}
@Composable
private fun TransactionScreen(
    modifier: Modifier = Modifier,
    state: TransactionState,
    transactionCallback: TransactionCallback,
    navigateUp: () -> Unit
) {
    val scrollState = rememberScrollState()
    val transactionScreenList = listOf(IncomeDestination, ExpenseDestination)
    val isExpenseTransaction = state.transactionScreen == ExpenseDestination

    val icon = when {
        isExpenseTransaction -> R.drawable.ic_expense_dollar
        else -> R.drawable.ic_income_dollar
    }

    Column(
        modifier = modifier.scrollable(
            state = scrollState,
            orientation = Orientation.Vertical
        )
    ) {
        TransactionTitle(
            icon = icon,
            state = state,
            transactionCallback = transactionCallback,
            transactionScreenList = transactionScreenList
        )
        Spacer(modifier = Modifier.size(12.dp))
        TransactionDetails(
            state = state,
            isExpenseTransaction = isExpenseTransaction,
            transactionCallback = transactionCallback
        )
        Spacer(modifier = Modifier.size(12.dp))
        TransactionButton(
            state =state,
            transactionCallback = transactionCallback,
            navigateUp = navigateUp
        )
    }
}

@Composable
fun TransactionButton(
    state: TransactionState,
    transactionCallback: TransactionCallback,
    navigateUp: () -> Unit
) {
    val buttonTittle = if (state.isUpdatingTransaction) "Update Transaction" else "Add Transaction"

    fun executeAction() {
        when (state.isUpdatingTransaction) {
            true -> {
                if (state.transactionScreen == IncomeDestination) {
                    transactionCallback.updateIncome()
                } else {
                    transactionCallback.updateExpense()
                }
            }

            false -> {
                if (state.transactionScreen == IncomeDestination) {
                    transactionCallback.addIncome()
                } else {
                    transactionCallback.addExpense()
                }
            }
        }
        navigateUp.invoke()
    }

    Button(
        onClick = { executeAction() },
        modifier = Modifier.fillMaxWidth(),
        enabled = transactionCallback.isFieldNotEmpty
    ) {
        Text(text = buttonTittle)
    }
}

@Composable
private fun TransactionTitle(
    icon: Int,
    state: TransactionState,
    transactionCallback: TransactionCallback,
    transactionScreenList: List<JetExpenseDestination>
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = icon), contentDescription = null)
        Spacer(modifier = Modifier.size(6.dp))
        Text(text = state.transactionScreen.pageTitle)
        Spacer(modifier = Modifier.size(6.dp))
        IconButton(onClick = { transactionCallback.onOpenDialog(!state.openDialog) }) {
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
        }
        if (!state.openDialog) {
            Popup(
                onDismissRequest = {
                    transactionCallback.onOpenDialog(false)
                }
            ) {
                Surface(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column {
                        transactionScreenList.forEach {
                            Text(
                                text = it.pageTitle,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .clickable {
                                        transactionCallback.onScreenTypeChange(it)
                                        transactionCallback.onOpenDialog(true)
                                    })
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetails(
    state: TransactionState,
    isExpenseTransaction: Boolean,
    transactionCallback: TransactionCallback
) {
    Column {
        TransactionTextField(
            value = state.title,
            onValueChange = transactionCallback::onTitleChange,
            labelText = "Transaction tittle"
        )
        Spacer(modifier = Modifier.size(12.dp))
        TransactionTextField(
            value = state.amount,
            onValueChange = transactionCallback::onAmountChange,
            labelText = "Amount",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.size(4.dp))
        JetExpenseDate(
            date = state.date,
            onDateChange = transactionCallback::onDateChange
        )
        TransactionTextField(
            value = state.description,
            onValueChange = transactionCallback::onDescriptionChange,
            labelText = "Transaction Description"
        )
        Spacer(modifier = Modifier.size(12.dp))
        AnimatedVisibility(
            visible = isExpenseTransaction
        ) {
            LazyRow {
                items(Category.values()) { category: Category ->
                    InputChip(
                        selected = category == state.category,
                        onClick = {
                            transactionCallback.onCategoryChange(category)
                        },
                        label = {
                            Text(text = category.title)
                        },
                        modifier = Modifier.padding(8.dp),
                        trailingIcon = {
                            Icon(
                                painter = painterResource(id = category.iconRes),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JetExpenseDate(
    date: Date,
    onDateChange: (Long?) -> Unit

) {
    val datePickerState = rememberDatePickerState()
    var openDateDialog by remember {
        mutableStateOf(false)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.DateRange, contentDescription = null)
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = formatDate(date))
        Spacer(modifier = Modifier.size(4.dp))
        if (openDateDialog) {
            DatePickerDialog(
                onDismissRequest = { openDateDialog = false },
                confirmButton = {
                    Button(onClick = {
                        onDateChange.invoke(datePickerState.selectedDateMillis)
                    }) {
                        Text(text = "Submit")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        openDateDialog = false
                    }) {
                        Text(text = "Dismiss")
                    }
                },
                content = {
                    DatePicker(state = datePickerState)
                }
            )
        }
        IconButton(onClick = {
            openDateDialog = true
        }) {
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)

        }
    }

}


@Composable
private fun TransactionTextField(
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = labelText) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = MaterialTheme.shapes.extraLarge
    )
}

@Preview(showSystemUi = true)
@Composable
private fun PrevTransactionScreen() {
    TransactionScreen(
        state = TransactionState(),
        transactionCallback = MockTransactionCallback(),
        navigateUp = {},
    )
}