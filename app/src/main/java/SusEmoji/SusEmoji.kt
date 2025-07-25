package SusEmoji

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dontpushmybuttons.HomeActivity
import com.example.dontpushmybuttons.R
import com.example.dontpushmybuttons.ui.theme.DontPushMyButtonsTheme

class SusEmoji : ComponentActivity() {
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
                    SusEmojiGame(
                        isDarkTheme = isDarkTheme,
                        onThemeChange = { isDarkTheme = it }
                    )
                }
            }
        }
    }
}

@Composable
fun SusEmojiGame(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    // Game state
    var currentClue by remember { mutableStateOf("") }
    var secretEmoji by remember { mutableStateOf("") }
    var selectedEmojis by remember { mutableStateOf(setOf<String>()) }

    // Full collection of diverse emojis paired with clues
    val allEmojiClues = remember {
        listOf(
            // Fruits & Food
            "🍎" to "A fruit that keeps the doctor away",
            "🍌" to "Yellow fruit that monkeys love",
            "🍊" to "Orange citrus fruit",
            "🍇" to "Purple clusters used to make wine",
            "🍓" to "Red berry with seeds on the outside",
            "🥝" to "Fuzzy brown fruit with green inside",
            "🍑" to "Sweet stone fruit, often in pairs",
            "🍒" to "Small red fruit with stems",
            "🥭" to "Tropical fruit with orange flesh",
            "🍍" to "Spiky tropical fruit with a crown",
            "🥥" to "Hard shell fruit with white meat inside",
            "🍅" to "Red fruit often mistaken for a vegetable",
            "🍆" to "Purple vegetable used in Mediterranean cooking",
            "🥑" to "Green fruit used to make guacamole",
            "🌶️" to "Spicy red or green pod",
            "🌽" to "Yellow vegetable that pops when heated",
            "🥕" to "Orange root vegetable, good for eyes",
            "🥒" to "Green vegetable used in pickles",
            "🥬" to "Leafy green vegetable for salads",
            "🥦" to "Green tree-like vegetable",
            "🍄" to "Fungi that grows in the forest",
            "🥜" to "Small brown nut good for protein",
            "🌰" to "Spiky brown nut that falls in autumn",
            "🍞" to "Baked good made from flour",
            "🥐" to "French crescent-shaped pastry",
            "🥖" to "Long French bread",
            "🥨" to "Twisted baked snack with salt",
            "🥯" to "Round bread with a hole",
            "🥞" to "Stack of flat cakes for breakfast",
            "🧇" to "Square breakfast food with holes",

            // Animals
            "🐶" to "Man's best friend",
            "🐱" to "Feline pet that purrs",
            "🐭" to "Small rodent that likes cheese",
            "🐹" to "Small pet that stores food in cheeks",
            "🐰" to "Fluffy animal that hops and eats carrots",
            "🦊" to "Clever orange animal with a bushy tail",
            "🐻" to "Large furry animal that hibernates",
            "🐼" to "Black and white bear from China",
            "🐨" to "Australian marsupial that eats eucalyptus",
            "🐯" to "Large striped cat from Asia",
            "🦁" to "King of the jungle with a mane",
            "🐮" to "Farm animal that gives milk",
            "🐷" to "Pink farm animal that rolls in mud",
            "🐸" to "Green amphibian that says ribbit",
            "🐵" to "Primate that swings from trees",
            "🙈" to "See no evil monkey",
            "🙉" to "Hear no evil monkey",
            "🙊" to "Speak no evil monkey",
            "🐒" to "Playful primate with a long tail",
            "🐔" to "Farm bird that lays eggs",
            "🐧" to "Black and white bird from Antarctica",
            "🐦" to "Flying animal with feathers",
            "🐤" to "Baby bird just hatched",
            "🐣" to "Bird breaking out of its shell",
            "🐥" to "Yellow baby duck or chick",
            "🦆" to "Water bird that quacks",
            "🦅" to "Large bird of prey with sharp talons",
            "🦉" to "Nocturnal bird that hoots",
            "🦇" to "Flying mammal that hangs upside down",
            "🐺" to "Wild dog that howls at the moon",

            // Transportation
            "🚗" to "Four-wheeled vehicle for personal transport",
            "🚕" to "Yellow car for hire",
            "🚙" to "SUV or recreational vehicle",
            "🚌" to "Large vehicle for public transport",
            "🚎" to "Electric bus with overhead wires",
            "🏎️" to "Fast racing car",
            "🚓" to "Law enforcement vehicle",
            "🚑" to "Emergency medical vehicle",
            "🚒" to "Red truck that fights fires",
            "🚐" to "Small van for passengers",
            "🛻" to "Pickup truck with open back",
            "🚚" to "Large vehicle for moving goods",
            "🚛" to "Semi-truck for long hauls",
            "🚜" to "Farm vehicle for plowing",
            "🏍️" to "Two-wheeled motorized vehicle",
            "🛵" to "Small scooter for city travel",
            "🚲" to "Two-wheeled vehicle powered by pedals",
            "🛴" to "Standing scooter you push with foot",
            "🛹" to "Board with wheels for tricks",
            "🚁" to "Flying vehicle with spinning blades",
            "✈️" to "Flying machine with wings",
            "🚀" to "Vehicle that goes to space",
            "🛸" to "Alien spacecraft",
            "🚢" to "Large vessel that sails the ocean",
            "⛵" to "Wind-powered boat",
            "🚤" to "Fast motorboat",
            "⛴️" to "Large passenger ship",
            "🛥️" to "Luxury motor yacht",
            "🚂" to "Steam locomotive",
            "🚆" to "High-speed passenger train",

            // Nature & Weather
            "☀️" to "Bright star that gives us daylight",
            "🌙" to "Celestial body that lights the night",
            "⭐" to "Twinkling light in the night sky",
            "🌟" to "Bright shining star",
            "💫" to "Shooting star across the sky",
            "☁️" to "White fluffy thing in the sky",
            "⛅" to "Mix of sun and clouds",
            "🌤️" to "Sun peeking through clouds",
            "🌦️" to "Sun and rain at the same time",
            "🌧️" to "Water falling from the sky",
            "⛈️" to "Storm with lightning and thunder",
            "🌩️" to "Electric flash in the sky",
            "❄️" to "Frozen water crystal",
            "☃️" to "Winter figure made of snow",
            "⛄" to "Classic snowman with carrot nose",
            "🌈" to "Colorful arc after rain",
            "🔥" to "Hot orange and red element",
            "💧" to "Single drop of water",
            "🌊" to "Large ocean wave",
            "🌍" to "",
            "🌎" to "",
            "🌏" to "",
            "🌑" to "",
            "🌒" to "",
            "🌓" to "",
            "🌔" to "",
            "🌕" to "",
            "🌖" to "",
            "🌗" to "",
            "🌘" to "",

            // Objects & Tools
            "⚽" to "Black and white ball kicked with feet",
            "🏀" to "Orange ball bounced and shot through hoops",
            "🏈" to "Brown oval ball thrown in American sport",
            "⚾" to "White ball with red stitches",
            "🎾" to "Yellow fuzzy ball hit with rackets",
            "🏐" to "White ball hit over a net",
            "🏉" to "Oval ball used in rugby",
            "🎱" to "Black ball with number 8",
            "🏓" to "Small white ball for table tennis",
            "🏸" to "Shuttlecock sport with rackets",
            "🥅" to "",
            "⛳" to "",
            "🎯" to "",
            "🏹" to "",
            "🎣" to "",
            "🥊" to "",
            "🥋" to "",
            "🎽" to "",
            "🛹" to "",
            "🛷" to "",
            "⛸️" to "",
            "🥌" to "",
            "🎿" to "",
            "⛷️" to "",
            "🏂" to "",
            "🪀" to "",
            "🎭" to "",
            "🎨" to "",
            "🎬" to "",
            "🎤" to "",

            // Hearts & Symbols
            "❤️" to "Symbol of love and affection",
            "🧡" to "Heart in the color of autumn leaves",
            "💛" to "Heart in the color of sunshine",
            "💚" to "Heart in the color of grass",
            "💙" to "Heart in the color of the sky",
            "💜" to "Heart in the color of royalty",
            "🖤" to "Heart in the darkest color",
            "🤍" to "Heart in the purest color",
            "🤎" to "Heart in the color of chocolate",
            "💔" to "Broken symbol of love",

            // Faces & People
            "😀" to "",
            "😃" to "",
            "😄" to "",
            "😁" to "",
            "😆" to "",
            "😅" to "",
            "🤣" to "",
            "😂" to "",
            "🙂" to "",
            "🙃" to "",
            "😉" to "",
            "😊" to "",
            "😇" to "",
            "🥰" to "",
            "😍" to "",
            "🤩" to "",
            "😘" to "",
            "😗" to "",
            "☺️" to "",
            "😚" to "",
            "😙" to "",
            "🥲" to "",
            "😋" to "",
            "😛" to "",
            "😜" to "",
            "🤪" to "",
            "😝" to "",
            "🤑" to "",
            "🤗" to "",
            "🤭" to "",

            // Fantasy & Magic
            "🦄" to "",
            "🐉" to "",
            "🧚" to "",
            "🧙" to "",
            "🧛" to "",
            "🧟" to "",
            "🧞" to "",
            "🧜" to "",
            "🧝" to "",
            "🎃" to "",
            "👻" to "",
            "💀" to "",
            "☠️" to "",
            "👽" to "",
            "👾" to "",
            "🤖" to "",
            "🔮" to "",
            "🪄" to "",
            "⚡" to "",
            "💥" to "",

            // Tools & Objects
            "🔨" to "",
            "🔧" to "",
            "⚙️" to "",
            "🧰" to "",
            "🔩" to "",
            "⛏️" to "",
            "🪓" to "",
            "🔪" to "",
            "🗡️" to "",
            "🛡️" to "",
            "🏺" to "",
            "🧭" to "",
            "🧲" to "",
            "💎" to "",
            "⚖️" to "",
            "🔗" to "",
            "⛓️" to "",
            "🧯" to "",
            "🛒" to "",
            "🎁" to ""
        )
    }

    // Randomly select 104 emoji-clue pairs for this game round (8x13 grid)
    val gameEmojiClues = remember {
        allEmojiClues.filter { it.second.isNotEmpty() }.shuffled().take(104)
    }

    // Select a secret emoji and its clue for this round
    val (currentSecretEmoji, currentSecretClue) = remember {
        val secretPair = gameEmojiClues.random()
        secretEmoji = secretPair.first
        currentClue = secretPair.second
        secretPair
    }

    // Extract just the emojis for display
    val gameEmojis = gameEmojiClues.map { it.first }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header with logo and theme toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppLogo(
                modifier = Modifier.size(60.dp)
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

        // Clue box
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🔍 CLUE",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = currentClue,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                // Show selected emojis
                if (selectedEmojis.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Selected: ${selectedEmojis.joinToString(" ")}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        // Emoji grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(8), // 8 columns for good emoji density
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(gameEmojis) { emoji ->
                EmojiButton(
                    emoji = emoji,
                    isSelected = selectedEmojis.contains(emoji),
                    onClick = {
                        selectedEmojis = if (selectedEmojis.contains(emoji)) {
                            selectedEmojis - emoji
                        } else {
                            selectedEmojis + emoji
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun EmojiButton(
    emoji: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                } else {
                    MaterialTheme.colorScheme.surface
                },
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )

        // Selection indicator
        if (isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    )
            )
        }
    }
}

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "App Logo",
        modifier = modifier
            .clickable {
                val intent = Intent(context, HomeActivity::class.java)
                context.startActivity(intent)
            }
    )
}