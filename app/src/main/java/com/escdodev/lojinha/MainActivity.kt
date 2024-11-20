package com.escdodev.lojinha


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.escdodev.lojinha.dados.ItemDatabase.Companion.abrirBancoDeDados
import com.escdodev.lojinha.dados.LocalRepository
import com.escdodev.lojinha.dados.RemoteRepository
import com.escdodev.lojinha.dados.SyncRepository

import com.escdodev.lojinha.ui.ItemViewModel
import com.escdodev.lojinha.ui.ItensNavHost


//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//
//        val isLocal = false
//
//        //val context = LocalContext.current
//        val db = abrirBancoDeDados(this)
//        //val viewModel = itemViewModel(db.itemDao())
//        val localRepository = LocalRepository(db.itemDao())
//        val remoteRepository = RemoteRepository()
//        //val viewModel = ItemViewModel(localRepository)
//
//        val viewModel: ItemViewModel
//        if (isLocal){
//            viewModel = ItemViewModel(localRepository)
//        } else {
//            viewModel = ItemViewModel(remoteRepository)
//        }
//
//
//        setContent {
//            ItensNavHost(viewModel = viewModel)
//        }
//    }
//}

//novo com off

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializar os repositórios
        val localRepository = LocalRepository(
            abrirBancoDeDados(applicationContext).itemDao()
        )
        val remoteRepository = RemoteRepository()
        val syncRepository = SyncRepository(localRepository, remoteRepository)

        // Inicializar a ViewModel com o SyncRepository
        val itemViewModel = ItemViewModel(syncRepository)

        setContent {
            // Passar a ViewModel para o ItensNavHost
            ItensNavHost(viewModel = itemViewModel)
        }
    }
}






/// USO POSTERIOR

//object AfazeresRoute{
//    val LISTAR_AFAZERES_SCREEN = "listar_afazeres"
//    val INCLUIR_AFAZER_SCREEN = "incluir_afazer"
//}
//
//@Composable
//fun AfazeresNavHost(){
//
//    val context = LocalContext.current
//    val db = AfazerDatabase.getInstance(context)
//
//    val navController = rememberNavController()
//    NavHost(
//        navController = navController,
//        startDestination =  AfazeresRoute.LISTAR_AFAZERES_SCREEN){
//        //Primeira tela listar
//        composable(AfazeresRoute.LISTAR_AFAZERES_SCREEN) {
//            ListarAfazeresScreen(db, navController)
//        }
//        //Segunda tela incluir
//        composable(AfazeresRoute.INCLUIR_AFAZER_SCREEN) {
//            IncluirAfazerScreen(db, navController)
//        }
//
//    }
//}

//@Composable
//fun IncluirAfazerScreen(
//    db: AfazerDatabase,
//    navController: NavController
//){
//    Text(text = "Tela para incluir afazer")
//}


