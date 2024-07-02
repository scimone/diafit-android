package scimone.diafit.core.domain.use_cases

import kotlinx.coroutines.flow.Flow
import scimone.diafit.core.domain.model.CarbsEntity
import scimone.diafit.core.domain.repository.CarbsRepository

class GetAllCarbsFromTodayUseCase (
    private val repository: CarbsRepository
) {
    suspend operator fun invoke(startOfDay: Long): Flow<List<CarbsEntity>> {
        return repository.getAllCarbsFromToday(startOfDay)
    }
}