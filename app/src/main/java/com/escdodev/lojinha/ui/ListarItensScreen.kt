package com.escdodev.lojinha.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

// INFRAESTRUTURA DE UI
@Composable
fun ListarItensScreen(
    //db: ItemDatabase,
    viewModel: ItemViewModel,
    navController: NavController
){

    var coroutineScope = rememberCoroutineScope()

    // Popular e carregar dados do banco
    //var itens by remember { mutableStateOf(listOf<Item>()) }
    val itens by viewModel.itens.collectAsState()

//    LaunchedEffect(Unit) {
//        coroutineScope.launch {
//            if(db.afazerDao().listarAfazeres().isEmpty()){
//                db.afazerDao().gravarAfazer(
//                    Afazer(titulo="A", descricao = "A")
//                )
//                db.afazerDao().gravarAfazer(
//                    Afazer(titulo="B", descricao = "B")
//                )
//                db.afazerDao().gravarAfazer(
//                    Afazer(titulo="C", descricao = "C")
//                )
//            }
//            afazeres = db.afazerDao().listarAfazeres()
//        }
//    }


    Column(
        modifier = Modifier.padding(
            top =  90.dp,
            start = 30.dp,
            end = 30.dp,
            bottom = 30.dp
        )
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(text = "Lista de Itens a venda",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.height(10.dp))

        for(item in itens){
            Row {
                Text(text = item.titulo,
                    fontSize = 30.sp)

                Button(onClick = {
                    //Navegação editar
                    navController.navigate("editarItem/${item.id}")
                }) {
                    Text(text = "Editar", fontSize = 25.sp)
                }


                Button(onClick = {
                    coroutineScope.launch {
//                        db.itemDao().excluirItem(item)
//                        itens = db.itemDao().listarItens()
                        viewModel.excluir(item)
                    }
                }) {
                    Text(text = "Excluir", fontSize = 25.sp)
                }
            }

        }

        Button(onClick = {
            navController.navigate("incluir itens a venda")
        }) {
            Text(text = "Novo item a venda")
        }
    }
}
