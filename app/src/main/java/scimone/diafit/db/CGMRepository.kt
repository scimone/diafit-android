package scimone.diafit.db

import androidx.lifecycle.LiveData

class CGMRepository(private val db: CGMDao) {

    fun getLatestCGM(): LiveData<CGMEntity> {
        return db.getLatest()
    }

    suspend fun insertCGMValue(cgmValue: CGMEntity) {
        db.insert(cgmValue)
    }
}