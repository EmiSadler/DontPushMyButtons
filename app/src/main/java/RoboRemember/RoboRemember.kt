package RoboRemember

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.AlertDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.dontpushmybuttons.HomeActivity
import com.example.dontpushmybuttons.R
import com.example.dontpushmybuttons.ui.theme.DontPushMyButtonsTheme
import kotlinx.coroutines.delay

class RoboRemember : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val systemDarkTheme = isSystemInDarkTheme()
            var isDarkTheme by rememberSaveable {
                mutableStateOf(systemDarkTheme)
            }

            DontPushMyButtonsTheme(darkTheme = isDarkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RoboRememberGame(
                        isDarkTheme = isDarkTheme,
                        onThemeChange = { isDarkTheme = it }
                    )
                }
            }
        }
    }
}

enum class GameState {
    READY, SHOWING_SEQUENCE, WAITING_FOR_INPUT, GAME_OVER
}

enum class ButtonColor(val normal: Color, val lit: Color) {
    RED(Color(0xFFE53E3E), Color(0xFFFF6B6B)),
    GREEN(Color(0xFF38A169), Color(0xFF68D391)),
    BLUE(Color(0xFF3182CE), Color(0xFF63B3ED)),
    YELLOW(Color(0xFFD69E2E), Color(0xFFF6E05E))
}

