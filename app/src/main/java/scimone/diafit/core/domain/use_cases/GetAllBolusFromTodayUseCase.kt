package scimone.diafit.core.domain.use_cases

import kotlinx.coroutines.flow.Flow
import scimone.diafit.core.domain.model.BolusEntity
import scimone.diafit.core.domain.repository.BolusRepository

class GetAllBolusFromTodayUseCase (
    private val repository: BolusRepository
) {
    suspend operator fun invoke(startOfDay: Long): Flow<List<BolusEntity>> {
        return repository.getAllBolusFromToday(startOfDay)
    }
}