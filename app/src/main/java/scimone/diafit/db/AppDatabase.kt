package scimone.diafit.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CGMEntity::class, BolusEntity::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cgmDao(): CGMDao
    abstract fun bolusDao(): BolusDao
}