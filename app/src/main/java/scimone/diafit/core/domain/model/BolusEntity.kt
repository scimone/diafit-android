package scimone.diafit.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BolusEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val timestamp: Long,
    val timestampString: String = "",
    val amount: Double,
    val eventType: String,
    val isSMB: Boolean,
    val pumpType: String,
    val pumpSerial: String
)