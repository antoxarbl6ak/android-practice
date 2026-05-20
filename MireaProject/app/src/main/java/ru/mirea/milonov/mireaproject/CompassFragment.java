package ru.mirea.milonov.mireaproject;

import static android.content.Context.SENSOR_SERVICE;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import ru.mirea.milonov.mireaproject.databinding.FragmentCompassBinding;


public class CompassFragment extends Fragment implements SensorEventListener {

    private FragmentCompassBinding binding;
    private SensorManager sensorManager;
    private Sensor magnetometer;
    private ImageView compass;

    public CompassFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompassBinding.inflate(inflater, container, false);
        sensorManager = (SensorManager) requireActivity().getSystemService(SENSOR_SERVICE);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        compass = binding.compass;
        return binding.getRoot();
    }
    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            switch (accuracy) {
                case SensorManager.SENSOR_STATUS_UNRELIABLE:
                    Log.d("Sensor", "Компас: ненадежные данные");
                    break;
                case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                    Log.d("Sensor", "Компас: высокая точность");
                    break;
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float angle = (float) Math.toDegrees(Math.atan2(event.values[1], event.values[0]));
            compass.setRotation(angle);
        }
    }
}