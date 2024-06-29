package scimone.diafit.db
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CGMDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cgmValue: CGMTable)

    @Query("SELECT * FROM CGMTable ORDER BY timestamp DESC LIMIT 1")
    fun getLatest(): LiveData<CGMTable>
}