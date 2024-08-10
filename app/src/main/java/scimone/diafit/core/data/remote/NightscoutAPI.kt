package scimone.diafit.core.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface NightscoutAPI {

    @GET("/api/v1/entries/sgv.json")
    suspend fun getNSCGMBetween(
        @Query("find[dateString][\$gte]") startDate: String,
        @Query("find[dateString][\$lte]") endDate: String,
        @Query("count") count: Int = 100000
    ): List<NSEntry>

    companion object {
        const val API_KEY = ""
        const val BASE_URL = "https://gluco.mooo.com"
    }
}