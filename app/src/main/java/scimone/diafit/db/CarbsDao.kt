package scimone.diafit.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CarbsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(carbs: CarbsEntity)

    // get all entries from today, ordered by timestamp descending
    @Query("SELECT * FROM CarbsEntity WHERE timestamp >= :startOfDay ORDER BY timestamp DESC")
    suspend fun getAllFromToday(startOfDay: Long): List<CarbsEntity>
}