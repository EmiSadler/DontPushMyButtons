package com.example.dontpushmybuttons.utils

import android.content.Context
import android.content.SharedPreferences

class HighScoreManager private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("high_scores", Context.MODE_PRIVATE)

    companion object {
        private const val SASSY_SWITCHES_KEY = "sassy_switches_high_score"
        private const val ROBO_REMEMBER_KEY = "robo_remember_high_score"
        private const val SUS_EMOJI_KEY = "sus_emoji_high_score"
        private const val SNEAKY_BUTTON_KEY = "sneaky_button_high_score"

        @Volatile
        private var INSTANCE: HighScoreManager? = null

        fun getInstance(context: Context): HighScoreManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: HighScoreManager(context.applicationContext).also { INSTANCE = it }
            }
        }
    }

    // SassySwitches - lower score is better (fewer clicks)
    fun getSassySwitchesHighScore(): Int {
        return sharedPreferences.getInt(SASSY_SWITCHES_KEY, Int.MAX_VALUE)
    }

    fun setSassySwitchesHighScore(score: Int) {
        val currentBest = getSassySwitchesHighScore()
        if (score < currentBest || currentBest == Int.MAX_VALUE) {
            sharedPreferences.edit()
                .putInt(SASSY_SWITCHES_KEY, score)
                .apply()
        }
    }

    fun hasSassySwitchesScore(): Boolean {
        return getSassySwitchesHighScore() != Int.MAX_VALUE
    }

    // RoboRemember - higher score is better
    fun getRoboRememberHighScore(): Int {
        return sharedPreferences.getInt(ROBO_REMEMBER_KEY, 0)
    }

    fun setRoboRememberHighScore(score: Int) {
        val currentBest = getRoboRememberHighScore()
        if (score > currentBest) {
            sharedPreferences.edit()
                .putInt(ROBO_REMEMBER_KEY, score)
                .apply()
        }
    }

    // SusEmoji - higher score is better
    fun getSusEmojiHighScore(): Int {
        return sharedPreferences.getInt(SUS_EMOJI_KEY, 0)
    }

    fun setSusEmojiHighScore(score: Int) {
        val currentBest = getSusEmojiHighScore()
        if (score > currentBest) {
            sharedPreferences.edit()
                .putInt(SUS_EMOJI_KEY, score)
                .apply()
        }
    }

    // SneakyButton - higher score is better (or could be time-based)
    fun getSneakyButtonHighScore(): Int {
        return sharedPreferences.getInt(SNEAKY_BUTTON_KEY, 0)
    }

    fun setSneakyButtonHighScore(score: Int) {
        val currentBest = getSneakyButtonHighScore()
        if (score > currentBest) {
            sharedPreferences.edit()
                .putInt(SNEAKY_BUTTON_KEY, score)
                .apply()
        }
    }

    // Clear all high scores (for testing or reset functionality)
    fun clearAllHighScores() {
        sharedPreferences.edit()
            .remove(SASSY_SWITCHES_KEY)
            .remove(ROBO_REMEMBER_KEY)
            .remove(SUS_EMOJI_KEY)
            .remove(SNEAKY_BUTTON_KEY)
            .apply()
    }
}
