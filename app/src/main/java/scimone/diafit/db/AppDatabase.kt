package scimone.diafit.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CGMTable::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cgmDao(): CGMDao
}