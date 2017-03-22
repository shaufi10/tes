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
import com.grinasia.transport.Utils.Strings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 * Created by dark_coder on 3/17/2017.
 */

public class DOBFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private SendDOBResults sendDOBResults;

    @Override
    public void onAttach(Activity activity) {
        if(getTargetFragment() == null) {
            sendDOBResults = (SendDOBResults) activity;
        }

        super.onAttach(activity);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle SavedInstanceState) {
        final  Calendar cc = Calendar.getInstance();

        if (getArguments() != null && getArguments().containsKey("dob")) {
            try {
                SimpleDateFormat dobParseAndroid = new SimpleDateFormat("yyyy-MM-dd");
                String date = getArguments().getString("dob");
                cc.setTime(dobParseAndroid.parse(date));
            } catch (ParseException e){
                e.printStackTrace();
            }
        }
        int year = cc.get(Calendar.YEAR);
        int month = cc.get(Calendar.MONTH);
        int day = cc.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);

        return dialog;
    }

    @Override
    public void onDateSet (DatePicker view, int year, int month, int dayOfMonth){
        Intent intent = new Intent();

        SimpleDateFormat parseSelectedDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newFormatSelectedDate = new SimpleDateFormat("yyyy-MM-dd");

        String selected_date = year + "-" + String.valueOf(month + 1) + "-" + dayOfMonth;
        try {
            intent.putExtra("data selected dob formatted", newFormatSelectedDate.format(parseSelectedDate.parse(selected_date)));
            if (getTargetFragment() != null) {
                getTargetFragment().onActivityResult(getTargetRequestCode(), 25, intent);
            }else {
                sendDOBResults.sendResults(25, intent);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDetach(){
        sendDOBResults = null;
        super.onDetach();
    }
}
