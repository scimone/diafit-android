package scimone.diafit

import android.app.Application
import androidx.room.Room
import dagger.hilt.android.HiltAndroidApp
import scimone.diafit.core.data.local.AppDatabase

@HiltAndroidApp
class DiafitApplication : Application()