package scimone.diafit

import android.app.Application
import androidx.room.Room
import scimone.diafit.db.AppDatabase

class DiafitApplication : Application() {
    companion object {
        lateinit var db: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "db-diafit"
        ).fallbackToDestructiveMigration().build()
    }
}