package scimone.diafit.core.domain.services

import scimone.diafit.core.domain.model.CGMEntity
import scimone.diafit.core.utils.DateUtils

class CreateCGMEntityService {

    fun createCGMEntity(timestamp: Long, cgmValue: Int, rate: Float): CGMEntity {
        return CGMEntity(
            timestamp=timestamp,
            timestampString = DateUtils.timestampToDateTimeString(timestamp),
            value=cgmValue,
            rate=rate,
        )
    }
}