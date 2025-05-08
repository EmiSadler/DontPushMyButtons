package com.example.dontpushmybuttons
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.material3.Switch
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import com.example.dontpushmybuttons.ui.theme.DontPushMyButtonsTheme


class MainActivity : ComponentActivity() {
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
        val systemDarkTheme = isSystemInDarkTheme()
        var isDarkTheme by rememberSaveable {
            mutableStateOf(systemDarkTheme)
         }
        var counter by remember { mutableStateOf(0) }
        var isGameRunning by remember { mutableStateOf(false) }
        var gameOver by remember { mutableStateOf(false) }
        var currentHint by remember { mutableStateOf<String?>(null) }

        DontPushMyButtonsTheme(darkTheme = isDarkTheme) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier
                            .offset(x = 45.dp)
                            .padding(horizontal = 16.dp, vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isGameRunning && !gameOver) {
                            Text(
                                text = "Count: $counter",
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.weight(1f)
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Switch(
                                checked = isDarkTheme,
                                onCheckedChange = { isDarkTheme = it }
                            )
                        }
                    }

                    FixedGrid(
                        counter = counter,
                        onCounterChange = { counter = it },
                        isGameRunning = isGameRunning,
                        onGameRunningChange = { isGameRunning = it },
                        gameOver = gameOver,
                        onGameOverChange = { gameOver = it },
                        currentHint = currentHint,
                        onHintChange = { currentHint = it }
                    )
                }
            }
        }
    }
}}


val String.color
    get() = Color(this.toColorInt())

val colourRed = "#F45B69".color
val colourGreen = "#92EF80".color
val colourYellow = "#FFBE0B".color
val colourTeal = "#028090".color
val colourBlue = "#9CFFFA".color
val colourDarkGreen = "#137547".color

class ButtonItem(
    val label: Int,
    val color: Color,
    val shape: Shape,
    val hint: List<String>
)

val button1 = ButtonItem(
    label = 1,
    shape = GenericShape { size, _ ->
        addPath(createSmoothTrianglePath(size.maxDimension))
    },
    color = colourYellow,
    hint = listOf("Don't", "Push", "My", "Buttons")

)

val button2 = ButtonItem(
    label = 2,
    shape = GenericShape { size, _ ->
        addPath(createSmoothHexagonPath(size.minDimension))
    },
    color = colourGreen,
    hint = listOf("Don't", "Push", "My", "Buttons")
)

val button3 = ButtonItem(
    label = 3,
    color = colourRed,
    shape = RoundedCornerShape(0.dp),
    hint = listOf("Don't", "Push", "My", "Buttons")
)

val button4 = ButtonItem(
    label = 4,
    color = colourDarkGreen,
    shape = GenericShape { size, _ ->
        addPath(createSmoothPentagonPath(size.minDimension))
    },
    hint = listOf("Don't", "Push", "My", "Buttons")
)
val button5 = ButtonItem(
    label = 5,
    color = colourBlue,
    shape = RoundedCornerShape(100.dp),
    hint = listOf("Don't", "Push", "My", "Buttons")
)

val button6 = ButtonItem(
    label = 6,
    color = colourYellow,
    shape = RoundedCornerShape(16.dp),
    hint = listOf("Don't", "Push", "My", "Buttons")
)

val button7 = ButtonItem(
    label = 7,
    color = colourTeal,
    shape = RoundedCornerShape(100.dp),
    hint = listOf("Don't", "Push", "My", "Buttons")
)

val button8 = ButtonItem(
    label = 8,
    color = colourBlue,
    shape = GenericShape { size, _ ->
        addPath(createSmoothHexagonPath(size.minDimension))
    },
    hint = listOf("Don't", "Push", "My", "Buttons")
)
val button9 = ButtonItem(
    label = 9,
    color = colourRed,
    shape = GenericShape { size, _ ->
        addPath(createSmoothPentagonPath(size.minDimension))
    },
    hint = listOf("Don't", "Push", "My", "Buttons")
)

val button10 = ButtonItem(
    label = 10,
    color = colourDarkGreen,
    shape = RoundedCornerShape(0.dp),
    hint = listOf("Don't", "Push", "My", "Buttons")
)

