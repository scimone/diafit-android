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

    fun timestampToTimeFloat(timestamp: Long): Float {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
        }
        val hours = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)
        val seconds = calendar.get(Calendar.SECOND)
        return hours + minutes / 60f + seconds / 3600f
    }

    fun nowMinusXMinutes(minutes: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MINUTE, -minutes)
        return calendar.timeInMillis
    }

    fun timestampToISOString(timestamp: Long): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        return dateFormat.format(Date(timestamp))
    }
}