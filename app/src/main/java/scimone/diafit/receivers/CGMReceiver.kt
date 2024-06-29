package scimone.diafit.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class CGMReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "CGMReceiver"
        const val ACTION = Intents.JUGGLUCO_NEW_CGM
        private const val CGMVALUE = "$ACTION.glucose"
    }

    private var onUpdateListener: OnUpdateListener? = null

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action != ACTION) {
            Log.e(TAG, "Unexpected action=$action")
            return
        }

        val cgmValue = intent.getFloatExtra(CGMVALUE, 0f)
        Log.i(TAG, "Received glucose: $cgmValue")

        // Save the new glucose value to SharedPreferences
        saveLastGlucoseValue(context, cgmValue)

        // Notify listener
        onUpdateListener?.onUpdate(cgmValue)
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
}
