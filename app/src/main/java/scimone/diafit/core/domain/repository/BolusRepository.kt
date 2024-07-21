package scimone.diafit.core.domain.repository

import kotlinx.coroutines.flow.Flow
import scimone.diafit.core.domain.model.BolusEntity

interface BolusRepository {

    suspend fun insertBolusValue(bolusValue: BolusEntity)

    fun getAllBolusFromToday(startOfDay: Long): Flow<List<BolusEntity>>

    fun getAllBolusSince(start: Long): Flow<List<BolusEntity>>

}