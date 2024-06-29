package scimone.diafit.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(
    indices = [Index(value = ["timestamp"], unique = true)]
)
data class CGMTable(
    val timestamp: Long,
    val cgmValue: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
