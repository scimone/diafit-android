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

        if (bundle == null) {
            Log.e(NSClientReceiver.TAG, "Bundle is null")
            return
        }

        // Return if action is null
        action ?: return

        // Handle different actions using when statement
        when (action) {
            Intents.NSCLIENT_NEW_PROFILE -> {
                Log.i(TAG, "Received new profile")
            }
            Intents.NSCLIENT_NEW_DEVICE_STATUS -> {
                Log.i(TAG, "Received new device status")
            }
            Intents.NSCLIENT_NEW_FOOD, Intents.NSCLIENT_NEW_TREATMENT -> {
                Log.i(TAG, "Received new treatment or food")

                // Process single treatment JSON object if available
                val treatmentJson = bundle.getString("treatment", "")
                if (treatmentJson!!.isNotEmpty()) {
                    Log.i(TAG, "Treatment JSON: $treatmentJson")
                }

                // Process treatments JSON array if available
                val treatmentsJson = bundle.getString("treatments", "")
                if (treatmentsJson!!.isNotEmpty()) {
                    Log.i(TAG, "Treatments JSON: $treatmentsJson")
                }

            }
            Intents.NSCLIENT_NEW_CGM -> {
                Log.i(TAG, "Received new CGM value")
                val sgvsJson = bundle.getString("sgvs", "")
                if (sgvsJson!!.isNotEmpty()) {
                    Log.i(TAG, "SGVs JSON: $sgvsJson")
                }
            }
            else -> {
                Log.e(TAG, "Unexpected action=$action")
            }
        }
    }
}