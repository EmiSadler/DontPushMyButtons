// Fixed version of the FixedGrid function for SassySwitches.kt
// Replace the existing FixedGrid function with this corrected version

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
        // Fixed: Use when statement to trigger hints at exactly 5, 10, 15, and 20 incorrect clicks
        when (incorrectClicks) {
            5, 10, 15, 20 -> {
                val hintIndex = (incorrectClicks / 5) - 1
                val targetButton = items.find { it.label == targetButtonId }
                targetButton?.let {
                    if (hintIndex < it.hint.size) {
                        onHintChange(it.hint[hintIndex])
                    }
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
        targetButtonId = (1..32).random() // This will now properly select a target button
    }

    // FIXED: Initialize the game when first loaded
    LaunchedEffect(Unit) {
        if (!isGameRunning && !gameOver && targetButtonId == 0) {
            startNewGame()
        }
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
            // IMPROVED: Better hint display with Card styling
            currentHint?.let { hint ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = "💡 Hint: $hint",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
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
                                updateHint() // This will now properly trigger hints
                            }
                        }
                    )
                }
            }
        }
    }
}
