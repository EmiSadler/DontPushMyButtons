package com.example.dontpushmybuttons

import android.icu.text.CaseMap.Title
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.dontpushmybuttons.ui.theme.DontPushMyButtonsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DontPushMyButtonsTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Hello World!", style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.height(16.dp))
                        FixedGrid()
                    }
                }
            }
        }
    }
}
@Composable
fun Title(lable: String) {
    Text(
        text = lable,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun FixedGrid() {
    val items = List(32) { "${it + 1}" }

    Box(
        modifier = Modifier.padding( top = 56.dp)
            .fillMaxSize()
            .padding(8.dp)

    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(items) { label ->
                ButtonGridItem(label = label)
            }
        }
    }
}

@Composable
fun ButtonGridItem(label: String) {
    Button(
        onClick = { /* Handle click */ },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Text(text = label)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFixedGrid() {
    DontPushMyButtonsTheme {
        Title("Don't Push My Buttons")
        FixedGrid()
    }
}
