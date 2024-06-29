package scimone.diafit.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log

class NSClientReceiver: BroadcastReceiver() {

    companion object {
        private const val TAG = "NSClientReceiver" // Log tag for debugging purposes
    }
    override fun onReceive(context: Context, intent: Intent) {
        Log.i(TAG, "Received nsclient action: ${intent.action}") // Log the received action

        val bundle: Bundle? = intent.extras // Extract extras bundle from the intent
        val action: String? = intent.action // Get the action from the intent

        // Return if action is null
        action ?: return

        // Handle different actions using when statement
        when (action) {
            Intents.ACTION_NEW_TREATMENT -> {
                Log.i(TAG, "Received new treatment")
            }
            Intents.ACTION_NEW_PROFILE -> {
                Log.i(TAG, "Received new profile")
            }
            Intents.ACTION_NEW_DEVICE_STATUS -> {
                Log.i(TAG, "Received new device status")
            }
            Intents.ACTION_NEW_FOOD -> {
                Log.i(TAG, "Received new food")
            }
            Intents.ACTION_NEW_SGV -> {
                Log.i(TAG, "Received new sgv")
            }
            else -> {
                Log.e(TAG, "Unexpected action=$action")
            }
        }
    }
}