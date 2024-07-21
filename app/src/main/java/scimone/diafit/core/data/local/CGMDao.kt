package scimone.diafit.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import scimone.diafit.core.domain.model.CGMEntity

@Dao
interface CGMDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCGM(cgmValue: CGMEntity)

    @Query("SELECT AVG(rate) FROM CGMEntity WHERE timestamp >= :start AND timestamp <= :end")
    fun get5MinCGMRateAvg(start: Long, end: Long): Flow<Float?>

    @Query("SELECT * FROM CGMEntity WHERE timestamp >= :start ORDER BY timestamp ASC")
    fun getAllCGMSince(start: Long): Flow<List<CGMEntity>>

    @Query("SELECT * FROM CGMEntity ORDER BY timestamp DESC LIMIT 1")
    fun getLatestCGM(): Flow<CGMEntity>
}