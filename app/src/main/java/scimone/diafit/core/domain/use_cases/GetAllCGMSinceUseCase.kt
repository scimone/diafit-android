package scimone.diafit.core.domain.use_cases

import kotlinx.coroutines.flow.Flow
import scimone.diafit.core.domain.model.CGMEntity
import scimone.diafit.core.domain.repository.CGMRepository
class GetAllCGMSinceUseCase (
    private val repository: CGMRepository
) {
    suspend operator fun invoke(startTime: Long): Flow<List<CGMEntity>> {
        return repository.getAllCGMSince(startTime)
    }
}