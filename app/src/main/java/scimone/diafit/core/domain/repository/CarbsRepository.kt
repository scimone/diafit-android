package scimone.diafit.core.domain.repository

import kotlinx.coroutines.flow.Flow
import scimone.diafit.core.domain.model.CarbsEntity

interface CarbsRepository {

    suspend fun insertCarbsValue(carbsValue: CarbsEntity)

    fun getAllCarbsFromToday(startOfDay: Long): Flow<List<CarbsEntity>>

    fun getAllCarbsSince(start: Long): Flow<List<CarbsEntity>>

}