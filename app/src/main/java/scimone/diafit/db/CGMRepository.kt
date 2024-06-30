package scimone.diafit.db

import androidx.lifecycle.LiveData

class CGMRepository(private val db: AppDatabase) {

    fun getLatestCGM(): LiveData<CGMEntity> {
        return db.cgmDao().getLatest()
    }

    suspend fun insertCGMValue(cgmValue: CGMEntity) {
        db.cgmDao().insert(cgmValue)
    }
}
