package scimone.diafit.db

import androidx.lifecycle.LiveData

class CarbsRepository(private val carbsDao: CarbsDao) {
    fun getAllFromToday(startOfDay: Long): LiveData<List<CarbsEntity>> {
        return carbsDao.getAllFromToday(startOfDay)
    }
}