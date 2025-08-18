package SneakyButton

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.dontpushmybuttons.HomeActivity
import com.example.dontpushmybuttons.R
import com.example.dontpushmybuttons.ui.theme.DontPushMyButtonsTheme
import com.example.dontpushmybuttons.utils.ThemeManager
import com.example.dontpushmybuttons.utils.HighScoreManager
import utils.HapticFeedback
import utils.SoundManager
import kotlin.math.cos
import kotlin.math.sin

class FinalButton : ComponentActivity() {
    private lateinit var soundManager: SoundManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        soundManager = SoundManager(this)
        enableEdgeToEdge()

        setContent {
            val context = LocalContext.current
            val themeManager = remember { ThemeManager.getInstance(context) }
            val systemDarkTheme = isSystemInDarkTheme()
            var isDarkTheme by remember {
                mutableStateOf(themeManager.isDarkTheme(systemDarkTheme))
            }

            DontPushMyButtonsTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SneakyButtonGame(
                        isDarkTheme = isDarkTheme,
                        onThemeChange = { newTheme ->
                            isDarkTheme = newTheme
                            themeManager.setDarkTheme(newTheme)
                        },
                        soundManager = soundManager
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        soundManager.release()
    }
}

@Composable
fun SneakyButtonGame(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    soundManager: SoundManager
) {
    // Landing page state
    var isGameStarted by remember { mutableStateOf(false) }
    var showHowToDialog by remember { mutableStateOf(false) }
    var showHighScoresDialog by remember { mutableStateOf(false) }

    if (!isGameStarted) {
        // Landing Page
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header with theme switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isDarkTheme) "Dark" else "Light",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = onThemeChange
                )
            }

            Spacer(modifier = Modifier.weight(0.3f))

            // Logo - using SneakyButton logo
            AppLogo(modifier = Modifier.size(360.dp))

            Button(
                onClick = { isGameStarted = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Start Game",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // How To button - CONSISTENT SIZING with other games
            Button(
                onClick = { showHowToDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    text = "How To Play",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // High Scores button
            Button(
                onClick = { showHighScoresDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text(
                    text = "High Scores",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.weight(0.2f))
        }

        // How To Dialog
        if (showHowToDialog) {
            AlertDialog(
                onDismissRequest = { showHowToDialog = false },
                title = {
                    Text(
                        text = "How to Play Sneaky Button",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        text = "1. Click all the buttons to make them disappear\n\n" +
                                "2. The last remaining button will move around\n\n" +
                                "3. Catch the sneaky button to win!\n\n" +
                                "4. Try to clear all buttons as quickly as possible\n\n" +
                                "5. Enjoy the fireworks when you succeed!",
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                confirmButton = {
                    Button(
                        onClick = { showHowToDialog = false }
                    ) {
                        Text("Got it!")
                    }
                }
            )
        }

        // High Scores Dialog
        if (showHighScoresDialog) {
            val context = LocalContext.current
            val highScoreManager = remember { HighScoreManager.getInstance(context) }

            AlertDialog(
                onDismissRequest = { showHighScoresDialog = false },
                title = {
                    Text(
                        text = "🏆 High Scores",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Column {
                        Text(
                            text = "Sneaky Button",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        val highScore = highScoreManager.getSneakyButtonHighScore()
                        if (highScore > 0) {
                            Text(
                                text = "Best Score: $highScore points",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "Higher scores are better!",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        } else {
                            Text(
                                text = "No games played yet!",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "Play a game to set your first high score.",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = { showHighScoresDialog = false }
                    ) {
                        Text("Close")
                    }
                }
            )
        }
    } else {
        // Game Screen
        FinalButtonScreen(
            isDarkTheme = isDarkTheme,
            onThemeChange = onThemeChange,
            soundManager = soundManager
        )
    }
}

@Composable
fun FinalButtonScreen(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    soundManager: SoundManager
) {
    val context = LocalContext.current
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

    // SassySwitches color palette - matching the exact colors
    val colourRed = Color(0xFFF45B69)
    val colourGreen = Color(0xFF92EF80)
    val colourYellow = Color(0xFFFFBE0B)
    val colourTeal = Color(0xFF028090)
    val colourBlue = Color(0xFF9CFFFA)
    val colourDarkGreen = Color(0xFF137547)

    val colors = listOf(
        colourRed,
        colourGreen,
        colourYellow,
        colourTeal,
        colourBlue,
        colourDarkGreen
    )

    // Randomize colors for each button position (remember to avoid recomposition)
    val randomizedColors = remember {
        List(gridSize) { colors.random() }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Main content with header
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header with theme switch and logo (matching SassySwitches pattern)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppLogo(
                    modifier = Modifier.size(120.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isDarkTheme) "Dark" else "Light",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = onThemeChange
                    )
                }
            }

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
                            val color = randomizedColors[index]
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

                                val currentContext = context // Capture context in a local variable

                                Button(
                                    onClick = {
                                        HapticFeedback.performHapticFeedback(currentContext)
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
        }

        // Success overlay
        if (showSuccess) {
            SuccessOverlay()
        }
    }
}

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Image(
        painter = painterResource(id = R.drawable.sneakybutton),
        contentDescription = "Sneaky Button Logo",
        modifier = modifier
            .clickable {
                val intent = Intent(context, HomeActivity::class.java)
                context.startActivity(intent)
            }
    )
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