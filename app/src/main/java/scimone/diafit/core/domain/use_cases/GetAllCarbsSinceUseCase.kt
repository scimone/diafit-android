package scimone.diafit.core.domain.use_cases

import kotlinx.coroutines.flow.Flow
import scimone.diafit.core.domain.model.CarbsEntity
import scimone.diafit.core.domain.repository.CarbsRepository

class GetAllCarbsSinceUseCase (
    private val repository: CarbsRepository
) {
    suspend operator fun invoke(startTime: Long): Flow<List<CarbsEntity>> {
        return repository.getAllCarbsSince(startTime)
    }
}
