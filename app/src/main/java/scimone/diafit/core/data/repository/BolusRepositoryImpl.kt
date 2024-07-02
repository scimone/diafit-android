package scimone.diafit.core.data.repository

import kotlinx.coroutines.flow.Flow
import scimone.diafit.core.data.local.BolusDao
import scimone.diafit.core.domain.model.BolusEntity
import scimone.diafit.core.domain.repository.BolusRepository

class BolusRepositoryImpl(
    private val bolusDao: BolusDao
) : BolusRepository {

    override suspend fun insertBolusValue(bolusValue: BolusEntity) {
        bolusDao.insertBolus(bolusValue)
    }
    override fun getAllBolusFromToday(startOfDay: Long): Flow<List<BolusEntity>> {
        return bolusDao.getAllBolusFromToday(startOfDay)
    }
}