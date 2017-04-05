package com.grinasia.transport.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

import com.grinasia.transport.Interface.SendDOBResults;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DOBPickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private SendDOBResults sendDOBResults;

    @Override
    public void onAttach(Activity activity) {
        if (getTargetFragment() == null) {
            sendDOBResults = (SendDOBResults) activity;
        }

        super.onAttach(activity);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();

        if (getArguments() != null && getArguments().containsKey("dob")) {
            try {
                SimpleDateFormat dobParseAndroid = new SimpleDateFormat("yyyy-MM-dd");
                String date = getArguments().getString("dob");
                c.setTime(dobParseAndroid.parse(date));
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);

        // Create a new instance of DatePickerDialog and return it
        return dialog;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Intent intent = new Intent();

        SimpleDateFormat parseSelectedDate = new SimpleDateFormat("yyyy-M-dd");
        SimpleDateFormat newFormatSelectedDate = new SimpleDateFormat("yyyy-MM-dd");

        String selected_date = year + "-" + String.valueOf(month + 1) + "-" + dayOfMonth;

        try {
            intent.putExtra("date_selected_dob_formatted", newFormatSelectedDate.format(parseSelectedDate.parse(selected_date)));
            if (getTargetFragment() != null){
                getTargetFragment().onActivityResult(getTargetRequestCode(), 25, intent);
            } else {
                sendDOBResults.sendResults(25, intent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        sendDOBResults = null;
        super.onDetach();
    }
}
