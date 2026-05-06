package ru.mirea.milonov.accelerometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.milonov.accelerometer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    ActivityMainBinding binding;
    private SensorManager sensorManager;
    private	Sensor	accelerometer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sensorManager	=	(SensorManager)	getSystemService(SENSOR_SERVICE);
        accelerometer	=	sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,	accelerometer,	SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        sensorManager.registerListener(this,	accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if	(event.sensor.getType()	==	Sensor.TYPE_ACCELEROMETER)	{
            float	x	=	event.values[0];
            float	y	=	event.values[1];
            float	z	=	event.values[2];
            binding.textViewAzimuth.setText(String.format("Azimuth:	%s",	x));
            binding.textViewPitch.setText(String.format("Pitch:	%s",	y));
            binding.textViewRoll.setText(String.format("Roll:	%s",	z));
        }
    }
}