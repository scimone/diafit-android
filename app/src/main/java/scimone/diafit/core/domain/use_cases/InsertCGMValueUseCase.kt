package scimone.diafit.core.domain.use_cases

import scimone.diafit.core.domain.model.CGMEntity
import scimone.diafit.core.domain.repository.CGMRepository

class InsertCGMValueUseCase (
    private val repository: CGMRepository
) {
    suspend operator fun invoke(cgmValue: CGMEntity) {
        repository.insertCGMValue(cgmValue)
    }
}