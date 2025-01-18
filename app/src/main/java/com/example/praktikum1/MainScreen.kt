package com.example.praktikum1

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.Data
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MainScreen(navController: NavController, email: String, screen: String) {
    val context = LocalContext.current
    val userPreferences = UserPreferences(context)
    val userFlow = userPreferences.userFlow.collectAsState(
        initial = User("", "", 0, "", "")
    )
    val user = userFlow.value
    Scaffold(
        bottomBar = { BottomNavBar(navController, user, onLogout = {
            logout(userPreferences, navController)
        }) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (screen) {
                "home" -> HomeScreen(context, user)
                "profile" -> ProfileScreen(context, user)
                else -> Text("Unknown Screen")
            }
        }
    }
}

private fun logout(userPreferences: UserPreferences, navController: NavController) {
    CoroutineScope(Dispatchers.IO).launch {
        userPreferences.clearUser Data()
        withContext(Dispatchers.Main) {
            navController.navigate("login") {
                popUpTo("login") { inclusive = true }
            }
        }
    }
}