package scimone.diafit.db

import scimone.diafit.db.BolusDao
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CGMEntity::class, BolusEntity::class, CarbsEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cgmDao(): CGMDao
    abstract fun bolusDao(): BolusDao
    abstract fun carbsDao(): CarbsDao
}