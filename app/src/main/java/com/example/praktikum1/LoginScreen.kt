package com.example.praktikum1

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun LoginScreen (navController: NavHostController) {
    var email by remember { mutableStateOf( "") }
    var password by remember { mutableStateOf( "") }
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val scope = rememberCoroutineScope()

    Box (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineMedium
            )

            OutlinedTextField(
                value = email,
                onValueChange = {email= it},
                label = { Text("Email") },
                placeholder = { Text("Masukkan email anda") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = {password = it},
                label = { Text("Password") },
                placeholder = { Text("Masukkan password anda") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                onClick = {
                    scope.launch {
                        if (email.isEmpty() || password.isEmpty()){
                            Toast.makeText(
                                context, "Tolong tidak boleh kosong",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            if (!userPreferences.isEmailRegistered(
                                    email,
                                    password
                            )) {
                                Toast.makeText(
                                    context, "Email tidak terdaftar. Tolong daftar terlebih dahulu",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.navigate("register")
                            } else {
                                navController.navigate("home/$email")
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = {
                navController.navigate("register")
            }) {
                Text("Belum punya akun? Register")
            }
        }
    }
}