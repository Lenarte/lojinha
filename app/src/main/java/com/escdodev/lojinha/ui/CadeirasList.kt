package com.example.minhalojinha.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.escdodev.lojinha.R
import com.escdodev.lojinha.model.Cadeira
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadeirasList(navController: NavController) {
    var bottomState by remember { mutableStateOf("cadeira") }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(navController = navController, drawerState = drawerState)
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "MinhaLojinha",
                                fontSize = 20.sp,
                                color = Color.White,
                                fontWeight = FontWeight(600)
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch { drawerState.open() }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Abrir Drawer",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { /* Ação do Carrinho */ }) {
                                Icon(
                                    imageVector = Icons.Default.ShoppingCart,
                                    contentDescription = "Carrinho",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            IconButton(onClick = { navController.navigate("login") }) {
                                Icon(
                                    imageVector = Icons.Default.Face,
                                    contentDescription = "Perfil",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(Color.Gray)
                    )
                },
                bottomBar = {
                    NavigationBar(containerColor = Color(0xFF626262)) {
                        NavigationBarItem(
                            selected = bottomState == "cadeira",
                            onClick = {
                                bottomState = "cadeira"
                                navController.navigate("cadeiras")
                            },
                            icon = {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = "Cadeiras",
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = { Text(text = "Cadeiras") }
                        )
                    }
                }
            ) { innerPadding ->
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(innerPadding)
                ) {
                    val cadeirasList = listOf(
                        Cadeira(
                            imagem = R.drawable.cadeira,
                            titulo = "cadeira moderna",
                            descricao = "feita em madeira mdf",
                            preco = "R$ 250"
                        ),
                        Cadeira(
                            imagem = R.drawable.cadeira,
                            titulo = "cadeira retro",
                            descricao = "madeira de lei",
                            preco = "R$ 180"
                        ),
                        Cadeira(
                            imagem = R.drawable.cadeira,
                            titulo = "cadeira gamer",
                            descricao = "plástico de alta resistência",
                            preco = "R$ 700"
                        ),
                        Cadeira(
                            imagem = R.drawable.cadeira,
                            titulo = "cadeira de bar",
                            descricao = "cadeira de plástico várias cores",
                            preco = "R$ 70"
                        )
                    )

                    itemsIndexed(cadeirasList) { _, cadeira ->
                        Card(
                            modifier = Modifier.padding(10.dp),
                            shape = MaterialTheme.shapes.medium,
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(17.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = cadeira.imagem!!),
                                    contentDescription = null,
                                    alignment = Alignment.Center,
                                    contentScale = ContentScale.FillWidth,
                                    modifier = Modifier
                                        .width(200.dp)
                                        .height(100.dp)
                                )
                                Text(
                                    text = cadeira.titulo ?: "Título não disponível",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp)
                                )
                                Text(
                                    text = cadeira.descricao ?: "Descrição não disponível",
                                    color = Color.Gray,
                                    modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp),
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = cadeira.preco ?: "Preço não disponível",
                                    color = Color.LightGray,
                                    modifier = Modifier.padding(0.dp, 10.dp, 0.dp, 0.dp),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun DrawerContent(navController: NavController, drawerState: DrawerState) {
    val coroutineScope = rememberCoroutineScope()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: "cadeiras"

    Column(
        modifier = Modifier
            .width(300.dp)
            .background(Color.White)
            .padding(30.dp)
            .fillMaxHeight()
    ) {
        Text("Menu", fontSize = 24.sp, color = Color.Black, modifier = Modifier.padding(bottom = 16.dp))

        DrawerItem(
            label = "Cadeiras",
            icon = Icons.Default.Home,
            isSelected = currentRoute == "cadeiras",
            onClick = {
                navController.navigate("cadeiras")
                coroutineScope.launch { drawerState.close() }
            }
        )
        DrawerItem(
            label = "Mesas",
            icon = Icons.Default.Favorite,
            isSelected = currentRoute == "mesa",
            onClick = {
                navController.navigate("mesa")
                coroutineScope.launch { drawerState.close() }
            }
        )
        DrawerItem(
            label = "Sofá",
            icon = Icons.Default.Favorite,
            isSelected = currentRoute == "sofa",
            onClick = {
                navController.navigate("sofa")
                coroutineScope.launch { drawerState.close() }
            }
        )
        DrawerItem(
            label = "Itens à Venda",
            icon = Icons.Default.ShoppingCart,
            isSelected = currentRoute == "listarItens",
            onClick = {
                navController.navigate("listarItens")
                coroutineScope.launch { drawerState.close() }
            }
        )
        Spacer(modifier = Modifier.weight(1f))

        DrawerItem(
            label = "Login",
            icon = Icons.Default.Person,
            isSelected = currentRoute == "login",
            onClick = {
                navController.navigate("login")
                coroutineScope.launch { drawerState.close() }
            }
        )
    }
}


@Composable
fun DrawerItem(label: String, icon: ImageVector, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = label, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = label, fontSize = 18.sp, color = if (isSelected) Color.Blue else Color.Black)
    }
}