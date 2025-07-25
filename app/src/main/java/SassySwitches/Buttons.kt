package SassySwitches
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt

class ButtonItem(
    val label: Int,
    val color: Color,
    val shape: Shape,
    val hint: List<String>
)

val String.color
    get() = Color(this.toColorInt())

val colourRed = "#F45B69".color
val colourGreen = "#92EF80".color
val colourYellow = "#FFBE0B".color
val colourTeal = "#028090".color
val colourBlue = "#9CFFFA".color
val colourDarkGreen = "#137547".color

val button1 = ButtonItem(
    label = 1,
    shape = GenericShape { size, _ ->
        addPath(createSmoothTrianglePath(size.maxDimension))
    },
    color = colourYellow,
    hint = listOf("I'm the best button!", "I'm a playful button", "I'm the color of the inside of an egg (but which part of the egg?", "How have you not gotten it yet?")

)

val button2 = ButtonItem(
    label = 2,
    shape = GenericShape { size, _ ->
        addPath(createSmoothHexagonPath(size.minDimension))
    },
    color = colourGreen,
    hint = listOf("I'm calming to look at", "six! six! six! The number of the beast!", "It's not easy being green", "Picture the inside of a cucumber, then choose the correct button and pretend you didn't need four hints")
)

val button3 = ButtonItem(
    label = 3,
    color = colourRed,
    shape = RoundedCornerShape(0.dp),
    hint = listOf("I'm not on the bottom row", "I'm thinking about something embarrassing", "I'll cut you", "I'm like the brick wall that stands between you and your future")
)

val button4 = ButtonItem(
    label = 4,
    color = colourDarkGreen,
    shape = GenericShape { size, _ ->
        addPath(createSmoothPentagonPath(size.minDimension))
    },
    hint = listOf("I've got the makings of a star", "My bottom is not flat (no matter what the tabloids say)", "I am the color of snot. Discuss", "You've had enough hints for now")
)
val button5 = ButtonItem(
    label = 5,
    color = colourBlue,
    shape = RoundedCornerShape(100.dp),
    hint = listOf("They see me rolling", "I'm smooth", "My friends call me snowball", "I am a button")
)

val button6 = ButtonItem(
    label = 6,
    color = colourYellow,
    shape = RoundedCornerShape(16.dp),
    hint = listOf("I only have four friends", "I'm feeling boxed in to my career", "six! six! six! The number of the beast!", "I don't think you actually understand how to play this game")
)

val button7 = ButtonItem(
    label = 7,
    color = colourTeal,
    shape = RoundedCornerShape(100.dp),
    hint = listOf("I am a perfect button", "I am smooth like a koala's brain", "Imagine if the earth flooded", "Or, imagine what it would be like to actually have logical thinking skills")
)

val button8 = ButtonItem(
    label = 8,
    color = colourBlue,
    shape = GenericShape { size, _ ->
        addPath(createSmoothHexagonPath(size.minDimension))
    },
    hint = listOf("I wouldn't look out of place on the giant's causeway", "Push", "My", "Buttons")
)
val button9 = ButtonItem(
    label = 9,
    color = colourRed,
    shape = GenericShape { size, _ ->
        addPath(createSmoothPentagonPath(size.minDimension))
    },
    hint = listOf("If you get a rash this colour maybe buy some E45", "If you looked inside me you'd find a scary story", "You need security clearance to access me", "I will slap you if you don't get this right")
)

val button10 = ButtonItem(
    label = 10,
    color = colourDarkGreen,
    shape = RoundedCornerShape(0.dp),
    hint = listOf("It's not easy being green", "Statistically I am the safest age to be", "I have a powerful energy", "I disapprove of you just as your father disapproves of you")
)

val button11 = ButtonItem(
    label = 11,
    color = colourBlue,
    shape = GenericShape { size, _ ->
        addPath(createSmoothTrianglePath(size.minDimension))
    },
    hint = listOf("Descartes was very keen on me", "Three is my favourite number for more than one reason", "I have a similar vibe to an icicle", "In the US about 15 people are killed by icicles each year")
)

val button12 = ButtonItem(
    label = 12,
    color = colourGreen,
    shape = RoundedCornerShape(0.dp),
    hint = listOf("I am the color of snot. Discuss", "Mind me corners or you'll have your eye out", "My friend is pointing at me", "Seriously? How much help do you need? How do you ever achieve anything in your life?")
)
val button13 = ButtonItem(
    label = 13,
    color = colourTeal,
    shape = RoundedCornerShape(100.dp),
    hint = listOf("Click the blue button", "Not that blue button", "I'm button number 28", "Can't believe you fell for that!")
)

val button14 = ButtonItem(
    label = 14,
    color = colourBlue,
    shape = RoundedCornerShape(16.dp),
    hint = listOf("Ice, ice baby...", "I'm peaceful, I'm serene, I'm halfway down the screen", "The buttons to my left and right are twins", "You are bad at this")
)

val button15 = ButtonItem(
    label = 15,
    color = colourYellow,
    shape = RoundedCornerShape(100.dp),
    hint = listOf("I'm like an egg but in reverse", "stop, WAIT, go", "Have you ever tried egg flavoured crisps? They're good, I promise", "The best thing about egg flavoured crisps is no one else likes them so you get to eat the whole bag")
)

