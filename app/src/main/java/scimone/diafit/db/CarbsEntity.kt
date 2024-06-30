package scimone.diafit.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CarbsEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val timestamp: Long,
    val amount: Double,
    val eventType: String
)