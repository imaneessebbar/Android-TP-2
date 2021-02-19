package com.tp.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  implements SensorEventListener {
    View v;
    private  SensorManager mSensorManager;
    private  Sensor mAccelerometer;
    private TextView t;
    float [] history = new float[2];
    String [] direction = {"NONE","NONE"};

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
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        directionchange(event);
    }

    private void directionchange(SensorEvent event) {

        float xChange = history[0] - event.values[0];
        float yChange = history[1] - event.values[1];

        history[0] = event.values[0];
        history[1] = event.values[1];

        if (xChange > 2){
            direction[0] = "GAUCHE";
        }
        else if (xChange < -2){
            direction[0] = "DROITE";
        }

        if (yChange > 2){
            direction[1] = "BAS";
        }
        else if (yChange < -2){
            direction[1] = "HAUT";
        }

        String res = "DIRECTION : " + direction[0] + ",  " +  direction[1];
        t.setText(res);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}