package scimone.diafit.core.domain.use_cases

import kotlinx.coroutines.flow.Flow
import scimone.diafit.core.domain.model.BolusEntity
import scimone.diafit.core.domain.repository.BolusRepository

class GetAllBolusSinceUseCase (
    private val repository: BolusRepository
) {
    suspend operator fun invoke(startTime: Long): Flow<List<BolusEntity>> {
        return repository.getAllBolusSince(startTime)
    }
}