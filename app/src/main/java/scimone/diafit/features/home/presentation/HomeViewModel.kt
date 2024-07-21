package scimone.diafit.features.home.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import scimone.diafit.core.domain.use_cases.CommonUseCases
import scimone.diafit.core.utils.DateUtils
import scimone.diafit.core.presentation.model.CGMChartData
import scimone.diafit.core.utils.DateUtils.timestampToDateTimeString
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val commonUseCases: CommonUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    init {
        loadBolus()
        loadCarbs()
        loadCGM()
        loadAllCGMSince24h()
        updateCountdown()
    }

    private val startOfDay: Long
        get() {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            // log time string
            Log.d("HomeViewModel", "startOfDay: ${timestampToDateTimeString(calendar.timeInMillis)}")
            return calendar.timeInMillis
        }

    private val nowMinus24h: Long
        get() {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.HOUR_OF_DAY, -24) // Subtract 24 hours from the current time
            Log.d("HomeViewModel", "nowMinus24h: ${timestampToDateTimeString(calendar.timeInMillis)}")
            return calendar.timeInMillis
        }

    private fun loadBolus() {
        viewModelScope.launch {
            commonUseCases.getAllBolusFromTodayUseCase(startOfDay).collect { bolusList ->
                val bolusWithDateTime = bolusList.map { bolus ->
                    bolus.copy(timestampString = DateUtils.timestampToTimeString(bolus.timestamp))
                }
                _state.value = _state.value.copy(allBolusFromToday = bolusWithDateTime)
            }
        }
    }

    private fun loadCarbs() {
        viewModelScope.launch {
            commonUseCases.getAllCarbsFromTodayUseCase(startOfDay).collect { carbsList ->
                val carbsWithDateTime = carbsList.map { carbs ->
                    carbs.copy(timestampString = DateUtils.timestampToTimeString(carbs.timestamp))
                }
                _state.value = _state.value.copy(allCarbsFromToday = carbsWithDateTime)
                Log.d("HomeViewModel", "allCarbsFromToday: $carbsList")
            }
        }
    }

    private fun loadCGM() {
        viewModelScope.launch {
            commonUseCases.getLatestCGMUseCase().collect { cgm ->
                _state.value = _state.value.copy(latestCGM = cgm)
            }
        }
    }


    private fun loadAllCGMSince24h() {
        viewModelScope.launch {
            commonUseCases.getAllCGMSinceUseCase(nowMinus24h).collect { cgmList ->
                val cgmChartDataList = cgmList.map { cgmEntity ->
                    CGMChartData(
                        timeFloat = cgmEntity.timestamp.toFloat(),
                        value = cgmEntity.value
                    )
                }
                _state.value = _state.value.copy(allCGMSince24h = cgmChartDataList)
            }
        }
    }

    private fun updateCountdown() {
        viewModelScope.launch {
            while (true) {
                state.value.latestCGM?.let { cgm ->
                    val duration = System.currentTimeMillis() - cgm.timestamp
                    val totalSeconds = duration / 1000
                    val hours = totalSeconds / 3600
                    val minutes = (totalSeconds % 3600) / 60
                    val seconds = totalSeconds % 60

                    val formattedTime = buildString {
                        if (hours > 0) append("${hours}h ")
                        if (minutes > 0 || hours > 0) append("${minutes}m ")
                        append("${seconds}s")
                    }

                    // Update the state with the new formatted time string
                    _state.value = _state.value.copy(timeSinceLastCGM = formattedTime)
                }
                delay(1000)
            }
        }
    }
}