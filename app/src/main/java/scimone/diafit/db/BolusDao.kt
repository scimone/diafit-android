package scimone.diafit.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import scimone.diafit.db.BolusEntity

@Dao
interface BolusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bolus: BolusEntity)

    // get all entries from today, ordered by timestamp descending
    @Query("SELECT * FROM BolusEntity WHERE timestamp >= :startOfDay ORDER BY timestamp DESC")
    fun getAllFromToday(startOfDay: Long): Flow<List<BolusEntity>>
}