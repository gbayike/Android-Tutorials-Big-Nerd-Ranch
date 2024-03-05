package com.olugbayike.android.criminalintent

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.TimeZone

class TimePickerFragment: DialogFragment() {

    private  val args: TimePickerFragmentArgs by navArgs()
    val timeListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->

        val cal = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
        cal.set(Calendar.MINUTE, minute)

        cal.timeZone = TimeZone.getDefault()

        val format = SimpleDateFormat("hh:mm aa")
        val resultTime = format.format(cal.time)


//        val resultTime = SimpleDateFormat("HH:mm").format(hourOfDay,minute)

        setFragmentResult(
            REQUEST_KEY_TIME, bundleOf(
                BUNDLE_KEY_TIME to resultTime
            )
        )
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c = Calendar.getInstance()
        val format = SimpleDateFormat("hh:mm aa")
        val date = format.parse(args.time)
        c.time = date
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        return TimePickerDialog(requireContext(), timeListener, hour, minute, true)
    }

    companion object{
        const val REQUEST_KEY_TIME = "REQUEST_KEY_TIME"
        const val BUNDLE_KEY_TIME = "BUNDLE_KEY_TIME"
    }
}