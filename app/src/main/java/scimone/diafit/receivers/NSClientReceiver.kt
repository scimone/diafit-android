package scimone.diafit.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import scimone.diafit.DiafitApplication
import scimone.diafit.db.BolusEntity
import scimone.diafit.db.CarbsEntity

class NSClientReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "NSClientReceiver"
        private const val ACTION_NEW_PROFILE = Intents.NSCLIENT_NEW_PROFILE
        private const val ACTION_NEW_DEVICE_STATUS = Intents.NSCLIENT_NEW_DEVICE_STATUS
        private const val ACTION_NEW_FOOD = Intents.NSCLIENT_NEW_FOOD
        private const val ACTION_NEW_TREATMENT = Intents.NSCLIENT_NEW_TREATMENT
        private const val ACTION_NEW_CGM = Intents.NSCLIENT_NEW_CGM
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return

        Log.i(TAG, "Received nsclient action: $action")

        val bundle: Bundle? = intent.extras
        if (bundle == null) {
            Log.e(TAG, "Bundle is null for action: $action")
            return
        }

        when (action) {
            ACTION_NEW_PROFILE -> {
                Log.i(TAG, "Received new profile")
            }
            ACTION_NEW_DEVICE_STATUS -> {
                Log.i(TAG, "Received new device status")
            }
            ACTION_NEW_FOOD, ACTION_NEW_TREATMENT -> {
                val treatmentsJson = bundle.getString("treatments", "")
                if (treatmentsJson.isNullOrEmpty()) {
                    Log.e(TAG, "Empty treatments JSON for action: $action")
                    return
                }
                Log.i(TAG, "Treatments JSON: $treatmentsJson")
                processTreatmentsJson(treatmentsJson)
            }
            ACTION_NEW_CGM -> {
                val sgvsJson = bundle.getString("sgvs", "")
                if (sgvsJson.isNullOrEmpty()) {
                    Log.e(TAG, "Empty SGVs JSON for action: $action")
                    return
                }
                Log.i(TAG, "SGVs JSON: $sgvsJson")
                // Process CGM values if needed
            }
            else -> {
                Log.e(TAG, "Unexpected action=$action")
            }
        }
    }

    private fun processTreatmentsJson(treatmentsJson: String) {
        try {
            val jsonArray = JSONArray(treatmentsJson)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                if (!jsonObject.has("_id")) {
                    Log.e(TAG, "Missing _id in treatment JSON object")
                    continue
                }
                if (jsonObject.has("insulin")) {
                    processInsulinJson(jsonObject)
                } else if (jsonObject.has("carbs")) {
                    processCarbsJson(jsonObject)
                } else {
                    Log.e(TAG, "Missing insulin or carbs in treatment JSON object")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error processing treatments JSON: $treatmentsJson", e)
        }
    }

    private fun processInsulinJson(jsonObject: JSONObject) {
        try {
            val id = jsonObject.getString("_id")
            val timestamp = jsonObject.optLong("date")
            val amount = jsonObject.optDouble("insulin")
            val eventType = jsonObject.optString("eventType")
            val isSMB = jsonObject.optBoolean("isSMB")
            val pumpType = jsonObject.optString("pumpType")
            val pumpSerial = jsonObject.optString("pumpSerial")

            if (timestamp == null || amount == null || eventType.isNullOrEmpty()) {
                Log.e(TAG, "Missing required fields in insulin JSON object")
                return
            }

            insertBolusEntry(id, timestamp, amount, eventType, isSMB, pumpType, pumpSerial)
            Log.d(TAG, "Processed insulin entry: $id, $timestamp, $amount, $eventType, $isSMB, $pumpType, $pumpSerial")
        } catch (e: Exception) {
            Log.e(TAG, "Error processing insulin JSON: $jsonObject", e)
        }
    }

    private fun processCarbsJson(jsonObject: JSONObject) {
        try {
            val id = jsonObject.getString("_id")
            val timestamp = jsonObject.optLong("date")
            val amount = jsonObject.optDouble("carbs")
            val eventType = jsonObject.optString("eventType")

            if (timestamp == null || amount == null || eventType.isNullOrEmpty()) {
                Log.e(TAG, "Missing required fields in carbs JSON object")
                return
            }

            insertCarbsEntry(id, timestamp, amount, eventType)
            Log.d(TAG, "Processed carbs entry: $id, $timestamp, $amount, $eventType")
        } catch (e: Exception) {
            Log.e(TAG, "Error processing carbs JSON: $jsonObject", e)
        }
    }

    private fun insertBolusEntry(id: String, timestamp: Long, amount: Double, eventType: String, isSMB: Boolean, pumpType: String, pumpSerial: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                DiafitApplication.db.bolusDao().insert(BolusEntity(id, timestamp, amount, eventType, isSMB, pumpType, pumpSerial))
                Log.d(TAG, "Inserted bolus into the database: $id, $timestamp, $amount, $eventType, $isSMB, $pumpType, $pumpSerial")
            } catch (e: Exception) {
                Log.e(TAG, "Error inserting bolus into the database", e)
            }
        }
    }

    private fun insertCarbsEntry(id: String, timestamp: Long, amount: Double, eventType: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                DiafitApplication.db.carbsDao().insert(CarbsEntity(id, timestamp, amount, eventType))
                Log.d(TAG, "Inserted carbs into the database: $id, $timestamp, $amount, $eventType")
            } catch (e: Exception) {
                Log.e(TAG, "Error inserting carbs into the database", e)
            }
        }
    }
}
