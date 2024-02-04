package com.abshtyfikant.fancal.data

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Dates(
    val maximum: String,
    val minimum: String
)

//Getting current date
val dateCurrent = LocalDate.now()
val datePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")
var dateTomorrow = dateCurrent.plusDays(1).format(datePattern)
var dateLater = dateCurrent.plusMonths(3).format(datePattern)