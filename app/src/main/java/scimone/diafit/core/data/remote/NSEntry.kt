package scimone.diafit.core.data.remote

import scimone.diafit.core.domain.model.CGMEntity

data class NSEntry(
    val _id: String,
    val date: Long,
    val dateString: String,
    val delta: Double,
    val device: String,
    val direction: String,
    val filtered: Int,
    val noise: Int,
    val rssi: Int,
    val sgv: Int,
    val sysTime: String,
    val type: String,
    val unfiltered: Int,
    val utcOffset: Int
) {
    fun toCGMEntity(): CGMEntity {
        return CGMEntity(
            timestamp = date,
            timestampString = dateString,
            value = sgv,
            rate = delta.toFloat()
        )
    }
}