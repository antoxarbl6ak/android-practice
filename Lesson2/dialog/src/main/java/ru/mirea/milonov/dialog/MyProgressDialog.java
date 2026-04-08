package ru.mirea.milonov.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class MyProgressDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog progressDialog = new ProgressDialog(requireActivity());
        progressDialog.setTitle("Loading");
        return progressDialog;
    }

}
