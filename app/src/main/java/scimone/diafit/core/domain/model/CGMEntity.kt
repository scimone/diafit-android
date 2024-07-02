package scimone.diafit.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    indices = [Index(value = ["timestamp"], unique = true)]
)
data class CGMEntity(
    val timestamp: Long,
    val timestampString: String = "",
    val value: Int,
    val rate: Float,
    val trend: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
