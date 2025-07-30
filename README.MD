# Don't Push My Buttons

## Description

Don't Push My Buttons is a collection of 4 engaging button-based mini-games built with Kotlin and Android Studio. Each game challenges players in different ways, from memory tests to pattern recognition and quick decision-making.

### Background

After just a week of learning Kotlin and Android Studio we were tasked with creating an App. Together, we created Don't Push My Buttons, evolving from a simple button pressing game into a comprehensive gaming suite with multiple game modes.

### Games Included

#### 1. **SassySwitches** 🎯
- **Objective**: Find the hidden target button among 32 uniquely designed buttons
- **Gameplay**: Click buttons to search for the secret target. Each wrong guess increases your click count
- **Hints**: After every 5 incorrect clicks, receive a clever hint about the target button's properties
- **Features**: 32 buttons with unique colors, shapes, and witty hint messages
- **Scoring**: Your score is the total number of clicks needed to find the target

#### 2. **RoboRemember** 🤖
- **Objective**: Memory sequence game inspired by Simon Says
- **Gameplay**: Watch as buttons light up in sequence, then repeat the pattern by tapping buttons in the same order
- **Progressive Difficulty**: Each round adds one more button to the sequence
- **Features**: 4 colorful buttons with haptic feedback and smooth animations
- **Scoring**: Points increase with each successfully completed sequence

#### 3. **SusEmoji** 😈
- **Objective**: Find specific emojis based on clues within a 60-second time limit
- **Gameplay**: A screen full of random emojis appears with clues to help you find the target emoji
- **Time Challenge**: 60-second rounds where you must find as many target emojis as possible
- **Features**: Randomized emoji layouts, creative clues, and fast-paced gameplay
- **Scoring**: Earn 1 point for each correctly identified emoji

#### 4. **Sneaky Button** 🕵️
- **Objective**: Classic "don't click the button" challenge with a twist
- **Gameplay**: Resist the temptation to click the button that's designed to be irresistible
- **Psychology**: Tests your willpower against increasingly persuasive button designs
- **Features**: Dynamic button behavior and psychological challenges
- **Scoring**: Success is measured by your ability to resist!

### Structure

This repo contains:

- A Kotlin Android app with 4 distinct mini-games
- Shared theme system with persistent dark/light mode across all games
- Home screen navigation to access all games
- Individual landing pages for each game with "How to Play" instructions

### Video demo

Video demo will be added soon. Stay tuned!

### Features

- **🎮 4 Unique Games**: Different gameplay mechanics and objectives
- **�� Dark/Light Mode**: Persistent theme settings across all games
- **📱 Responsive Design**: Optimized layouts for different screen sizes
- **🎯 Consistent UI**: Unified design language across all games
- **💾 Game State Management**: Proper state handling and game progression
- **🔄 Navigation**: Seamless switching between games via home screen
- **📖 Instructions**: Built-in "How to Play" guides for each game
- **🎨 Visual Feedback**: Animations, color changes, and haptic feedback

### Technical Features

- **Jetpack Compose UI**: Modern Android UI toolkit
- **Theme Management**: Centralized theme system with SharedPreferences
- **State Management**: Proper state handling with remember and LaunchedEffect
- **Modular Architecture**: Each game is a separate module with clean separation
- **Haptic Feedback**: Enhanced user interaction in RoboRemember
- **Custom Shapes**: Unique button designs in SassySwitches

### Future Features

- Online multiplayer functionality
- Cloud save/sync capabilities
- Achievement system
- Sound effects and background music
- Leaderboards and score sharing

## Installation

### Set up the project

1. Fork this repository
2. Rename the fork
3. Clone the fork to their local machine


## Authors and acknowledgment

This project was created by:
 
[Imogen Lovell](https://github.com/I-Lovell)  
[Emily Sadler](https://github.com/EmiSadler)
