package com.example.praktikum1

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: NavController, user: User, onLogout: () -> Unit) {
    val items = listOf("Name", "Profile", "Logout")
    val icons = listOf(Icons.Filled.Home, Icons.Filled.Person, Icons.Filled.ExitToApp)
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route ?: "home"
    val selectedItem = when (currentRoute) {
        "home/${user.email}" -> 0
        "profile/${user.email}" -> 1
        else -> 0
    }
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = item,
                        tint = if (selectedItem == index)
                            MaterialTheme.colorScheme.primary else
                                MaterialTheme.colorScheme.onSurface
                    )
                },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = {
                    when (item) {
                        "Home" -> navController.navigate("home/${user.email}") {
                            launchSingleTop = true
                        }
                        "Profile" -> navController.navigate("profile/${user.email}") {
                            launchSingleTop = true
                        }
                        "Logout" -> onLogout()
                    }
                }
            )

        }
    }
}