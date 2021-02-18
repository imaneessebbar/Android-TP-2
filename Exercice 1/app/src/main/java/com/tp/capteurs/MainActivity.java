package com.tp.capteurs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.widget.LinearLayout.VERTICAL;
import static java.lang.Character.getType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorsList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuffer sensorDesc = new StringBuffer();
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(VERTICAL);
        ScrollView sv = new ScrollView(this);

         setContentView(layout);

        for (Sensor sensor : sensors) {
            sensorDesc.append("New sensor detected : \r\n");
            sensorDesc.append("\t\tName: " + sensor.getName() + "\r\n");
            sensorDesc.append("\t\tType: " + getType(sensor.getType()) + "\r\n");
            sensorDesc.append("\t\tVersion: " + sensor.getVersion() + "\r\n");
            sensorDesc.append("\t\tResolution (in the sensor unit): " + sensor.getResolution() + "\r\n");
            sensorDesc.append("\t\tPower in mA used by this sensor while in use" + sensor.getPower() + "\r\n");
            sensorDesc.append("\t\tVendor: " + sensor.getVendor() + "\r\n");
            sensorDesc.append("\t\tMaximum range of the sensor in the sensor's unit." + sensor.getMaximumRange() + "\r\n");
            sensorDesc.append("\t\tMinimum delay allowed between two events in microsecond »" + " or zero if this sensor only returns a value when the data its measuring changes » " +   sensor.getMinDelay() + "\r\n");
            sensorDesc.append("-------------------------------------------------------------------------------------------------- \r\n");



        }

        TextView t = new TextView(this);
        t.setText(sensorDesc.toString());

        sv.addView(t);
        layout.addView(sv);
        //Toast.makeText(this, sensorDesc.toString(), Toast.LENGTH_LONG).show();
        //setContentView(layout);
    }

}