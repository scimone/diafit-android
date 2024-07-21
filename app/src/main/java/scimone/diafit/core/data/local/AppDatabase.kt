package scimone.diafit.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import scimone.diafit.core.domain.model.BolusEntity
import scimone.diafit.core.domain.model.CGMEntity
import scimone.diafit.core.domain.model.CarbsEntity

@Database(entities = [CGMEntity::class, BolusEntity::class, CarbsEntity::class], version = 5)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cgmDao(): CGMDao
    abstract fun bolusDao(): BolusDao
    abstract fun carbsDao(): CarbsDao

    companion object {
        const val DB_NAME = "db_diafit"
    }
}