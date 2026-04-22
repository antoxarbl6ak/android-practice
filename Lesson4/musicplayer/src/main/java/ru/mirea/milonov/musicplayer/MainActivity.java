package ru.mirea.milonov.musicplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.TimeUnit;

import ru.mirea.milonov.musicplayer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MediaPlayer player;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            if (player != null && player.isPlaying()) {
                binding.seekBar.setProgress(player.getCurrentPosition());
                binding.textCurrentTime.setText(formatTime(player.getCurrentPosition()));
                handler.postDelayed(this, 1000);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        player = MediaPlayer.create(this, R.raw.legalizenukes);

        player.setOnPreparedListener(mp -> {
            binding.seekBar.setMax(mp.getDuration());
            binding.textTotalTime.setText(formatTime(mp.getDuration()));
        });

        binding.buttonPlay.setOnClickListener(v -> {
            player.start();
            handler.post(updateSeekBar);
        });

        binding.buttonPause.setOnClickListener(v -> player.pause());

        binding.buttonStop.setOnClickListener(v -> {
            player.stop();
            player = MediaPlayer.create(this, R.raw.legalizenukes);
            binding.seekBar.setProgress(0);
            binding.textCurrentTime.setText("0:00");
            binding.textTotalTime.setText(formatTime(player.getDuration()));
        });

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && player != null) {
                    // Seek MediaPlayer to new position and update current time
                    player.seekTo(progress);
                    binding.textCurrentTime.setText(formatTime(progress));
                }
            }

            // Not used, but required to override
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            // Not used, but required to override
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        setContentView(binding.getRoot());
    }

    private String formatTime(int milliseconds) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateSeekBar);
        if (player != null) {
            player.release();
        }
    }
}