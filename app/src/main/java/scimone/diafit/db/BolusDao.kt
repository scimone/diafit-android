package scimone.diafit.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BolusDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bolus: BolusEntity)

    // get all entries from today, ordered by timestamp descending
    @Query("SELECT * FROM BolusEntity WHERE timestamp >= :startOfDay ORDER BY timestamp DESC")
    suspend fun getAllFromToday(startOfDay: Long): List<BolusEntity>

    @Query("SELECT * FROM BolusEntity ORDER BY timestamp DESC")
    suspend fun getAll(): List<BolusEntity>
}