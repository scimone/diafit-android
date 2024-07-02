package scimone.diafit.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import scimone.diafit.core.domain.model.BolusEntity

@Dao
interface BolusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBolus(bolus: BolusEntity)

    // get all entries from today, ordered by timestamp descending
    @Query("SELECT * FROM BolusEntity WHERE timestamp >= :startOfDay ORDER BY timestamp DESC")
    fun getAllBolusFromToday(startOfDay: Long): Flow<List<BolusEntity>>
}