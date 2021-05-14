package haivv.learning.savingmoney.ui.datetime

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import java.util.*


fun createNormalDatePicker(context: Context, listener: DatePickerDialog.OnDateSetListener): Dialog {
    // Use the current date as the default date in the picker
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    // Create a new instance of DatePickerDialog and return it
    return DatePickerDialog(context, listener, year, month, day)
}

fun createDatePickerMaxDate(
    context: Context,
    listener: DatePickerDialog.OnDateSetListener,
    maxDate: Date
): Dialog {

    // Use the current date as the default date in the picker
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    // Create a new instance of DatePickerDialog and return it
    val datePickerFragment = DatePickerDialog(context, listener, year, month, day)
    datePickerFragment.datePicker.maxDate = maxDate.time

    return datePickerFragment
}

fun createDatePickerMaxMinDate(
    context: Context,
    listener: DatePickerDialog.OnDateSetListener,
    maxDate: Date,
    minDate: Date
): Dialog {

    // Use the current date as the default date in the picker
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    // Create a new instance of DatePickerDialog and return it
    val datePickerFragment = DatePickerDialog(context, listener, year, month, day)
    datePickerFragment.datePicker.maxDate = maxDate.time
    datePickerFragment.datePicker.minDate = minDate.time

    return datePickerFragment
}