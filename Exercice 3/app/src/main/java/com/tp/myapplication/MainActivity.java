package com.tp.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
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
        if (mAccelerometer == null) { t.setText("ERROR SENSOR"); }
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
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            changeCouleur(event);
        }
    }

    private void changeCouleur(SensorEvent event) {
        double inf = 0.0675 ;
        double sup = 3 ;
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        double valeur = Math.sqrt(x*x + y*y + z*z) - SensorManager.GRAVITY_EARTH;
        t.setText("x = "+x+", y = "+y+ ", z ="+z+"\n valeur ="+valeur);

        if(valeur >= inf && valeur<= sup){
            v.setBackgroundColor(getResources().getColor(R.color.green));
        } else {
            if (valeur < inf){
                v.setBackgroundColor(getResources().getColor(R.color.red));
            }else{
                if (valeur > sup){
                    v.setBackgroundColor(getResources().getColor(R.color.black));}
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}