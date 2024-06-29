package scimone.diafit

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class CGMReceiver : BroadcastReceiver() {

    private var onUpdateListener: OnUpdateListener? = null

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action != ACTION) {
            Log.e(LOG_ID, "Unexpected action=$action")
            return
        }

        val glucose = intent.getFloatExtra(GLUCOSECUSTOM, 0f)
        Log.i(LOG_ID, "Received glucose: $glucose")

        // Save the new glucose value to SharedPreferences
        saveLastGlucoseValue(context, glucose)

        // Notify listener
        onUpdateListener?.onUpdate(glucose)
    }

    private fun saveLastGlucoseValue(context: Context, glucose: Float) {
        val sharedPreferences = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putFloat("last_glucose_value_key", glucose)
            apply()
        }
    }

    fun setOnUpdateListener(listener: OnUpdateListener?) {
        onUpdateListener = listener
    }

    interface OnUpdateListener {
        fun onUpdate(glucose: Float)
    }

    companion object {
        private const val LOG_ID = "CGMReceiver"
        private const val ACTION = "glucodata.Minute"
        private const val GLUCOSECUSTOM = "glucodata.Minute.glucose"
    }
}
