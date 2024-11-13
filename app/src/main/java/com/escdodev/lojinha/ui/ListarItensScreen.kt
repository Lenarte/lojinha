package com.escdodev.lojinha.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class) // Se necessário
@Composable
fun ListarItensScreen(
    viewModel: ItemViewModel,
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()

    // Obter os itens a partir do ViewModel
    val itens by viewModel.itens.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Lista de Itens à Venda",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Gray
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("incluirItens")
                },
                modifier = Modifier.padding(16.dp),
                containerColor = Color.Gray,
                elevation = FloatingActionButtonDefaults.elevation(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar Novo Item",
                    tint = Color.White
                )
            }
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(top = 16.dp, start = 30.dp, end = 30.dp, bottom = 30.dp)
            ) {
                Spacer(modifier = Modifier.height(30.dp))

                for (item in itens) {
                    Row(modifier = Modifier.padding(vertical = 8.dp)) {
                        // Exibindo o título do item
                        Text(
                            text = item.titulo,
                            fontSize = 30.sp,
                            modifier = Modifier.weight(1f)
                        )

                        // Exibindo a descrição do item
                        Text(
                            text = item.descricao,
                            fontSize = 20.sp,
                            modifier = Modifier.weight(1f),
                            color = Color.Gray // Cor de texto para a descrição
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        // Botão para editar o item
                        Button(onClick = {
                            navController.navigate("editarItem/${item.id}")
                        }) {
                            Text(text = "Editar", fontSize = 25.sp)
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // Botão para excluir o item
                        Button(onClick = {
                            coroutineScope.launch {
                                viewModel.excluir(item)
                            }
                        }) {
                            Text(text = "Excluir", fontSize = 25.sp)
                        }
                    }
                }
            }
        }
    )
}
