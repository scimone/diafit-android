package scimone.diafit.core.data.repository

import kotlinx.coroutines.flow.Flow
import scimone.diafit.core.data.local.CGMDao
import scimone.diafit.core.domain.model.CGMEntity
import scimone.diafit.core.domain.repository.CGMRepository

class CGMRepositoryImpl(
    private val cgmDao: CGMDao
) : CGMRepository {

    override fun getLatestCGM(): Flow<CGMEntity> {
        return cgmDao.getLatestCGM()
    }

    override fun getAllCGMSince(startOfDay: Long): Flow<List<CGMEntity>> {
        return cgmDao.getAllCGMSince(startOfDay)
    }

    override suspend fun insertCGMValue(cgmValue: CGMEntity) {
        cgmDao.insertCGM(cgmValue)
    }
}