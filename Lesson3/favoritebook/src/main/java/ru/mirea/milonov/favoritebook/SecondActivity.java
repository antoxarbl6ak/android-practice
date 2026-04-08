package ru.mirea.milonov.favoritebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            TextView devFavBook = findViewById(R.id.textViewDevFavBook);
            TextView devFavQuote = findViewById(R.id.textViewDevFavQuote);
            String book_name = extras.getString(MainActivity.BOOK_NAME_KEY);
            String quotes_name = extras.getString(MainActivity.QUOTES_KEY);
            devFavBook.setText("Книга: " + book_name);
            devFavQuote.setText("Цитата: " + quotes_name);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void sendData(View view) {
        EditText userFavBook = findViewById(R.id.textViewUserFavBook);
        EditText userFavQuote = findViewById(R.id.textViewUserFavQuote);
        Intent data = new Intent();
        data.putExtra(MainActivity.NAME, userFavBook.getText().toString());
        data.putExtra(MainActivity.QUOTE, userFavQuote.getText().toString());
        setResult(Activity.RESULT_OK, data);
        finish();
    }
}