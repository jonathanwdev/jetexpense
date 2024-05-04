package com.jonathan.jetexpense.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jonathan.jetexpense.presentation.expense.ExpenseScreen
import com.jonathan.jetexpense.presentation.expense.ExpenseViewModel
import com.jonathan.jetexpense.presentation.home.HomeScreen
import com.jonathan.jetexpense.presentation.home.HomeViewModel
import com.jonathan.jetexpense.presentation.income.IncomeScreen
import com.jonathan.jetexpense.presentation.income.IncomeViewModel
import com.jonathan.jetexpense.presentation.transaction.TransactionAssistedFactory
import com.jonathan.jetexpense.presentation.transaction.TransactionScreen

@Composable
fun JetExpenseNavigation(
    modifier: Modifier = Modifier,
    assistedFactory: TransactionAssistedFactory,
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
    incomeViewModel: IncomeViewModel,
    expenseViewModel: ExpenseViewModel
) {
    NavHost(navController = navHostController, startDestination = HomeDestination.routePath) {
        composable(
            route = HomeDestination.routePath
        ) {
            HomeScreen(
                state = homeViewModel.homeUiState,
                modifier = modifier,
                onIncomeClick = {
                    navHostController.navigateToTransactionScreen(
                        id = it,
                        transactionType = IncomeDestination.routePath
                    )
                },
                onClickSeeAllIncome = {
                    navHostController.navigateToSingleTop(IncomeDestination.routePath)
                },
                onExpenseClick = {
                    navHostController.navigateToTransactionScreen(
                        id = it,
                        transactionType = ExpenseDestination.routePath
                    )
                },
                onClickSeeAllExpense = {
                    navHostController.navigateToSingleTop(ExpenseDestination.routePath)
                },
                onInsertExpense = homeViewModel::insertExpense,
                onInsertIncome = homeViewModel::insertIncome
            )
        }

        composable(
            route = ExpenseDestination.routePath
        ) {
            ExpenseScreen(
                modifier = modifier,
                expenses = expenseViewModel.expenseState.expenses,
                onExpenseItemClick = { expense ->
                    navHostController.navigateToTransactionScreen(
                        id = expense,
                        transactionType = ExpenseDestination.routePath
                    )
                },
                onExpenseItemDelete = {
                    expenseViewModel.deleteExpense(it)
                }
            )
        }

        composable(
            route = IncomeDestination.routePath
        ) {
            IncomeScreen(
                incomes = incomeViewModel.incomeState.incomes,
                modifier = modifier,
                onIncomeItemClick = { income ->
                    navHostController.navigateToTransactionScreen(
                        id = income,
                        transactionType = IncomeDestination.routePath
                    )
                },
                onIncomeItemDelete = {
                    incomeViewModel.deleteIncome(it)
                }
            )
        }

        composable(
            route = TransactionDestination.routeWithArgs,
            arguments = TransactionDestination.arguments
        ) { navBackStackEntry ->
            val transType =
                navBackStackEntry.arguments?.getString(TransactionDestination.transactionTypeArg)
                    ?: ""
            val transId =
                navBackStackEntry.arguments?.getInt(TransactionDestination.idTypeArg) ?: -1

            TransactionScreen(
                modifier = modifier,
                transactionType = transType,
                transactionId = transId,
                assistedFactory = assistedFactory,
                navigateUp = {
                    navHostController.navigateUp()
                }
            )
        }
    }
}

/**
 * @param route navRoute
 *
 * **/
fun NavHostController.navigateToSingleTop(route: String) {
    navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true

    }
}

fun NavHostController.navigateToTransactionScreen(
    id: Int = -1,
    transactionType: String = ""
) {
    val route =
        "${TransactionDestination.routePath}?${TransactionDestination.transactionTypeArg}=$transactionType&${TransactionDestination.idTypeArg}=$id"
    navigateToSingleTop(route)
}