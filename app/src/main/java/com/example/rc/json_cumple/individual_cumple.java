package com.example.rc.json_cumple;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class individual_cumple extends Activity {

    static final String KEY_NOMBRE = "nombre";
    static final String KEY_FECHA = "fecha";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_cumple);

        // getting intent data
        Intent in = getIntent();

        // Get JSON values from previous intent
        String nombre = in.getStringExtra(KEY_NOMBRE);
        String fecha = in.getStringExtra(KEY_FECHA);

        // Displaying all values on the screen
        TextView nom = (TextView) findViewById(R.id.nombre2);
        TextView fec = (TextView) findViewById(R.id.fecha2);

        nom.setText(nombre);
        fec.setText(fecha);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_individual_cumple, menu);
        return true;
    }

}

