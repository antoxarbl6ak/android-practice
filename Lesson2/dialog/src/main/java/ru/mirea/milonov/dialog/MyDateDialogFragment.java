package ru.mirea.milonov.dialog;

import androidx.fragment.app.DialogFragment;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class MyDateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();

        return new DatePickerDialog(requireActivity(), this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Snackbar.make(requireActivity().findViewById(android.R.id.content), "Year " + year + ", month: " + month + ", day: " + dayOfMonth,
                Snackbar.LENGTH_LONG).show();
    }
}
