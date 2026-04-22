package ru.mirea.milonov.mireaproject;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import java.util.concurrent.TimeUnit;

import ru.mirea.milonov.mireaproject.databinding.FragmentMediaPlayerBinding;

public class MediaPlayerFragment extends Fragment {

    private FragmentMediaPlayerBinding binding;
    private MusicService musicService;
    private boolean bound = false;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private final Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            if (musicService != null && musicService.isPlaying()) {
                binding.seekBar.setProgress(musicService.getCurrentPosition());
                binding.textCurrentTime.setText(formatTime(musicService.getCurrentPosition()));
                handler.postDelayed(this, 1000);
            }
        }
    };

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.LocalBinder binder = (MusicService.LocalBinder) service;
            musicService = binder.getService();
            bound = true;

            binding.seekBar.setMax(musicService.getDuration());
            binding.textTotalTime.setText(formatTime(musicService.getDuration()));
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
            bound = false;
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMediaPlayerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent intent = new Intent(requireContext(), MusicService.class);
        requireContext().startService(intent);
        requireContext().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        binding.buttonPlay.setOnClickListener(v -> {
            if (bound && musicService != null) {
                musicService.play();
                handler.post(updateSeekBar);
            }
        });

        binding.buttonPause.setOnClickListener(v -> {
            if (bound && musicService != null) {
                musicService.pause();
            }
        });

        binding.buttonStop.setOnClickListener(v -> {
            if (bound && musicService != null) {
                musicService.stop();
                binding.seekBar.setProgress(0);
                binding.textCurrentTime.setText("0:00");
                binding.seekBar.setMax(musicService.getDuration());
                binding.textTotalTime.setText(formatTime(musicService.getDuration()));
            }
        });

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && bound && musicService != null) {
                    musicService.seekTo(progress);
                    binding.textCurrentTime.setText(formatTime(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(updateSeekBar);
        if (bound) {
            requireContext().unbindService(serviceConnection);
            bound = false;
        }
        binding = null;
    }

    private String formatTime(int milliseconds) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    public MediaPlayerFragment() {
        // Required empty public constructor
    }
}