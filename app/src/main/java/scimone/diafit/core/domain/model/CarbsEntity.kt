package scimone.diafit.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CarbsEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val timestamp: Long,
    val timestampString: String = "",
    val amount: Int,
    val eventType: String
)