package scimone.diafit.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData

import scimone.diafit.db.BolusDao

class BolusRepository(private val bolusDao: BolusDao) {
    fun getAllFromToday(startOfDay: Long): LiveData<List<BolusEntity>> {
        return bolusDao.getAllFromToday(startOfDay).asLiveData()
    }
}