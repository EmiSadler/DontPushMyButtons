package SassySwitches
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dontpushmybuttons.HomeActivity
import com.example.dontpushmybuttons.R
import com.example.dontpushmybuttons.ui.theme.DontPushMyButtonsTheme

class SassySwitches : ComponentActivity() {
    private lateinit var soundManager: SoundManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        soundManager = SoundManager(this)
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
                    SassySwitchesGame(
                        isDarkTheme = isDarkTheme,
                        onThemeChange = { isDarkTheme = it },
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
fun SassySwitchesGame(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    soundManager: SoundManager
) {
    // Landing page state
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

            // Logo - using consistent 180dp size like other games
            AppLogo(modifier = Modifier.size(360.dp))

            // Start Game button - CONSISTENT SIZING with other games
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

            Spacer(modifier = Modifier.weight(0.2f))
        }

        // How To Dialog
        if (showHowToDialog) {
            AlertDialog(
                onDismissRequest = { showHowToDialog = false },
                title = {
                    Text(
                        text = "How to Play Sassy Switches",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        text = "1. Find the correct button based on the clue\n\n" +
                                "2. Each button has unique properties to help you identify it\n\n" +
                                "3. Click the button that matches the description\n\n" +
                                "4. Get hints if you make too many wrong clicks\n\n" +
                                "5. Try to find the target with as few clicks as possible!",
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
        GameScreen(
            isDarkTheme = isDarkTheme,
            onThemeChange = onThemeChange,
            soundManager = soundManager
        )
    }
}

@Composable
fun GameScreen(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    soundManager: SoundManager
) {
    var counter by remember { mutableStateOf(0) }
    var isGameRunning by remember { mutableStateOf(false) }
    var gameOver by remember { mutableStateOf(false) }
    var currentHint by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        // Header with SassySwitches logo and theme toggle - ADDED LOGO HERE
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // SassySwitches logo on game page
            AppLogo(modifier = Modifier.size(120.dp))

            if (isGameRunning && !gameOver) {
                Text(
                    text = "Count: $counter",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    checked = isDarkTheme,
                    onCheckedChange = onThemeChange
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
            onHintChange = { currentHint = it },
            onButtonClick = { isCorrect ->
                if (isCorrect) {
                    soundManager.playCorrectButtonSound()
                } else {
                    soundManager.playButtonClickSound()
                }
            }
        )
    }
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
    onHintChange: (String?) -> Unit,
    onButtonClick: (Boolean) -> Unit
) {
    var targetButtonId by remember { mutableStateOf(0) }
    var finalScore by remember { mutableStateOf(0) }
    var incorrectClicks by remember { mutableStateOf(0) }

    val items: List<ButtonItem> = listOf(
        button1, button2, button3, button4, button5, button6, button7, button8,
        button9, button10, button11, button12, button13, button14, button15, button16,
        button17, button18, button19, button20, button21, button22, button23, button24,
        button25, button26, button27, button28, button29, button30, button31, button32
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
        if (gameOver) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AppLogo(modifier = Modifier.size(120.dp))

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Ok! Ok! You found me!",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Now stop pushing my buttons!",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "Final Score: $finalScore",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = { startNewGame() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Text("Play Again")
                }
            }
        } else {
            // Game screen
            Box(modifier = Modifier.fillMaxWidth()) {
                currentHint?.let { hint ->
                    Text(
                        text = "Hint: $hint",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

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
                            val isCorrectButton = item.label == targetButtonId
                            onButtonClick(isCorrectButton)

                            if (isCorrectButton) {
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
    val context = LocalContext.current

    Image(
        painter = painterResource(id = R.drawable.sassyswitches),
        contentDescription = "SassySwitches Logo",
        modifier = modifier
            .clickable {
                val intent = Intent(context, HomeActivity::class.java)
                context.startActivity(intent)
            }
    )
}
