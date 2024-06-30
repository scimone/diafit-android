package scimone.diafit.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface CarbsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(carbs: CarbsEntity)
}