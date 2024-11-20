package com.escdodev.lojinha.ui

import CadeirasList
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun ItensNavHost(viewModel: ItemViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "cadeiras"
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

        //off
        composable("incluirItens") {
            IncluirEditarItemScreen(viewModel = viewModel, navController = navController)
        }
        composable("editarItem/{itemId}") { navRequest ->
            val itemId = navRequest.arguments?.getString("itemId")?.toInt()
            IncluirEditarItemScreen(itemId, viewModel, navController)
        }
        composable("cadeiras") {
            CadeirasList(viewModel, navController)
        }
        composable("sofaList") {
            SofaList(viewModel, navController)
        }
        composable("mesaList") {
            MesaList(viewModel, navController)
        }
    }
}

