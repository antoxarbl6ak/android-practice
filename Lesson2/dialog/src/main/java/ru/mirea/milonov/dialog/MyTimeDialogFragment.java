package ru.mirea.milonov.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class MyTimeDialogFragment extends DialogFragment implements  TimePickerDialog.OnTimeSetListener{
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();

        return new TimePickerDialog(requireActivity(), this,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Snackbar.make(requireActivity().findViewById(android.R.id.content), "Hour: " + hourOfDay + ", minute: " + minute,
                Snackbar.LENGTH_LONG).show();
    }

}
