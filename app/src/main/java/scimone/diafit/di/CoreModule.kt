package scimone.diafit.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import scimone.diafit.core.data.local.AppDatabase
import scimone.diafit.core.data.repository.BolusRepositoryImpl
import scimone.diafit.core.data.repository.CGMRepositoryImpl
import scimone.diafit.core.data.repository.CarbsRepositoryImpl
import scimone.diafit.core.domain.repository.BolusRepository
import scimone.diafit.core.domain.repository.CGMRepository
import scimone.diafit.core.domain.repository.CarbsRepository
import scimone.diafit.core.domain.services.CreateCGMEntityService
import scimone.diafit.core.domain.use_cases.CommonUseCases
import scimone.diafit.core.domain.use_cases.GetAllBolusFromTodayUseCase
import scimone.diafit.core.domain.use_cases.GetAllCGMFromTodayUseCase
import scimone.diafit.core.domain.use_cases.GetAllCarbsFromTodayUseCase
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
            getAllCGMFromTodayUseCase = GetAllCGMFromTodayUseCase(cgmRepository),
            getAllBolusFromTodayUseCase = GetAllBolusFromTodayUseCase(bolusRepository),
            getAllCarbsFromTodayUseCase = GetAllCarbsFromTodayUseCase(carbsRepository)
        )
    }

    @Provides
    @Singleton
    fun provideCGMEntityService(): CreateCGMEntityService {
        return CreateCGMEntityService()
    }
}