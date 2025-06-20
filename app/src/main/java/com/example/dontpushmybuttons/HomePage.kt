package com.example.dontpushmybuttons

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun HomePage(navController: NavController) {
    Column {
        Button(onClick = { navController.navigate("sassybuttons.SassyButtons") }) {
            Text("Don't Push My Buttons")
        }
        Button(onClick = { navController.navigate("game2") }) {
            Text("Game 2")
        }
        // Add more buttons for other games
    }
}