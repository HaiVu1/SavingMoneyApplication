package haivv.learning.savingmoney.ui.datetime

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

const val DATE_FORMAT = "dd/MM/YYYY"

@SuppressLint("SimpleDateFormat", "WeekBasedYear")
@RequiresApi(Build.VERSION_CODES.O)
fun convertDateToString(day: Int, month: Int, year: Int): String {
    val localDate: LocalDate = LocalDate.of(year, month +1, day)
    val simpleDateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT)
    return localDate.format(simpleDateFormat)
}