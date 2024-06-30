package scimone.diafit.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    indices = [Index(value = ["timestamp"], unique = true)]
)
data class CGMEntity(
    val timestamp: Long,
    val value: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