val button11 = ButtonItem(
    label = 11,
    color = colourBlue,
    shape = GenericShape { size, _ ->
        addPath(createSmoothTrianglePath(size.minDimension))
    },
    hint = listOf("Don't", "Push", "My", "Buttons")
)

val button12 = ButtonItem(
    label = 12,
    color = colourGreen,
    shape = RoundedCornerShape(0.dp),
    hint = listOf("Don't", "Push", "My", "Buttons")
)
val button13 = ButtonItem(
    label = 13,
    color = colourTeal,
    shape = RoundedCornerShape(100.dp),
    hint = listOf("Don't", "Push", "My", "Buttons")
)

val button14 = ButtonItem(
    label = 14,
    color = colourBlue,
    shape = RoundedCornerShape(16.dp),
    hint = listOf("Don't", "Push", "My", "Buttons")
)

val button15 = ButtonItem(
    label = 15,
    color = colourYellow,
    shape = RoundedCornerShape(100.dp),
    hint = listOf("Don't", "Push", "My", "Buttons")
)

val button16 = ButtonItem(
    label = 16,
    color = colourRed,
    shape = GenericShape {size,_ ->
        addPath(createSmoothHexagonPath(size.minDimension))
    },
    hint = listOf("Don't", "Push", "My", "Buttons")
)
val button17 = ButtonItem(
    label = 17,
    color = colourGreen,
    shape = GenericShape {size,_ ->
        addPath(createSmoothTrianglePath(size.minDimension))
    },
    hint = listOf("Don't", "Push", "My", "Buttons")
)

val button18 = ButtonItem(
    label = 18,
    color = colourTeal,
    shape = GenericShape {size,_ ->
        addPath(createSmoothHexagonPath(size.minDimension))
    },
    hint = listOf("Don't", "Push", "My", "Buttons")
)

val button19 = ButtonItem(
    label = 19,
    color = colourRed,
    shape = RoundedCornerShape(0.dp),
    hint = listOf("Don't", "Push", "My", "Buttons")
)

val button20 = ButtonItem(
    label = 20,
    color = colourDarkGreen,
    shape = RoundedCornerShape(100.dp),
    hint = listOf("Don't", "Push", "My", "Buttons")
)
val button21 = ButtonItem(
    label = 21,
    color = colourRed,
    shape = RoundedCornerShape(100.dp),
    hint = listOf("Don't", "Push", "My", "Buttons")
)

val button22 = ButtonItem(
    label = 22,
    color = colourDarkGreen,
    shape = RoundedCornerShape(16.dp),
    hint = listOf("Don't", "Push", "My", "Buttons")
)

val button23 = ButtonItem(
    label = 23,
    color = colourYellow,
    shape = GenericShape {size,_ ->
        addPath(createSmoothHexagonPath(size.minDimension))
    },
    hint = listOf("Don't", "Push", "My", "Buttons")
)

val button24 = ButtonItem(
    label = 24,
    color = colourBlue,
    shape = GenericShape {size, _ ->
        addPath(createSmoothPentagonPath(size.minDimension))
    },
    hint = listOf("Don't", "Push", "My", "Buttons")
)
val button25 = ButtonItem(
    label = 25,
    color = colourYellow,
    shape = GenericShape {size,_ ->
        addPath(createSmoothHexagonPath(size.minDimension))
    },
    hint = listOf("Don't", "Push", "My", "Buttons")
)

val button26 = ButtonItem(
    label = 26,
    color = colourGreen,
    shape = GenericShape { size, _ ->
        addPath(createSmoothPentagonPath(size.minDimension))
    },
    hint = listOf("Don't", "Push", "My", "Buttons")
)

val button27 = ButtonItem(
    label = 27,
    color = colourRed,
    shape = RoundedCornerShape(100.dp),
    hint = listOf("Don't", "Push", "My", "Buttons")
)

val button28 = ButtonItem(
    label = 28,
    color = colourTeal,
    shape = GenericShape {size,_ ->
        addPath(createSmoothTrianglePath(size.minDimension))
    },
    hint = listOf("Don't", "Push", "My", "Buttons")
)
val button29 = ButtonItem(
    label = 29,
    color = colourRed,
    shape = GenericShape {size,_ ->
        addPath(createSmoothTrianglePath(size.minDimension))
    },
    hint = listOf("Don't", "Push", "My", "Buttons")
)

