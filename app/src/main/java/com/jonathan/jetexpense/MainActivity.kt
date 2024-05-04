package com.jonathan.jetexpense

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jonathan.jetexpense.presentation.components.JetExpenseBottomBar
import com.jonathan.jetexpense.presentation.components.JetExpenseTopBar
import com.jonathan.jetexpense.presentation.expense.ExpenseViewModel
import com.jonathan.jetexpense.presentation.home.HomeScreen
import com.jonathan.jetexpense.presentation.home.HomeViewModel
import com.jonathan.jetexpense.presentation.income.IncomeViewModel
import com.jonathan.jetexpense.presentation.navigation.ExpenseDestination
import com.jonathan.jetexpense.presentation.navigation.HomeDestination
import com.jonathan.jetexpense.presentation.navigation.IncomeDestination
import com.jonathan.jetexpense.presentation.navigation.JetExpenseDestination
import com.jonathan.jetexpense.presentation.navigation.JetExpenseNavigation
import com.jonathan.jetexpense.presentation.navigation.TransactionDestination
import com.jonathan.jetexpense.presentation.navigation.navigateToSingleTop
import com.jonathan.jetexpense.presentation.transaction.TransactionAssistedFactory
import com.jonathan.jetexpense.ui.theme.JetExpenseTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var assistedFactory: TransactionAssistedFactory
    private val allScreens = listOf(IncomeDestination, HomeDestination, ExpenseDestination)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetExpApp()
        }
    }

    @Composable
    private fun JetExpApp() {
        val homeViewModel: HomeViewModel = viewModel()
        val expenseViewModel: ExpenseViewModel = viewModel()
        val incomeViewModel: IncomeViewModel = viewModel()
        val navHostController = rememberNavController()
        val systemTheme = isSystemInDarkTheme()
        var currTheme by remember {
            mutableStateOf(
                if (systemTheme) Theme.SYSTEM else Theme.LIGHT
            )
        }

        CompositionLocalProvider(LocalAppTheme provides currTheme) {
            JetExpenseTheme(
                currTheme == Theme.DARK
            ) {
                val currentScreen = rememberCurrentScreen(navHostController);

                val icon = when (currTheme) {
                    Theme.DARK -> R.drawable.ic_switch_on
                    else -> R.drawable.ic_switch_off
                }


                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Scaffold(
                        topBar = {
                            JetExpenseTopBar(
                                title = currentScreen.pageTitle,
                                onSwitchClick = {
                                    currTheme = when (currTheme) {
                                        Theme.DARK -> Theme.LIGHT
                                        else -> Theme.DARK
                                    }
                                },
                                icon = icon,
                                onNavigateUp = {
                                    navHostController.navigateUp()
                                }
                            )
                        },
                        bottomBar = {
                            JetExpenseBottomBar(
                                allScreens = allScreens,
                                onTabSelected = {
                                    navHostController.navigateToSingleTop(it.routePath)
                                },
                                selectedTab = currentScreen
                            ) {
                                navHostController.navigateToSingleTop(TransactionDestination.routeWithArgs)
                            }
                        },
                        floatingActionButtonPosition = FabPosition.End
                    ) { paddingValues ->
                        JetExpenseNavigation(
                            modifier = Modifier
                                .padding(paddingValues)
                                .padding(16.dp),
                            navHostController = navHostController,
                            homeViewModel = homeViewModel,
                            incomeViewModel = incomeViewModel,
                            expenseViewModel = expenseViewModel,
                            assistedFactory = assistedFactory
                        )
                    }
                }


            }
        }

    }

    @Composable
    private fun rememberCurrentScreen(navController: NavController): JetExpenseDestination {
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStackEntry?.destination
        return allScreens.find { it -> it.routePath == currentDestination?.route }
            ?: TransactionDestination
    }

    private val LocalAppTheme = staticCompositionLocalOf<Theme> {
        error("No theme provided")
    }

    enum class Theme {
        SYSTEM,
        DARK,
        LIGHT
    }
}

