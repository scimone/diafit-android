package scimone.diafit.db

import androidx.lifecycle.LiveData

class CGMRepository(private val db: AppDatabase) {

    fun getLatestCGM(): LiveData<CGMTable> {
        return db.cgmDao().getLatest()
    }

    suspend fun insertCGMValue(cgmValue: CGMTable) {
        db.cgmDao().insert(cgmValue)
    }
}
