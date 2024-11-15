package com.escdodev.lojinha.ui

import CadeirasList
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun ItensNavHost(
    //db: ItemDatabase
    viewModel: ItemViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "cadeiras" // Inicia na tela de login
    ) {
        composable("login") {
            LoginScreen(navController)
        }
        composable("registro") {
            RegistroScreen(navController)
        }
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
        composable("cadeiras") {
            CadeirasList(navController)
        }
        composable("sofaList") {
            SofaList(viewModel, navController)
        }
        composable("mesaList") {
            MesaList(viewModel, navController)
        }

    }
}