@Composable
fun RoboRememberGame(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    var gameState by remember { mutableStateOf(GameState.READY) }
    var score by remember { mutableIntStateOf(0) }
    var currentSequence by remember { mutableStateOf(listOf<Int>()) }
    var playerInput by remember { mutableStateOf(listOf<Int>()) }
    var currentlyLit by remember { mutableIntStateOf(-1) }
    var showingIndex by remember { mutableIntStateOf(0) }
    var isGameStarted by remember { mutableStateOf(false) }
    var showHowToDialog by remember { mutableStateOf(false) }

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

            // Logo
            AppLogo(modifier = Modifier.size(360.dp))

            // Start Game button
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

            // How To button
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

            Spacer(modifier = Modifier.weight(0.2f))
        }

        // How To Dialog
        if (showHowToDialog) {
            AlertDialog(
                onDismissRequest = { showHowToDialog = false },
                title = {
                    Text(
                        text = "How to Play Robo Remember",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        text = "1. Watch as the buttons light up in sequence\n\n" +
                                "2. Repeat the sequence by tapping the buttons in the same order\n\n" +
                                "3. Each round adds one more button to remember\n\n" +
                                "4. If you make a mistake, the game ends\n\n" +
                                "5. Try to get the highest score possible!",
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
    } else {
        // Game Screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header with theme switch and logo
            Row(
                modifier = Modifier.fillMaxWidth(),
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

            Spacer(modifier = Modifier.weight(0.3f))

            // Game Title
            Text(
                text = "Robo Remember",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Score display
            if (gameState != GameState.READY) {
                Text(
                    text = "Score: $score",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Game status text
            val statusText = when (gameState) {
                GameState.READY -> "Tap 'Start Game' to begin!"
                GameState.SHOWING_SEQUENCE -> "Watch the sequence..."
                GameState.WAITING_FOR_INPUT -> "Repeat the sequence!"
                GameState.GAME_OVER -> "Game Over! Final Score: $score"
            }

            Text(
                text = statusText,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Game buttons grid (2x2) - Made larger and better centered
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    SimonButton(
                        buttonId = 0,
                        color = ButtonColor.RED,
                        isLit = currentlyLit == 0,
                        isEnabled = gameState == GameState.WAITING_FOR_INPUT,
                        onClick = {
                            handleButtonClick(0, gameState, currentSequence, playerInput, score) { newState, newPlayerInput, newScore, newSequence ->
                                gameState = newState
                                playerInput = newPlayerInput
                                score = newScore
                                currentSequence = newSequence
                            }
                        }
                    )
                    SimonButton(
                        buttonId = 1,
                        color = ButtonColor.GREEN,
                        isLit = currentlyLit == 1,
                        isEnabled = gameState == GameState.WAITING_FOR_INPUT,
                        onClick = {
                            handleButtonClick(1, gameState, currentSequence, playerInput, score) { newState, newPlayerInput, newScore, newSequence ->
                                gameState = newState
                                playerInput = newPlayerInput
                                score = newScore
                                currentSequence = newSequence
                            }
                        }
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    SimonButton(
                        buttonId = 2,
                        color = ButtonColor.BLUE,
                        isLit = currentlyLit == 2,
                        isEnabled = gameState == GameState.WAITING_FOR_INPUT,
                        onClick = {
                            handleButtonClick(2, gameState, currentSequence, playerInput, score) { newState, newPlayerInput, newScore, newSequence ->
                                gameState = newState
                                playerInput = newPlayerInput
                                score = newScore
                                currentSequence = newSequence
                            }
                        }
                    )
                    SimonButton(
                        buttonId = 3,
                        color = ButtonColor.YELLOW,
                        isLit = currentlyLit == 3,
                        isEnabled = gameState == GameState.WAITING_FOR_INPUT,
                        onClick = {
                            handleButtonClick(3, gameState, currentSequence, playerInput, score) { newState, newPlayerInput, newScore, newSequence ->
                                gameState = newState
                                playerInput = newPlayerInput
                                score = newScore
                                currentSequence = newSequence
                            }
                        }
                    )
                }
            }

            // Add weight to center vertically and push Start button to bottom
            Spacer(modifier = Modifier.weight(0.4f))

            // Start/Restart button
            Button(
                onClick = {
                    // Start new game
                    currentSequence = listOf((0..3).random())
                    playerInput = emptyList()
                    score = 0
                    gameState = GameState.SHOWING_SEQUENCE
                    showingIndex = 0
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = if (gameState == GameState.READY || gameState == GameState.GAME_OVER) "Start Game" else "Restart",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Handle sequence showing
        LaunchedEffect(gameState, showingIndex, currentSequence) {
            if (gameState == GameState.SHOWING_SEQUENCE) {
                if (showingIndex < currentSequence.size) {
                    delay(600) // Pause between buttons
                    currentlyLit = currentSequence[showingIndex]
                    delay(600) // Keep button lit
                    currentlyLit = -1
                    delay(200) // Brief pause
                    showingIndex++
                } else {
                    // Sequence shown, wait for player input
                    gameState = GameState.WAITING_FOR_INPUT
                    showingIndex = 0
                }
            }
        }
    }
}

@Composable
fun SimonButton(
    buttonId: Int,
    color: ButtonColor,
    isLit: Boolean,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .size(160.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (isLit) color.lit else color.normal)
            .clickable(enabled = isEnabled) {
                // Add haptic feedback when button is pressed during input
                if (isEnabled) {
                    performHapticFeedback(context)
                }
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        // Button number text
        Text(
            text = "${buttonId + 1}",
            color = Color.White,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Image(
        painter = painterResource(id = R.drawable.roboremember),
        contentDescription = "RoboRemember Logo",
        modifier = modifier
            .clickable {
                val intent = Intent(context, HomeActivity::class.java)
                context.startActivity(intent)
            }
    )
}

// Helper function to perform haptic feedback
fun performHapticFeedback(context: Context) {
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Modern haptic feedback with more control (50ms vibration)
        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        // Legacy vibration for older devices
        @Suppress("DEPRECATION")
        vibrator.vibrate(50)
    }
}

fun handleButtonClick(
    buttonId: Int,
    gameState: GameState,
    currentSequence: List<Int>,
    playerInput: List<Int>,
    score: Int,
    onStateChange: (GameState, List<Int>, Int, List<Int>) -> Unit
) {
    if (gameState != GameState.WAITING_FOR_INPUT) return

    val newPlayerInput = playerInput + buttonId

    // Check if this button is correct
    if (buttonId != currentSequence[playerInput.size]) {
        // Wrong button - game over
        onStateChange(GameState.GAME_OVER, emptyList(), score, currentSequence)
        return
    }

    // Check if sequence is complete
    if (newPlayerInput.size == currentSequence.size) {
        // Sequence complete - add new button and show next sequence
        val newScore = score + 1
        val newSequence = currentSequence + (0..3).random()
        onStateChange(GameState.SHOWING_SEQUENCE, emptyList(), newScore, newSequence)
    } else {
        // Continue with current sequence
        onStateChange(GameState.WAITING_FOR_INPUT, newPlayerInput, score, currentSequence)
    }
}
