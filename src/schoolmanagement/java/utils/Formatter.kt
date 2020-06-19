package schoolmanagement.java.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Formatter {
    fun dateTimeFormatOfNow(): String {
        val now = LocalDateTime.now()
        val format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//        val formatDateTime = now.format(format)
        return now.format(format)
    }
}

