package ru.mirea.milonov.toastapp;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onClickButtontoast(View view) {
        EditText edittext = findViewById(R.id.editText);
        Toast toast = new Toast(this);
        String message = "«СТУДЕНТ № 7 ГРУППА БСБО-51-24\nКоличество символов - " + edittext.getText().length();

        TextView textView = new TextView(this);
        textView.setText(message);
        textView.setTextColor(0xFFFFFFFF); // белый цвет
        textView.setBackgroundColor(0xDD000000); // полупрозрачный черный фон
        textView.setPadding(50, 30, 50, 30);
        textView.setTextSize(12);
        textView.setMaxLines(5);
        textView.setGravity(Gravity.CENTER);

        toast.setView(textView);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 100);
        toast.show();
    }
}