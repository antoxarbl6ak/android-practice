package ru.mirea.milonov.thread;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.milonov.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        binding.buttonMirea.setOnClickListener(new	View.OnClickListener()	{
            @Override
            public void onClick(View v)	{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int result = 66 / 28;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.textView.setText("Mean num of classes per day: " + result);
                            }
                        });
                    }
                }).start();
            }
        });

        setContentView(binding.getRoot());
    }
}