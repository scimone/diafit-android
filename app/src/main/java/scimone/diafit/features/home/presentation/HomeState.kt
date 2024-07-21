package scimone.diafit.features.home.presentation

import scimone.diafit.core.domain.model.BolusEntity
import scimone.diafit.core.domain.model.CGMEntity
import scimone.diafit.core.domain.model.CarbsEntity
import scimone.diafit.core.presentation.model.CGMChartData

data class HomeState(
    val allBolusFromToday: List<BolusEntity> = emptyList(),
    val allCarbsFromToday: List<CarbsEntity> = emptyList(),
    val allCGMSince24h: List<CGMChartData> = emptyList(),
    val latestCGM: CGMEntity? = null,
    val timeSinceLastCGM: String = "",
)