package scimone.diafit.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import scimone.diafit.core.domain.model.CarbsEntity

@Dao
interface CarbsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCarbs(carbs: CarbsEntity)

    // get all entries from today, ordered by timestamp descending

    @Query("SELECT * FROM CarbsEntity WHERE timestamp >= :startOfDay ORDER BY timestamp DESC")
    fun getAllCarbsFromToday(startOfDay: Long): Flow<List<CarbsEntity>>

    // get all entries since the defined timestamp, ordered by timestamp ascending
    @Query("SELECT * FROM CarbsEntity WHERE timestamp >= :start ORDER BY timestamp ASC")
    fun getAllCarbsSince(start: Long): Flow<List<CarbsEntity>>
}