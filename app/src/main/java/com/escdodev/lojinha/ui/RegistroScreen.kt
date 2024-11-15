package com.escdodev.lojinha.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun RegistroScreen(navController: NavController) {
    val nomeState = remember { mutableStateOf("") }
    val numeroState = remember { mutableStateOf("") }
    val cpfState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Registro", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = nomeState.value,
            onValueChange = { nomeState.value = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = numeroState.value,
            onValueChange = { numeroState.value = it },
            label = { Text("Número") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = cpfState.value,
            onValueChange = { cpfState.value = it },
            label = { Text("CPF") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val nome = nomeState.value.trim()
                val numero = numeroState.value.trim()
                val cpf = cpfState.value.trim()
                val email = emailState.value.trim()
                val password = passwordState.value.trim()

                if (nome.isNotEmpty() && numero.isNotEmpty() && cpf.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                    FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val userId = task.result.user?.uid
                                val firestore = FirebaseFirestore.getInstance()

                                // Dados a serem salvos
                                val user = hashMapOf(
                                    "nome" to nome,
                                    "numero" to numero,
                                    "cpf" to cpf,
                                    "email" to email
                                )

                                // Salvar no Firestore
                                firestore.collection("usuarios")
                                    .document(userId!!)
                                    .set(user)
                                    .addOnSuccessListener {
                                        Toast.makeText(context, "Registro e dados salvos com sucesso!", Toast.LENGTH_SHORT).show()
                                        navController.navigate("cadeiras")
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(context, "Erro ao salvar os dados: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                            } else {
                                Toast.makeText(context, "Erro: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrar")
        }
    }
}

