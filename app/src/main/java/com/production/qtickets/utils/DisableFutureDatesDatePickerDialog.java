package com.production.qtickets.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import java.util.Calendar;

public class DisableFutureDatesDatePickerDialog extends DatePickerDialog {

    private final Calendar calendarMaxDate;

    public DisableFutureDatesDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
        super(context, callBack, year, monthOfYear, dayOfMonth);

        // Get the current date
        Calendar calendarToday = Calendar.getInstance();

        // Create a copy of the current date to avoid modifying it
        calendarMaxDate = Calendar.getInstance();
        calendarMaxDate.set(calendarToday.get(Calendar.YEAR),
                calendarToday.get(Calendar.MONTH),
                calendarToday.get(Calendar.DAY_OF_MONTH));

        // Set the maximum date to today to disable future dates
        getDatePicker().setMaxDate(calendarMaxDate.getTimeInMillis());
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int dayOfMonth) {
        // When the date changes, check if the selected date is in the future
        // If it is, set the selected date to the maximum allowed date (today)
        if (calendarMaxDate != null) {
            Calendar calendarSelected = Calendar.getInstance();
            calendarSelected.set(year, month, dayOfMonth);

            if (calendarSelected.after(calendarMaxDate)) {
                // The selected date is in the future, so reset it to today's date
                view.updateDate(calendarMaxDate.get(Calendar.YEAR),
                        calendarMaxDate.get(Calendar.MONTH),
                        calendarMaxDate.get(Calendar.DAY_OF_MONTH));
            }
        }
    }
}