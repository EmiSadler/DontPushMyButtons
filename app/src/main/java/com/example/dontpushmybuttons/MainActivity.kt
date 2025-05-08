package com.example.dontpushmybuttons

import android.graphics.Color.parseColor
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dontpushmybuttons.ui.theme.DontPushMyButtonsTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DontPushMyButtonsTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Spacer(modifier = Modifier.height(16.dp))
                        FixedGrid()
                    }
                }
            }
        }
    }
}

val colourRed = "#F45B69".color
val colourGreen = "#92EF80".color
val colourYellow = "#FFBE0B".color
val colourTeal = "#028090".color
val colourBlue = "#9CFFFA".color
val colourDarkGreen = "#137547".color

class ButtonItem(
    val label: Int,
    val color: Color,
    val shape: Shape
)

val button1 = ButtonItem(
    label = 1,
    color = colourYellow,
    shape = RoundedCornerShape(0.dp)
)

val button2 = ButtonItem(
    label = 2,
    color = colourGreen,
    shape = RoundedCornerShape(16.dp)
)

val button3 = ButtonItem(
    label = 3,
    color = colourRed,
    shape = RoundedCornerShape(100.dp)
)

val button4 = ButtonItem(
    label = 4,
    color = colourDarkGreen,
    shape = RoundedCornerShape(0.dp)
)
val button5 = ButtonItem(
    label = 5,
    color = colourBlue,
    shape = RoundedCornerShape(0.dp)
)

val button6 = ButtonItem(
    label = 6,
    color = colourYellow,
    shape = RoundedCornerShape(16.dp)
)

val button7 = ButtonItem(
    label = 7,
    color = colourTeal,
    shape = RoundedCornerShape(100.dp)
)

val button8 = ButtonItem(
    label = 8,
    color = colourBlue,
    shape = RoundedCornerShape(0.dp)
)
val button9 = ButtonItem(
    label = 9,
    color = colourRed,
    shape = RoundedCornerShape(0.dp)
)

val button10 = ButtonItem(
    label = 10,
    color = colourDarkGreen,
    shape = RoundedCornerShape(16.dp)
)

val button11 = ButtonItem(
    label = 11,
    color = colourBlue,
    shape = RoundedCornerShape(100.dp)
)

val button12 = ButtonItem(
    label = 12,
    color = colourGreen,
    shape = RoundedCornerShape(0.dp)
)
val button13 = ButtonItem(
    label = 13,
    color = colourTeal,
    shape = RoundedCornerShape(0.dp)
)

val button14 = ButtonItem(
    label = 14,
    color = colourBlue,
    shape = RoundedCornerShape(16.dp)
)

val button15 = ButtonItem(
    label = 15,
    color = colourYellow,
    shape = RoundedCornerShape(100.dp)
)

val button16 = ButtonItem(
    label = 16,
    color = colourRed,
    shape = RoundedCornerShape(0.dp)
)
val button17 = ButtonItem(
    label = 17,
    color = colourGreen,
    shape = RoundedCornerShape(0.dp)
)

val button18 = ButtonItem(
    label = 18,
    color = colourTeal,
    shape = RoundedCornerShape(16.dp)
)

val button19 = ButtonItem(
    label = 19,
    color = colourRed,
    shape = RoundedCornerShape(100.dp)
)

val button20 = ButtonItem(
    label = 20,
    color = colourDarkGreen,
    shape = RoundedCornerShape(0.dp)
)
val button21 = ButtonItem(
    label = 21,
    color = colourRed,
    shape = RoundedCornerShape(0.dp)
)

val button22 = ButtonItem(
    label = 22,
    color = colourDarkGreen,
    shape = RoundedCornerShape(16.dp)
)

val button23 = ButtonItem(
    label = 23,
    color = colourYellow,
    shape = RoundedCornerShape(100.dp)
)

val button24 = ButtonItem(
    label = 24,
    color = colourBlue,
    shape = RoundedCornerShape(0.dp)
)
val button25 = ButtonItem(
    label = 25,
    color = colourYellow,
    shape = RoundedCornerShape(0.dp)
)

val button26 = ButtonItem(
    label = 26,
    color = colourGreen,
    shape = RoundedCornerShape(16.dp)
)

val button27 = ButtonItem(
    label = 27,
    color = colourRed,
    shape = RoundedCornerShape(100.dp)
)

val button28 = ButtonItem(
    label = 28,
    color = colourTeal,
    shape = RoundedCornerShape(0.dp)
)
val button29 = ButtonItem(
    label = 29,
    color = colourRed,
    shape = RoundedCornerShape(0.dp)
)

val button30 = ButtonItem(
    label = 30,
    color = colourBlue,
    shape = RoundedCornerShape(16.dp)
)

val button31 = ButtonItem(
    label = 31,
    color = colourTeal,
    shape = RoundedCornerShape(100.dp)
)

val button32 = ButtonItem(
    label = 32,
    color = colourDarkGreen,
    shape = RoundedCornerShape(0.dp)
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
    var counter by remember { mutableStateOf(0) }
    val items: List<ButtonItem> = listOf(
        button1,
        button2,
        button3,
        button4,
        button5,
        button6,
        button7,
        button8,
        button9,
        button10,
        button11,
        button12,
        button13,
        button14,
        button15,
        button16,
        button17,
        button18,
        button19,
        button20,
        button21,
        button22,
        button23,
        button24,
        button25,
        button26,
        button27,
        button28,
        button29,
        button30,
        button31,
        button32
    )

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Count: $counter",
            style = MaterialTheme.typography.headlineSmall,
        )

        Box(
            modifier = Modifier.padding(top = 56.dp)
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
                    ButtonGridItem(label = item.label, color = item.color, shape = item.shape, onClickIncrement = { counter++ }
                    )
                }
            }
        }
    }
}

@Composable
fun ButtonGridItem(label: Int, color: Color, shape: Shape, onClickIncrement: () -> Unit) {
    Button(
        onClick = onClickIncrement,
        colors = ButtonDefaults.buttonColors(containerColor = color),
        shape = shape,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Text(
            text = label.toString(),
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black
        )
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

val String.color
    get() = Color(parseColor(this))