package SassySwitches

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
import com.example.dontpushmybuttons.R

class SoundManager(private val context: Context) {
    private val soundPool: SoundPool
    private var buttonClickSound: Int = 0
    private var correctButtonSound: Int = 0

    private var buttonClickLoaded = false
    private var correctButtonLoaded = false

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(audioAttributes)
            .build()

        soundPool.setOnLoadCompleteListener { _, sampleId, status ->
            if (status == 0) {
                when (sampleId) {
                    buttonClickSound -> buttonClickLoaded = true
                    correctButtonSound -> correctButtonLoaded = true
                }
                Log.d("SoundManager", "Sound $sampleId loaded successfully")
            } else {
                Log.e("SoundManager", "Failed to load sound $sampleId")
            }
        }

        buttonClickSound = soundPool.load(context, R.raw.button_click, 1)
        correctButtonSound = soundPool.load(context, R.raw.correct_button, 1)
    }

    fun playButtonClickSound() {
        if (!buttonClickLoaded) {
            Log.w("SoundManager", "Button click sound not loaded yet")
            return
        }
        Log.d("SoundManager", "Playing button click sound")
        soundPool.play(buttonClickSound, 1.0f, 1.0f, 1, 0, 1.0f)
    }

    fun playCorrectButtonSound() {
        if (!correctButtonLoaded) {
            Log.w("SoundManager", "Correct button sound not loaded yet")
            return
        }
        Log.d("SoundManager", "Playing correct button sound")
        soundPool.play(correctButtonSound, 1.0f, 1.0f, 1, 0, 1.0f)
    }

    fun release() {
        soundPool.release()
    }
}