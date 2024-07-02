package scimone.diafit.core.data.repository

import kotlinx.coroutines.flow.Flow
import scimone.diafit.core.data.local.CarbsDao
import scimone.diafit.core.domain.model.CarbsEntity
import scimone.diafit.core.domain.repository.CarbsRepository

class CarbsRepositoryImpl(
    private val carbsDao: CarbsDao
) : CarbsRepository {

    override suspend fun insertCarbsValue(carbsValue: CarbsEntity) {
        carbsDao.insertCarbs(carbsValue)
    }

    override fun getAllCarbsFromToday(startOfDay: Long): Flow<List<CarbsEntity>> {
        return carbsDao.getAllCarbsFromToday(startOfDay)
    }
}