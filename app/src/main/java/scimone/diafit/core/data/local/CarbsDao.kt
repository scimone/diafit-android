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
}