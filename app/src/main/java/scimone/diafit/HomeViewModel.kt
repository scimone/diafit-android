package scimone.diafit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import scimone.diafit.db.CGMEntity
import scimone.diafit.db.CGMRepository
import scimone.diafit.db.BolusEntity
import scimone.diafit.db.BolusRepository
import scimone.diafit.DiafitApplication
import java.util.*

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val cgmRepository: CGMRepository = CGMRepository(DiafitApplication.db.cgmDao())
    private val bolusRepository: BolusRepository = BolusRepository(DiafitApplication.db.bolusDao())

    val latestCGMValue: LiveData<CGMEntity> = cgmRepository.getLatestCGM()

    private val startOfDay: Long
        get() {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            return calendar.timeInMillis
        }

    val allBolusFromToday: LiveData<List<BolusEntity>> = bolusRepository.getAllFromToday(startOfDay)

    fun insertCGMValue(cgmValue: CGMEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            cgmRepository.insertCGMValue(cgmValue)
        }
    }
}