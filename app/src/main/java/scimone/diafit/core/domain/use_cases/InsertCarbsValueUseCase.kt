package scimone.diafit.core.domain.use_cases

import scimone.diafit.core.domain.model.CarbsEntity
import scimone.diafit.core.domain.repository.CarbsRepository

class InsertCarbsValueUseCase (
    private val repository: CarbsRepository
) {
    suspend operator fun invoke(carbsValue: CarbsEntity) {
        repository.insertCarbsValue(carbsValue)
    }
}