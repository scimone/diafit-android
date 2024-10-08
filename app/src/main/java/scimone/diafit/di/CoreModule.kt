package scimone.diafit.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import scimone.diafit.core.data.local.AppDatabase
import scimone.diafit.core.data.remote.NightscoutAPI
import scimone.diafit.core.data.repository.BolusRepositoryImpl
import scimone.diafit.core.data.repository.CGMRepositoryImpl
import scimone.diafit.core.data.repository.CarbsRepositoryImpl
import scimone.diafit.core.domain.repository.BolusRepository
import scimone.diafit.core.domain.repository.CGMRepository
import scimone.diafit.core.domain.repository.CarbsRepository
import scimone.diafit.core.domain.use_cases.CommonUseCases
import scimone.diafit.core.domain.use_cases.GetAllBolusFromTodayUseCase
import scimone.diafit.core.domain.use_cases.GetAllBolusSinceUseCase
import scimone.diafit.core.domain.use_cases.GetAllCGMSinceUseCase
import scimone.diafit.core.domain.use_cases.GetAllCarbsFromTodayUseCase
import scimone.diafit.core.domain.use_cases.GetAllCarbsSinceUseCase
import scimone.diafit.core.domain.use_cases.GetLatestCGMUseCase
import scimone.diafit.core.domain.use_cases.InsertBolusValueUseCase
import scimone.diafit.core.domain.use_cases.InsertCGMValueUseCase
import scimone.diafit.core.domain.use_cases.InsertCarbsValueUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideNightscoutApi(): NightscoutAPI {
        return Retrofit.Builder()
            .baseUrl(NightscoutAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }).build())
            .build()
            .create(NightscoutAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideCGMRepository(appDatabase: AppDatabase): CGMRepository {
        return CGMRepositoryImpl(appDatabase.cgmDao())
    }

    @Provides
    @Singleton
    fun provideBolusRepository(appDatabase: AppDatabase): BolusRepository {
        return BolusRepositoryImpl(appDatabase.bolusDao())
    }

    @Provides
    @Singleton
    fun provideCarbsRepository(appDatabase: AppDatabase): CarbsRepository {
        return CarbsRepositoryImpl(appDatabase.carbsDao())
    }

    @Provides
    @Singleton
    fun provideCommonUseCases(cgmRepository: CGMRepository,
                              carbsRepository: CarbsRepository,
                              bolusRepository: BolusRepository
    ): CommonUseCases {
        return CommonUseCases(
            insertCGMValueUseCase = InsertCGMValueUseCase(cgmRepository),
            insertCarbsValueUseCase = InsertCarbsValueUseCase(carbsRepository),
            insertBolusValueUseCase = InsertBolusValueUseCase(bolusRepository),
            getLatestCGMUseCase = GetLatestCGMUseCase(cgmRepository),
            getAllCGMSinceUseCase = GetAllCGMSinceUseCase(cgmRepository),
            getAllBolusSinceUseCase = GetAllBolusSinceUseCase(bolusRepository),
            getAllCarbsSinceUseCase = GetAllCarbsSinceUseCase(carbsRepository),
            getAllBolusFromTodayUseCase = GetAllBolusFromTodayUseCase(bolusRepository),
            getAllCarbsFromTodayUseCase = GetAllCarbsFromTodayUseCase(carbsRepository),
            )
    }
}