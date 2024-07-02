package scimone.diafit.core.domain.repository

import kotlinx.coroutines.flow.Flow
import scimone.diafit.core.domain.model.CGMEntity

interface CGMRepository {

    fun getLatestCGM(): Flow<CGMEntity>

    suspend fun insertCGMValue(cgmValue: CGMEntity)

}