val button30 = ButtonItem(
    label = 30,
    color = colourBlue,
    shape = GenericShape {size,_ ->
        addPath(createSmoothHexagonPath(size.minDimension))
    },
    hint = listOf("Don't", "Push", "My", "Buttons")
)

val button31 = ButtonItem(
    label = 31,
    color = colourTeal,
    shape = RoundedCornerShape(16.dp),
    hint = listOf("Don't", "Push", "My", "Buttons")
)

val button32 = ButtonItem(
    label = 32,
    color = colourDarkGreen,
    shape = RoundedCornerShape(100.dp),
    hint = listOf("Don't", "Push", "My", "Buttons")
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
fun FixedGrid(
    counter: Int,
    onCounterChange: (Int) -> Unit,
    isGameRunning: Boolean,
    onGameRunningChange: (Boolean) -> Unit,
    gameOver: Boolean,
    onGameOverChange: (Boolean) -> Unit,
    currentHint: String?,
    onHintChange: (String?) -> Unit
) {
    var targetButtonId by remember { mutableStateOf(0) }
    var finalScore by remember { mutableStateOf(0) }
    var incorrectClicks by remember { mutableStateOf(0) }

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

    fun updateHint() {
        if (incorrectClicks % 5 == 0 && incorrectClicks > 0) {
            val hintIndex = (incorrectClicks / 5) - 1
            val targetButton = items.find { it.label == targetButtonId }
            targetButton?.let {
                if (hintIndex < it.hint.size) {
                    onHintChange(it.hint[hintIndex])
                }
            }
        }
    }

    fun startNewGame() {
        onGameRunningChange(true)
        onGameOverChange(false)
        onCounterChange(0)
        incorrectClicks = 0
        onHintChange(null)
        targetButtonId = (1..32).random()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        if (!isGameRunning && !gameOver) {
            Button(
                onClick = { startNewGame() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)

            ) {
                Text("Start Game")
            }
        } else if (gameOver) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),

                ) {
                Text(
                    text = "Game Over!",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "Final Score: $finalScore",
                    style = MaterialTheme.typography.headlineSmall
                )
                Button(
                    onClick = { startNewGame() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Play Again")
                }
            }
        } else {
Box(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                AppLogo(
                    modifier = Modifier
                        .height(100.dp)
                        .align(Alignment.CenterStart)

                )
    currentHint?.let { hint ->
        Text(
            text = "Hint: $hint",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(start = 110.dp)
                .fillMaxWidth()
        )
    }

}


            Box(
                modifier = Modifier.padding(top = 16.dp)
                    .fillMaxSize()
                    .padding(8.dp)

            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(items) { item ->
                        ButtonGridItem(
                            label = item.label,
                            color = item.color,
                            shape = item.shape,
                            onClickIncrement = {
                                onCounterChange(counter + 1)
                                if (item.label == targetButtonId) {
                                    finalScore = counter + 1
                                    onGameOverChange(true)
                                } else {
                                    incorrectClicks++
                                    updateHint()
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
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
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "App Logo",
        modifier = modifier
            .height(100.dp)
            .padding(vertical = 0.dp),
    )
}


fun createSmoothHexagonPath(size: Float): androidx.compose.ui.graphics.Path {
    val roundedPolygon = RoundedPolygon(
        numVertices = 6,
        radius = size / 1.9f,
        centerX = size / 2,
        centerY = size / 2,
        rounding = CornerRounding(size / 13f, smoothing = 0.1f)
    )
    return roundedPolygon.toPath().asComposePath()
}

fun createSmoothTrianglePath(size: Float): androidx.compose.ui.graphics.Path {
    val roundedTriangle = RoundedPolygon(
        numVertices = 3,
        radius = size / 1.6f,
        centerX = size / 2,
        centerY = size / 2,
        rounding = CornerRounding(size / 10f, smoothing = 0.1f)
    )

    return roundedTriangle.toPath().asComposePath()
}

fun createSmoothPentagonPath(size: Float): androidx.compose.ui.graphics.Path {
    val roundedPentagon = RoundedPolygon(
        numVertices = 5,
        radius = size / 1.7f,
        centerX = size / 2,
        centerY = size / 2,
        rounding = CornerRounding(size / 10f, smoothing = 0.1f)
    )

    return roundedPentagon.toPath().asComposePath()
}

