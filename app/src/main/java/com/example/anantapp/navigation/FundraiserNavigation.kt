package com.example.anantapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.anantapp.presentation.screen.CreateFundraiserScreen
import com.example.anantapp.presentation.screen.SelectFundraiserCategoryScreen
import com.example.anantapp.presentation.viewmodel.CreateFundraiserViewModel
import com.example.anantapp.presentation.viewmodel.SelectFundraiserCategoryViewModel

/**
 * Navigation routes for fundraiser feature
 */
object FundraiserRoutes {
    const val SELECT_CATEGORY = "fundraiser/select_category"
    const val CREATE_FUNDRAISER = "fundraiser/create"
}

/**
 * Add fundraiser navigation routes to the NavGraphBuilder
 */
fun NavGraphBuilder.fundraiserNavigation(navController: NavController) {
    
    composable(FundraiserRoutes.SELECT_CATEGORY) {
        val viewModel = SelectFundraiserCategoryViewModel()
        SelectFundraiserCategoryScreen(
            viewModel = viewModel,
            onNextClick = { category, customTitle ->
                navController.navigate(FundraiserRoutes.CREATE_FUNDRAISER) {
                    popUpTo(FundraiserRoutes.SELECT_CATEGORY) { inclusive = true }
                }
            },
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
    
    composable(FundraiserRoutes.CREATE_FUNDRAISER) {
        val viewModel = CreateFundraiserViewModel()
        CreateFundraiserScreen(
            viewModel = viewModel,
            onBackClick = {
                navController.popBackStack()
            },
            onDraftSaved = {
                navController.popBackStack()
            },
            onFundraiserCreated = { fundraiserId ->
                // Navigate to fundraiser details or success screen
                navController.popBackStack()
            }
        )
    }
}

/**
 * Navigate to fundraiser creation flow
 */
fun NavController.navigateToFundraiserFlow() {
    navigate(FundraiserRoutes.SELECT_CATEGORY) {
        launchSingleTop = true
    }
}

/**
 * Navigate to fundraiser creation screen directly
 */
fun NavController.navigateToCreateFundraiser() {
    navigate(FundraiserRoutes.CREATE_FUNDRAISER) {
        launchSingleTop = true
    }
}
