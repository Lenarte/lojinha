//package com.escdodev.lojinha.ui
//
//import android.widget.Toast
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun LoginScreen(navController: NavController) {
//    val emailState = remember { mutableStateOf("") }
//    val passwordState = remember { mutableStateOf("") }
//    val context = LocalContext.current
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text(text = "Login", style = MaterialTheme.typography.headlineMedium)
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        TextField(
//            value = emailState.value,
//            onValueChange = { emailState.value = it },
//            label = { Text("Email") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        TextField(
//            value = passwordState.value,
//            onValueChange = { passwordState.value = it },
//            label = { Text("Senha") },
//            visualTransformation = PasswordVisualTransformation(),
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        Button(
//            onClick = {
//                val email = emailState.value.trim()
//                val password = passwordState.value.trim()
//                if (email.isNotEmpty() && password.isNotEmpty()) {
//                    FirebaseAuth.getInstance()
//                        .signInWithEmailAndPassword(email, password)
//                        .addOnCompleteListener { task ->
//                            if (task.isSuccessful) {
//                                val userId = task.result.user?.uid
//                                if (userId != null) {
//                                    // Buscar os dados do Firestore
//                                    FirebaseFirestore.getInstance()
//                                        .collection("usuarios")
//                                        .document(userId)
//                                        .get()
//                                        .addOnSuccessListener { document ->
//                                            if (document.exists()) {
//                                                val nome = document.getString("nome")
//                                                Toast.makeText(context, "Bem-vindo, $nome!", Toast.LENGTH_SHORT).show()
//                                                navController.navigate("cadeiras")
//                                            } else {
//                                                Toast.makeText(context, "Erro: Dados do usuário não encontrados.", Toast.LENGTH_SHORT).show()
//                                            }
//                                        }
//                                        .addOnFailureListener { e ->
//                                            Toast.makeText(context, "Erro ao buscar dados: ${e.message}", Toast.LENGTH_SHORT).show()
//                                        }
//                                }
//                            } else {
//                                Toast.makeText(context, "Erro: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
//                            }
//                        }
//                } else {
//                    Toast.makeText(context, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
//                }
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Login")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        TextButton(onClick = { navController.navigate("registro") }) {
//            Text("Não tem uma conta? Registre-se")
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        TextButton(onClick = { navController.navigate("cadeiras") }) {
//            Text("Entrar sem fazer login")
//        }
//    }
//}
//
package com.escdodev.lojinha.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEEEEE)) // Fundo cinza suave
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Minha Lojinha",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 32.sp),
            color = Color(0xFF444444)
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val email = emailState.value.trim()
                val password = passwordState.value.trim()
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val userId = task.result.user?.uid
                                if (userId != null) {
                                    FirebaseFirestore.getInstance()
                                        .collection("usuarios")
                                        .document(userId)
                                        .get()
                                        .addOnSuccessListener { document ->
                                            if (document.exists()) {
                                                val nome = document.getString("nome")
                                                Toast.makeText(context, "Bem-vindo, $nome!", Toast.LENGTH_SHORT).show()
                                                navController.navigate("cadeiras")
                                            } else {
                                                Toast.makeText(context, "Erro: Dados do usuário não encontrados.", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(context, "Erro ao buscar dados: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                }
                            } else {
                                Toast.makeText(context, "Erro: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF444444)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Login", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate("registro") }) {
            Text("Não tem uma conta? Registre-se", color = Color(0xFF555555))
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { navController.navigate("cadeiras") }) {
            Text("Entrar sem fazer login", color = Color(0xFF555555))
        }
    }
}