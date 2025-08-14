package utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

/**
 * Utility class for haptic feedback across all games
 */
object HapticFeedback {

    /**
     * Performs haptic feedback with a short vibration
     * @param context The context to access system services
     */
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
}
