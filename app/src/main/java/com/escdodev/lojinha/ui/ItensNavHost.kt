package com.escdodev.lojinha.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.escdodev.lojinha.ui.IncluirEditarItemScreen
import com.escdodev.lojinha.ui.ItemViewModel
import com.escdodev.lojinha.ui.ListarItensScreen

@Composable
fun ItensNavHost(
    //db: ItemDatabase
    viewModel: ItemViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "listarItens")
    {
        composable("listarItens") {
            ListarItensScreen(viewModel, navController)
        }
        composable("incluirItens") {
            IncluirEditarItemScreen(viewModel = viewModel, navController = navController)
        }
        composable("editarItem/{itemId}") { navRequest ->
            val itemId = navRequest.arguments?.getString("itemId")
            IncluirEditarItemScreen(itemId?.toInt(), viewModel, navController)
        }
    }
}


