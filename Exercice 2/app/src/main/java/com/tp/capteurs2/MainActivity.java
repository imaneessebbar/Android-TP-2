package com.tp.capteurs2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static android.view.View.TEXT_ALIGNMENT_CENTER;
import static android.widget.LinearLayout.VERTICAL;
import static java.lang.Character.getType;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        String[] capts =  getResources().getStringArray(R.array.capteurs); /**Les valeurs affichées a l'utilisateur*/
        int[] ints =  getResources().getIntArray(R.array.ints);/**Ls valeurs utilisées dans la méthode de verification */


        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(VERTICAL);

        setContentView(layout);

        /**Message : Veuillez choisir un capteur**/
        TextView tt = new TextView(this);
        tt.setText(R.string.txt);
        tt.setTextSize(30);
        tt.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));

        layout.addView(tt);

        /********* La liste des capteurs ********/
        Spinner sp = new Spinner(this);
        sp.setBackgroundResource(android.R.drawable.btn_dropdown);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,capts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));

        sp.setAdapter(adapter);
        layout.addView(sp);

        /**Le boutton**/

        Button button = new Button(this);
        button.setBackgroundColor(getResources().getColor(R.color.purple_500));
        button.setText(R.string.chercher);
        button.setTextColor(getResources().getColor(R.color.white));
        button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView t = new TextView(this);
        t.setTextSize(30);
        t.setGravity(TEXT_ALIGNMENT_CENTER);
        t.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));

        /********************************* Button onClick event **********************************/

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                int i = ints[sp.getSelectedItemPosition()];
                Sensor defaultProximitySensor = sensorManager.getDefaultSensor(i);
                String s ;

                if(defaultProximitySensor == null){
                    s = getResources().getString(R.string.ind,sp.getSelectedItem().toString());
                    t.setText(s);
                }else{
                    s = getResources().getString(R.string.dis,sp.getSelectedItem().toString());
                    t.setText(s);
                }

            }
        });

        layout.addView(button);
        layout.addView(t);

    }

}