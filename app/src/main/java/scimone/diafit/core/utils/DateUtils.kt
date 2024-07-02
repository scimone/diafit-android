package scimone.diafit.core.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    fun timestampToDateTimeString(timestamp: Long): String {
        return dateTimeFormat.format(Date(timestamp))
    }

    fun timestampToTimeString(timestamp: Long): String {
        return timeFormat.format(Date(timestamp))
    }
}