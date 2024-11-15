package com.escdodev.lojinha.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.escdodev.lojinha.dados.Item
import kotlinx.coroutines.launch


@Composable
fun IncluirEditarItemScreen(
    itemId: Int? = null,
    viewModel: ItemViewModel,
    navController: NavController
) {
    var coroutineScope = rememberCoroutineScope()

    // Dados do item
    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("0.0") }
    var imagemUri by remember { mutableStateOf<String?>(null) }

    var item: Item? by remember { mutableStateOf(null) }

    val label = if (itemId == null) "Novo Item" else "Editar Item"

    // Launcher fora da função do botão, para não recriar a cada recomposição
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        imagemUri = uri?.toString()
    }

    LaunchedEffect(itemId) {
        coroutineScope.launch {
            if (itemId != null) {
                item = viewModel.buscarPorId(itemId)
                item?.let {
                    titulo = it.titulo
                    descricao = it.descricao
                    preco = it.preco.toString()
                    imagemUri = it.imagemUri
                }
            }
        }
    }

    Column(
        modifier = Modifier.padding(30.dp)
    ) {
        Text(
            text = label,
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text("Descrição") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = preco,
            onValueChange = { preco = it },
            label = { Text("Preço") }
        )
        Spacer(modifier = Modifier.height(10.dp))

        // Botão para selecionar imagem com cor cinza
        Button(
            onClick = {
                launcher.launch("image/*")  // Chama o launcher fora do escopo do Button
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray) // Cor do botão cinza
        ) {
            Text(text = "Selecionar Imagem")
        }

        // Exibir a imagem selecionada, se existir
        imagemUri?.let {
            Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Botão de salvar com cor cinza
        Button(
            onClick = {
                coroutineScope.launch {
                    val itemSalvar = Item(
                        id = itemId,
                        titulo = titulo,
                        descricao = descricao,
                        preco = preco.toDouble(),
                        imagemUri = imagemUri // Salvando a URI da imagem
                    )
                    viewModel.gravar(itemSalvar)
                    navController.popBackStack()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray) // Cor do botão cinza
        ) {
            Text(text = "Salvar", fontSize = 30.sp)
        }
    }
}




