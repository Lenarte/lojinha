package com.escdodev.lojinha.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.escdodev.lojinha.dados.Item
import kotlinx.coroutines.launch

@Composable
fun IncluirEditarItemScreen(
    itemId: Int? = null,
    //db: ItemDatabase,
    viewModel: ItemViewModel,
    navController: NavController
){

    var coroutineScope = rememberCoroutineScope()

    // Dados do novo item
    var titulo by remember {  mutableStateOf( "") }
    var descricao by remember { mutableStateOf( "") }

    var item: Item? by remember { mutableStateOf(null) }

    var label = if (itemId == null) "Novo Item a venda" else "Editar Item"

    LaunchedEffect(itemId) {
        coroutineScope.launch {
            if(itemId != null){
                item = viewModel.buscarPorId(itemId)
                item?.let {
                    titulo = it.titulo
                    descricao = it.descricao
                }
            }
        }
    }

    Column(
        modifier = Modifier.padding(
            top =  90.dp,
            start = 30.dp,
            end = 30.dp,
            bottom = 30.dp
        )
    ) {
        Text(
            text = label,
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it }
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = descricao,
            onValueChange = { descricao = it }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            //Serve para disparar o segundo processo
            coroutineScope.launch {
                val itemSalvar = Item(
                    id = itemId,
                    titulo = titulo,
                    descricao = descricao
                )
//                db.afazerDao().gravarItem(ItemSalvar)
                viewModel.gravar(itemSalvar)
                navController.popBackStack()
                //itens = db.itemDao().listarItens()
                //navController.navigate("listarItens")
            }
        }) {
            Text(text = "Salvar", fontSize = 30.sp)
        }
    }

}
