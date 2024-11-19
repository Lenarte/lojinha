import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.rememberAsyncImagePainter
import com.escdodev.lojinha.ui.ItemViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadeirasList(viewModel: ItemViewModel, navController: NavHostController) {
    var bottomState by remember { mutableStateOf("cadeira") }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val itens by viewModel.itens.collectAsState()


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
                    items(itens) { item ->
                        Card(
                            modifier = Modifier.padding(8.dp),
                            shape = MaterialTheme.shapes.medium,
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                item.imagemUri?.let { uri ->
                                    Image(
                                        painter = rememberAsyncImagePainter(uri),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(150.dp)
                                    )
                                }

                                Text(
                                    text = item.titulo,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                                Text(
                                    text = "R$ ${item.preco}",
                                    color = Color.Gray,
                                    modifier = Modifier.padding(top = 4.dp)
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
                navController.navigate("CadeirasList")
            }
        )
        DrawerItem(
            label = "Mesas",
            icon = Icons.Default.Favorite,
            isSelected = currentRoute == "mesa",
            onClick = {
                navController.navigate("MesaList")
            }
        )
        DrawerItem(
            label = "Sofá",
            icon = Icons.Default.Favorite,
            isSelected = currentRoute == "sofa",
            onClick = {
                navController.navigate("SofaList")
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
