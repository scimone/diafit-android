package scimone.diafit.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import scimone.diafit.DiafitApplication
import scimone.diafit.db.CGMTable

class CGMReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "CGMReceiver"
        const val ACTION = Intents.JUGGLUCO_NEW_CGM
        private const val CGMVALUE = "$ACTION.mgdl" // CGM glucose value in mg/dL
        private const val RATE = "$ACTION.Rate"  // Rate of change
        private const val TIMESTAMP = "$ACTION.Time" // Timestamp of the CGM value
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        if (action != ACTION) {
            Log.e(TAG, "Unexpected action=$action")
            return
        }

        // CGM value
        val cgmValue = intent.getIntExtra(CGMVALUE, 0)
        val rate = intent.getFloatExtra(RATE, 0f)
        val timestamp = intent.getLongExtra(TIMESTAMP, 0)

        val cgmString = "$cgmValue mg/dL, rate: $rate mg/dL/min, trend: ${getDexcomTrend(rate)}"
        Log.i(TAG, "Received new CGM value: $cgmString")

        // Insert CGM value into the database
        insertCGMValue(timestamp, cgmValue)
    }

    private fun insertCGMValue(timestamp: Long, cgmValue: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            DiafitApplication.db.cgmDao().insert(CGMTable(timestamp, cgmValue))
            Log.d(TAG, "Inserted CGM value into the database: $cgmValue at $timestamp")
        }
    }

    private fun getDexcomTrend(rate: Float): String {
        if (rate >= 3.5f) return "DoubleUp"
        if (rate >= 2.0f) return "SingleUp"
        if (rate >= 1.0f) return "FortyFiveUp"
        if (rate > -1.0f) return "Flat"
        if (rate > -2.0f) return "FortyFiveDown"
        if (rate > -3.5f) return "SingleDown"
        return if (java.lang.Float.isNaN(rate)) "" else "DoubleDown"
    }
}
