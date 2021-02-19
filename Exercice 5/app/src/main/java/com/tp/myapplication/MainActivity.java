package com.tp.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  implements SensorEventListener {
    View v;
    private  SensorManager mSensorManager;
    private  Sensor mAccelerometer;
    private TextView t;
    private static final float SHAKE_THRESHOLD = 3.25f;
    private static final int MIN_TIME_BETWEEN_SHAKES_MILLISECS = 1000;
    private long mLastShakeTime;
    private CameraManager cameraManager;
    String cameraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        t = (TextView) findViewById(R.id.text);

        v = this.getWindow().getDecorView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAccelerometer == null) {
            t.setText("pas d'accelerometre");
        }
        if (mAccelerometer != null) {
            mSensorManager.registerListener(this, mAccelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
            cameraManager = (CameraManager)
                    getSystemService(Context.CAMERA_SERVICE);
            try {
                cameraId = cameraManager.getCameraIdList()[0];
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            shake(event);
        }
    }

    private void shake(SensorEvent event) {

        long curTime = System.currentTimeMillis();
        if ((curTime - mLastShakeTime) > MIN_TIME_BETWEEN_SHAKES_MILLISECS) {

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            double acceleration = Math.sqrt(x*x + y*y + z*z) - SensorManager.GRAVITY_EARTH;

            if (acceleration > SHAKE_THRESHOLD) {
                mLastShakeTime = curTime;
                Boolean light = true;
                if (light) {
                    try {
                        cameraManager.setTorchMode(cameraId, true);

                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    t.setText("Torch On");
                    light = false;
                } else {
                    try {
                        cameraManager.setTorchMode(cameraId, false);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                    t.setText("Torch Off");
                    light = true;
                }
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}