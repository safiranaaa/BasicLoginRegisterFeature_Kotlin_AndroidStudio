package com.example.praktikum1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.praktikum1.ui.theme.Praktikum1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Praktikum1Theme {
                Surface{
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "Login"
    ){
        composable("Login"){ LoginScreen(navController) }
        composable("Register"){ RegisterScreen(navController) }
        composable("home/{email}"){backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: "Unknown"
            MainScreen(navController, email, "home")
        }
        composable("profile/{email}") {backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: "Unknown"
            MainScreen(navController, email, "profile")
        }
    }
}