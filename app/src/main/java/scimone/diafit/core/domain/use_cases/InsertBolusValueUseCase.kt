package scimone.diafit.core.domain.use_cases

import scimone.diafit.core.domain.model.BolusEntity
import scimone.diafit.core.domain.repository.BolusRepository

class InsertBolusValueUseCase (
    private val repository: BolusRepository
) {
    suspend operator fun invoke(bolusValue: BolusEntity) {
        repository.insertBolusValue(bolusValue)
    }
}