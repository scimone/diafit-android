package scimone.diafit.core.domain.repository

import kotlinx.coroutines.flow.Flow
import scimone.diafit.core.domain.model.CGMEntity

interface CGMRepository {

    fun getLatestCGM(): Flow<CGMEntity>

    fun get5MinCGMRateAvg(): Flow<Float>

    fun getAllCGMSince(startOfDay: Long): Flow<List<CGMEntity>>

    suspend fun insertCGMValue(cgmValue: CGMEntity)

}