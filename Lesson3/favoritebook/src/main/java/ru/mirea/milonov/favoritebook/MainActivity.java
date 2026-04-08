package ru.mirea.milonov.favoritebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ActivityResultLauncher<Intent> activityResultLauncher;
    static final String BOOK_NAME_KEY = "book_name";
    static final String QUOTES_KEY = "quotes_name";
    static final String QUOTE = "quote";
    static final String NAME = "name";
    private TextView textViewUserBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewUserBook = findViewById(R.id.textView);
        ActivityResultCallback<ActivityResult> callback = new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    String userBook = data.getStringExtra(NAME);
                    String quote = data.getStringExtra(QUOTE);
                    textViewUserBook.setText(String.format("Ваша любимая книга: %s. Цитата: %s",  userBook, quote));
                }
            }
        };
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), callback);
    }

    public void getInfoAboutBook(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(BOOK_NAME_KEY, "Письма Баламута");
        intent.putExtra(QUOTES_KEY, "И капитал приобрести, и невинность соблюсти:)");
        activityResultLauncher.launch(intent);
    }
}