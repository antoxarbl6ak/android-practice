package ru.mirea.milonov.multiactivity;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import ru.mirea.milonov.multiactivity.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivitySecondBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView textView = findViewById(R.id.textView);

        String text = (String) getIntent().getSerializableExtra("key");
        textView.setText(text);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart() - activity second");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause() - activity second");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop() - activity second");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart() - activity second");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy() - activity second");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume() - activity second");
    }
}





