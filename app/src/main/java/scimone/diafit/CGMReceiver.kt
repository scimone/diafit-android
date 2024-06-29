package scimone.diafit

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

// BroadcastReceiver for receiving CGM data broadcasts
class CGMReceiver : BroadcastReceiver() {

    // Listener for onUpdate events
    private var onUpdateListener: OnUpdateListener? = null

    // Method called when a broadcast is received
    override fun onReceive(context: Context, intent: Intent) {
        // Retrieve the action from the intent
        val action = intent.action

        // Check if the action matches the expected action
        if (action != ACTION) {
            // Log an error if the action is unexpected
            Log.e(LOG_ID, "Unexpected action=$action")
            return
        }

        // Retrieve the glucose value from the intent
        val glucose = intent.getFloatExtra(GLUCOSECUSTOM, 0f)
        Log.i(LOG_ID, "Received glucose: $glucose")

        // Save the new glucose value to SharedPreferences
        saveLastGlucoseValue(context, glucose)

        // Notify listener (MainActivity) about the new glucose value
        onUpdateListener?.onUpdate(glucose)
    }

    // Method to save the last received glucose value to SharedPreferences
    private fun saveLastGlucoseValue(context: Context, glucose: Float) {
        val sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putFloat("last_glucose_value_key", glucose)
            apply()
        }
    }

    // Method to set the onUpdateListener (MainActivity)
    fun setOnUpdateListener(listener: OnUpdateListener?) {
        onUpdateListener = listener
    }

    // Interface definition for the onUpdate callback
    interface OnUpdateListener {
        fun onUpdate(glucose: Float)
    }

    companion object {
        private const val LOG_ID = "CGMReceiver"
        private const val ACTION = "glucodata.Minute" // Action name for the broadcast
        private const val GLUCOSECUSTOM = "glucodata.Minute.glucose" // Extra key for glucose value
    }
}
