package scimone.diafit.features.home.presentation

import scimone.diafit.core.domain.model.BolusEntity
import scimone.diafit.core.domain.model.CGMEntity
import scimone.diafit.core.domain.model.CarbsEntity

data class HomeState(
    val allBolusFromToday: List<BolusEntity> = emptyList(),
    val allCarbsFromToday: List<CarbsEntity> = emptyList(),
    val latestCGM: CGMEntity? = null,
    val timeSinceLastCGM: Int = 0
)