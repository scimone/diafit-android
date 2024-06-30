package scimone.diafit.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CarbsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(carbs: CarbsEntity)

    // get all entries from today, ordered by timestamp descending

    @Query("SELECT * FROM CarbsEntity WHERE timestamp >= :startOfDay")
    fun getAllFromToday(startOfDay: Long): LiveData<List<CarbsEntity>>
}