val button16 = ButtonItem(
    label = 16,
    color = colourRed,
    shape = GenericShape {size,_ ->
        addPath(createSmoothHexagonPath(size.minDimension))
    },
    hint = listOf("STOP", "I am sweet", "I can't be bothered to write anymore hints", "Really that's it, you won't get another")
)
val button17 = ButtonItem(
    label = 17,
    color = colourGreen,
    shape = GenericShape {size,_ ->
        addPath(createSmoothTrianglePath(size.minDimension))
    },
    hint = listOf("I like making lists, mostly because it is satisfying to tick things off.", "My neighbour always complains that I am poking him.", "In someways, I am first.", "Somewhere on this screen is the button you are looking for, but I'm not that one.")
)

val button18 = ButtonItem(
    label = 18,
    color = colourTeal,
    shape = GenericShape {size,_ ->
        addPath(createSmoothHexagonPath(size.minDimension))
    },
    hint = listOf("I touch grass everyday. You should too!", "Hexagons are the bestagons", "They don't call me Big Rig for nothing!", "I am a button on this screen.")
)

val button19 = ButtonItem(
    label = 19,
    color = colourRed,
    shape = RoundedCornerShape(0.dp),
    hint = listOf("Deal or no deal?", "I'm halfway to being a stop sign.", "Psst, the Sun rises in the North.", "Really? You need ANOTHER hint?")
)

val button20 = ButtonItem(
    label = 20,
    color = colourDarkGreen,
    shape = RoundedCornerShape(100.dp),
    hint = listOf("I'll have you know, I pay my taxes every year.", "You should really touch some grass.", "What is your point? Because I don't have one.", "It is unclear to me if you even want to win this game.")
)
val button21 = ButtonItem(
    label = 21,
    color = colourRed,
    shape = RoundedCornerShape(100.dp),
    hint = listOf("Legally, I can do all sorts of things.", "I live on Jump Street.", "I live next to a bee-fanatic!", "You'll have your moment soon!")
)

val button22 = ButtonItem(
    label = 22,
    color = colourDarkGreen,
    shape = RoundedCornerShape(16.dp),
    hint = listOf("My name is Desmond.", "The number of the Avenue.", "In the word's of Taylor, I am feeling...", "Ok, I've given you some pretty significant hints already, go listen to some music!")
)

val button23 = ButtonItem(
    label = 23,
    color = colourYellow,
    shape = GenericShape {size,_ ->
        addPath(createSmoothHexagonPath(size.minDimension))
    },
    hint = listOf("I'm actually allergic to bees I'll have you know!", "I am a prime number.", "My carpet is red.", "Did you know, one of my creators has her birthday on this day!")
)

val button24 = ButtonItem(
    label = 24,
    color = colourBlue,
    shape = GenericShape {size, _ ->
        addPath(createSmoothPentagonPath(size.minDimension))
    },
    hint = listOf("I have so many factors!", "Jack Bauer is my dad's name.", "I am beside a yellow button.", "You'll have your moment, it just isn't this one...")
)
val button25 = ButtonItem(
    label = 25,
    color = colourYellow,
    shape = GenericShape {size,_ ->
        addPath(createSmoothHexagonPath(size.minDimension))
    },
    hint = listOf("Do you take your tea with honey or sugar?", "I have red carpets in my house.", "I am a button on this screen.", "Ho Ho Ho! Merry Christmas!")
)

val button26 = ButtonItem(
    label = 26,
    color = colourGreen,
    shape = GenericShape { size, _ ->
        addPath(createSmoothPentagonPath(size.minDimension))
    },
    hint = listOf("I'm feeling lucky!", "I am too old for Leonardo DiCaprio", "I am next to a green button.", "Looks like we've got a winner! Not you though, obviously.")
)

val button27 = ButtonItem(
    label = 27,
    color = colourRed,
    shape = RoundedCornerShape(100.dp),
    hint = listOf("Without my neighbour, I'd roll right off the screen!", "Stop! In the name of love!", "My favourite food is pizza.", "Wow, so close to finding me... maybe...")
)

val button28 = ButtonItem(
    label = 28,
    color = colourTeal,
    shape = GenericShape {size,_ ->
        addPath(createSmoothTrianglePath(size.minDimension))
    },
    hint = listOf("I'm feeling quite blue today...", "I am next to a circle.", "My number neighbour and I share a shape!", "I like buttons.")
)
val button29 = ButtonItem(
    label = 29,
    color = colourRed,
    shape = GenericShape {size,_ ->
        addPath(createSmoothTrianglePath(size.minDimension))
    },
    hint = listOf("I am basically famous!", "I am beside a yellow button.", "I am a Prime Number", "Oh come on! I am famous after all!")
)

val button30 = ButtonItem(
    label = 30,
    color = colourBlue,
    shape = GenericShape {size,_ ->
        addPath(createSmoothHexagonPath(size.minDimension))
    },
    hint = listOf("My favourite song is by Eiffel 65", "My neighbour is the YouTube play button!", "I am divisible by 5!", "Really? No luck yet?")
)

val button31 = ButtonItem(
    label = 31,
    color = colourTeal,
    shape = RoundedCornerShape(16.dp),
    hint = listOf("I have four soft corners.", "Third column is best column.", "In May, I am the final day!", "Did you know, one of my creators has her birthday on this day!")
)

val button32 = ButtonItem(
    label = 32,
    color = colourDarkGreen,
    shape = RoundedCornerShape(100.dp),
    hint = listOf("I don't have any sharp points.", "You could split me evenly in half, but please don't.", "I'm next to two teal buttons.", "I may be at the end of the list, but I am still important!")
)
