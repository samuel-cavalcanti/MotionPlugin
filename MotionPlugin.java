package MotionPlugin;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.Arrays;

import static android.util.Half.EPSILON;
import static java.lang.Math.sqrt;


public class MotionPlugin implements SensorEventListener {
    private Context context;

    private SensorManager sensorManager;
    private Sensor accelerometerSensor;
    private Sensor gyroscopeSensor;


    private Boolean sensorsEnable;
    private Boolean sensorsListening;

    public DeviceAcceleration acceleration;
    public DeviceAcceleration accelerationIncludingGravity;
    public DeviceRotationRate rotationRate;
    public DeviceRotationRate rotationRateNormalized;
    public static final float EPSILON = 0.000000001f;


    private double[] gravity;


    public MotionPlugin() {
        sensorManager = null;
        accelerometerSensor = null;
        gyroscopeSensor = null;
        acceleration = null;
        accelerationIncludingGravity = null;
        rotationRate = null;
        rotationRateNormalized = null;

        sensorsEnable = false;
        sensorsListening = false;

        gravity = new double[3];

        Arrays.fill(gravity, 0);


    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {


        switch (sensorEvent.sensor.getType()) {

            case Sensor.TYPE_ACCELEROMETER:
                final double alpha = 0.785336;

                // alpha is calculated as t / (t + dT)
                // with t, the low-pass filter's time-constant
                // and dT, the event delivery rate
                for (int i = 0; i < 3; i++) {
                    gravity[i] = alpha * gravity[i] + (1 - alpha) * sensorEvent.values[i];

                }


                acceleration = new DeviceAcceleration(sensorEvent.values[0] - gravity[0],
                        sensorEvent.values[1] - gravity[1], sensorEvent.values[2] - gravity[2]);


                accelerationIncludingGravity = new DeviceAcceleration(sensorEvent.values[0],
                        sensorEvent.values[1],
                        sensorEvent.values[2]);

                break;

            case Sensor.TYPE_GYROSCOPE:
                double[] axis = new double[3];

                // Calculate the angular speed of the sample
                double omegaMagnitude = sqrt(sensorEvent.values[0] * sensorEvent.values[0] +
                        sensorEvent.values[1] * sensorEvent.values[1] +
                        sensorEvent.values[2] * sensorEvent.values[2]);

                // Normalize the rotation vector if it's big enough to get the axis
                if (omegaMagnitude > EPSILON) {
                    for (int i = 0; i < 3; i++)
                        axis[i] = sensorEvent.values[i] / omegaMagnitude;

                    rotationRateNormalized = new DeviceRotationRate(axis[0], axis[1], axis[2]);
                } else
                    rotationRateNormalized = new DeviceRotationRate(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);


                rotationRate = new DeviceRotationRate(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
                break;


        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void onCreate(Activity activity, Bundle savedInstanceState) {
        context = activity;
        initSensors();
    }

    private void registerListeners() {
        if (sensorsListening)
            return;

        sensorManager.registerListener(this, accelerometerSensor, 2000);
        sensorManager.registerListener(this, gyroscopeSensor, 2000);
        sensorsListening = true;

    }

    private void unregisterListeners() {
        if (!sensorsListening)
            return;

        sensorManager.unregisterListener(this);
        sensorsListening = false;

    }

    private void initSensors() {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);


    }

    public void startEvents() {
        sensorsEnable = true;
        registerListeners();
    }

    public void stopEvents() {
        sensorsEnable = false;
        unregisterListeners();
    }


}
