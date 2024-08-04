package scimone.diafit.core.services

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import scimone.diafit.core.data.remote.NightscoutAPI
import scimone.diafit.core.domain.use_cases.CommonUseCases
import scimone.diafit.core.utils.DateUtils
import scimone.diafit.core.utils.DateUtils.timestampToISOString
import java.util.concurrent.TimeUnit

@HiltWorker
class DataGapService @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val commonUseCases: CommonUseCases,
    private val nightscoutApi: NightscoutAPI

) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.d(TAG, "doWork: Starting work")
        checkAndFillDataGaps()
        Log.d(TAG, "doWork: Work completed")
        return Result.success()
    }

    private fun checkAndFillDataGaps() {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(TAG, "checkAndFillDataGaps: Checking for data gaps")
            val gaps = checkForDataGaps()
            Log.d(TAG, "checkAndFillDataGaps: Found ${gaps.size} gaps")
            fillMissingValues(gaps)
        }
    }

    private suspend fun checkForDataGaps(): List<Pair<Long, Long>> = withContext(Dispatchers.IO) {
        val startDate = DateUtils.nowMinusXMinutes(LOOKBACK_TIME)
        val cgmEntries = commonUseCases.getAllCGMSinceUseCase(startDate).first()
        val gaps = mutableListOf<Pair<Long, Long>>()

        if (cgmEntries.isNotEmpty()) {
            val firstEntry = cgmEntries.first()
            if (firstEntry.timestamp > startDate + EXPECTED_INTERVAL) {
                gaps.add(Pair(startDate, firstEntry.timestamp))
            }

            for (i in 1 until cgmEntries.size) {
                val currentEntry = cgmEntries[i]
                val previousEntry = cgmEntries[i - 1]
                val expectedDate = previousEntry.timestamp + EXPECTED_INTERVAL

                if (currentEntry.timestamp > expectedDate) {
                    gaps.add(Pair(expectedDate, currentEntry.timestamp))
                }
            }
        }

        Log.d(TAG, "checkForDataGaps: Gaps identified: $gaps")
        return@withContext gaps
    }

    private suspend fun fillMissingValues(gaps: List<Pair<Long, Long>>) {
        for ((startDate, endDate) in gaps) {
            val startDateStr = timestampToISOString(startDate)
            val endDateStr = timestampToISOString(endDate)
            Log.d(TAG, "fillMissingValues: Filling gap from $startDateStr to $endDateStr")

            val missingEntries = withContext(Dispatchers.IO) {
                nightscoutApi.getNSCGMBetween(
                    startDateStr,
                    endDateStr
                )
            }
            for (entry in missingEntries.map { it.toCGMEntity() }) {
                Log.d(TAG, "fillMissingValues: Inserting entry: ${entry.value} at ${entry.timestampString}")
                commonUseCases.insertCGMValueUseCase(entry)
            }
        }
    }

    companion object {
        private const val TAG = "DataGapService"
        const val FREQUENCY = 60L  // worker frequency in minutes
        const val LOOKBACK_TIME = 60  // lookback time in minutes
        const val EXPECTED_INTERVAL = 7 * 60 * 1000  // expected interval between CGM entries in milliseconds
        fun scheduleWork(context: Context) {
            val workRequest = PeriodicWorkRequestBuilder<DataGapService>(FREQUENCY, TimeUnit.MINUTES)
                .build()
            WorkManager.getInstance(context).enqueue(workRequest)
        }
    }
}