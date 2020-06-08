package ru.schedule.util

import java.text.SimpleDateFormat
import java.util.*

fun Date.toHourMinutes(): String =
        SimpleDateFormat("HH:mm", Locale.getDefault()).format(this)