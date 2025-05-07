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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.foundation.shape.RoundedCornerShape

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DontPushMyButtonsTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text(text = "Hello World!", style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.height(16.dp))
                        FixedGrid()
                    }
                }
            }
        }
    }
}

class ButtonItem(
    val label: Int,
    val color: Color,
    val shape: Shape
)

val button1 = ButtonItem(
    label = 1,
    color = Color.Blue,
    shape = RoundedCornerShape(0.dp)
)

val button2 = ButtonItem(
    label = 2,
    color = Color.Red,
    shape = RoundedCornerShape(16.dp)
)

val button3 = ButtonItem(
    label = 3,
    color = Color.Green,
    shape = RoundedCornerShape(100.dp)
)

@Composable
fun Title(label: String) {
    Text(
        text = label,
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun FixedGrid() {
    val items: List<ButtonItem> = listOf(
        button1,
        button2,
        button3
    )

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
            items(items) { item ->
                ButtonGridItem(label = item.label, color = item.color, shape = item.shape)
            }
        }
    }
}

@Composable
fun ButtonGridItem(label: Int, color: Color, shape: Shape) {
    Button(
        onClick = { /* Handle click */ },
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = shape,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
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
