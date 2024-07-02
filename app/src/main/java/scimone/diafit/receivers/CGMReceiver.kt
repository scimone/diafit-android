package scimone.diafit.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import scimone.diafit.core.domain.services.CreateCGMEntityService
import scimone.diafit.core.domain.use_cases.CommonUseCases
import javax.inject.Inject

@AndroidEntryPoint
class CGMReceiver : BroadcastReceiver() {

    @Inject
    lateinit var commonUseCases: CommonUseCases
    @Inject
    lateinit var createCgmEntityService: CreateCGMEntityService

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

        Log.i(TAG, "Received new CGM value: $cgmValue")

        // Insert CGM value into the database
        insertCGMValue(timestamp, cgmValue, rate)
    }

    private fun insertCGMValue(timestamp: Long, cgmValue: Int, rate: Float) {
        val cgmEntity = createCgmEntityService.createCGMEntity(timestamp, cgmValue, rate)
        CoroutineScope(Dispatchers.IO).launch {
            commonUseCases.insertCGMValueUseCase(cgmEntity)
            Log.d(TAG, "Inserted CGM value into the database: ${cgmEntity.value} at ${cgmEntity.timestampString}")
        }
    }
}
