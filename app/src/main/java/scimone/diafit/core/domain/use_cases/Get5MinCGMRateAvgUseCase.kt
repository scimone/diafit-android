package scimone.diafit.core.domain.use_cases

import kotlinx.coroutines.flow.Flow
import scimone.diafit.core.domain.model.CGMEntity
import scimone.diafit.core.domain.repository.CGMRepository

class Get5MinCGMRateAvgUseCase (
    private val repository: CGMRepository
    ) {
        suspend operator fun invoke(): Flow<Float?> {
            return repository.get5MinCGMRateAvg()
        }
    }