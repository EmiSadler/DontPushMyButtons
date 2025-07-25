package FinalButton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class FinalButton : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinalButtonScreen()
        }
    }
}

@Composable
fun FinalButtonScreen() {
    val gridSize = 5 * 5
    var buttons by remember { mutableStateOf(List(gridSize) { true }) }
    val remainingButtons = buttons.count { it }
    var showSuccess by remember { mutableStateOf(false) }

    // Random position for the last button
    var randomRow by remember { mutableStateOf(0) }
    var randomCol by remember { mutableStateOf(0) }

    // Trigger position change when there's only one button left
    LaunchedEffect(remainingButtons) {
        if (remainingButtons == 1) {
            while (buttons.count { it } == 1) {
                kotlinx.coroutines.delay(800)
                randomRow = (0..4).random()
                randomCol = (0..4).random()
            }
        }
        if (remainingButtons == 0) {
            showSuccess = true
        }
    }

    // Sassy Switches color palette
    val colors = listOf(
        Color(0xFFB388FF), // Purple
        Color(0xFF8C9EFF), // Indigo
        Color(0xFF80D8FF), // Light Blue
        Color(0xFFA7FFEB), // Teal
        Color(0xFFFF8A80)  // Red
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Game content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            repeat(5) { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    repeat(5) { col ->
                        val index = row * 5 + col
                        val color = colors[index % colors.size]
                        val shape = if ((index + 1) % 2 == 1) CircleShape else RoundedCornerShape(8.dp)

                        // Show button logic
                        val shouldShowButton = if (remainingButtons == 1) {
                            val lastButtonIndex = buttons.indexOfFirst { it }
                            row == randomRow && col == randomCol && lastButtonIndex != -1
                        } else {
                            buttons[index]
                        }

                        if (shouldShowButton) {
                            val buttonIndex = if (remainingButtons == 1) {
                                buttons.indexOfFirst { it }
                            } else {
                                index
                            }

                            Button(
                                onClick = {
                                    buttons = buttons.toMutableList().also { it[buttonIndex] = false }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = color),
                                shape = shape,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                            ) {
                                Text("${buttonIndex + 1}")
                            }
                        } else {
                            Spacer(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                            )
                        }
                    }
                }
            }
        }

        // Success overlay
        if (showSuccess) {
            SuccessOverlay()
        }
    }
}

@Composable
fun SuccessOverlay() {
    val context = LocalContext.current
    val infiniteTransition = rememberInfiniteTransition(label = "fireworks")

    val fireworkProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "firework_progress"
    )

    val textScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "text_scale"
    )

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Firework animation
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawFireworks(fireworkProgress, size.width, size.height)
        }

        // Success text
        Card(
            modifier = Modifier
                .padding(32.dp)
                .clickable {
                    (context as? ComponentActivity)?.finish()
                },
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF4CAF50).copy(alpha = 0.9f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "Success!",
                    fontSize = (48 * textScale).sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "Tap to return home",
                    fontSize = 16.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

fun DrawScope.drawFireworks(progress: Float, screenWidth: Float, screenHeight: Float) {
    val fireworkColors = listOf(
        Color(0xFFFF6B6B), Color(0xFF4ECDC4), Color(0xFF45B7D1),
        Color(0xFFFFA07A), Color(0xFF98D8C8), Color(0xFFFFD93D)
    )

    // Generate multiple firework bursts
    repeat(8) { fireworkIndex ->
        val centerX = screenWidth * (0.2f + (fireworkIndex % 3) * 0.3f)
        val centerY = screenHeight * (0.3f + (fireworkIndex % 2) * 0.4f)
        val delay = fireworkIndex * 0.2f
        val adjustedProgress = ((progress - delay).coerceAtLeast(0f) * 1.5f).coerceAtMost(1f)

        if (adjustedProgress > 0f) {
            // Draw radiating particles
            repeat(12) { particleIndex ->
                val angle = (particleIndex * 30f) * (Math.PI / 180f)
                val maxRadius = 150f
                val radius = maxRadius * adjustedProgress
                val alpha = (1f - adjustedProgress).coerceAtLeast(0f)

                val x = centerX + cos(angle).toFloat() * radius
                val y = centerY + sin(angle).toFloat() * radius

                drawCircle(
                    color = fireworkColors[fireworkIndex % fireworkColors.size].copy(alpha = alpha),
                    radius = (8f * (1f - adjustedProgress * 0.5f)).coerceAtLeast(2f),
                    center = androidx.compose.ui.geometry.Offset(x, y)
                )
            }
        }
    }
}