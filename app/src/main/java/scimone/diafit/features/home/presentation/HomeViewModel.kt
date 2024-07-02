package scimone.diafit.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import scimone.diafit.core.domain.use_cases.CommonUseCases
import scimone.diafit.core.utils.DateUtils
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
        updateCountdown()
    }

    private val startOfDay: Long
        get() {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
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

    private fun updateCountdown() {
        viewModelScope.launch {
            while (true) {
                state.value.latestCGM?.let { cgm ->
                    val duration = System.currentTimeMillis() - cgm.timestamp
                    _state.value = _state.value.copy(timeSinceLastCGM = duration.toInt() / 1000)
                }
                delay(1000)
            }
        }
    }
}