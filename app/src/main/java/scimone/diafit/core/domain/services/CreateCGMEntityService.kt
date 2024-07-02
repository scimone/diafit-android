package scimone.diafit.core.domain.services

import scimone.diafit.core.domain.model.CGMEntity
import scimone.diafit.core.domain.utils.DateUtils

class CreateCGMEntityService {

    fun createCGMEntity(timestamp: Long, cgmValue: Int, rate: Float): CGMEntity {
        val trend = getDexcomTrend(rate)
        return CGMEntity(
            timestamp=timestamp,
            timestampString = DateUtils.timestampToDateTimeString(timestamp),
            value=cgmValue,
            rate=rate,
            trend=trend)
    }

    private fun getDexcomTrend(rate: Float): String {
        if (rate >= 3.5f) return "DoubleUp"
        if (rate >= 2.0f) return "SingleUp"
        if (rate >= 1.0f) return "FortyFiveUp"
        if (rate > -1.0f) return "Flat"
        if (rate > -2.0f) return "FortyFiveDown"
        if (rate > -3.5f) return "SingleDown"
        return if (java.lang.Float.isNaN(rate)) "" else "DoubleDown"
    }
}