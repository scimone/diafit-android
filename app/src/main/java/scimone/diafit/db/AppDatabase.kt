package scimone.diafit.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CGMTable::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cgmDao(): CGMDao
}