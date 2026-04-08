package ru.mirea.milonov.multiactivity;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate() - activity main");
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onClickNewActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
        intent.putExtra("key", "MIREA - МИЛОНОВ АНТОН ГРИГОРЬЕВИЧ");
        startActivity(intent);
    }

    public void onClickButton2(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        EditText editText = findViewById(R.id.edittext);
        intent.putExtra("key", String.valueOf(editText.getText()));
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart() - activity main");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause() - activity main");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop() - activity main");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart() - activity main");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy() - activity main");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume() - activity main");
    }
}