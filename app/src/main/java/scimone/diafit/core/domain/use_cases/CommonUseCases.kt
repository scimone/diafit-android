package scimone.diafit.core.domain.use_cases

data class CommonUseCases (
    val insertBolusValueUseCase: InsertBolusValueUseCase,
    val insertCarbsValueUseCase: InsertCarbsValueUseCase,
    val insertCGMValueUseCase: InsertCGMValueUseCase,
    val getAllCGMSinceUseCase: GetAllCGMSinceUseCase,
    val getAllBolusFromTodayUseCase: GetAllBolusFromTodayUseCase,
    val getAllCarbsFromTodayUseCase: GetAllCarbsFromTodayUseCase,
    val getLatestCGMUseCase: GetLatestCGMUseCase
)