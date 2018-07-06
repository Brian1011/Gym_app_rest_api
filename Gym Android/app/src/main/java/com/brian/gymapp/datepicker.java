package com.brian.gymapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;


public class datepicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //use the current date as the default date in the picker
        final java.util.Calendar c  = java.util.Calendar.getInstance();
        int year = c.get(java.util.Calendar.YEAR);
        int month =  c.get(java.util.Calendar.MONTH);
        int day =  c.get(java.util.Calendar.DAY_OF_MONTH);

        //Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month,day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        //Do something with the date chosen by the user
        new_workout activity = (new_workout) getActivity();
        activity.processDatePickerResult(year,month,dayOfMonth);
    }
}
