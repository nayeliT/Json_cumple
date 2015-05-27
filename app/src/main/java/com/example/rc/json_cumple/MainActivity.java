package com.example.rc.json_cumple;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends ListActivity {

    private ProgressDialog pDialog;

    // URL to get contacts JSON
    private static String url = "http://iin8.szhernandez.dx.am/aabbcc_nayeli.json";


    // JSON Node names
    private final String KEY_TAG = "festejo"; // parent node
    private final String KEY_NOMBRE = "nombre";
    private final String KEY_FECHA = "fecha";


    // contacts JSONArray
    JSONArray festejo = null;

    // Hashmap for ListView
    ArrayList<HashMap<String, String>> festejoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        festejoList = new ArrayList<HashMap<String, String>>();

        ListView lv = getListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // getting values from selected ListItem
                String nom = ((TextView) view.findViewById(R.id.nombre2_2)).getText().toString();
                String fec = ((TextView) view.findViewById(R.id.fecha2_2)).getText().toString();


                // Starting new intent
                Intent in = new Intent(getApplicationContext(), individual_cumple.class);
                in.putExtra(KEY_NOMBRE, nom);
                in.putExtra(KEY_FECHA, fec);
                startActivity(in);

            }
        });

        // Calling async task to get json
        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    festejo = jsonObj.getJSONArray(KEY_TAG);

                    // looping through All Contacts
                    for (int i = 0; i < festejo.length(); i++) {
                        JSONObject c = festejo.getJSONObject(i);

                        // Phone node is JSON Object
                        String nom = c.getString(KEY_NOMBRE);
                        String fec = c.getString(KEY_FECHA);

                        // tmp hashmap for single contact
                        HashMap<String, String> prt = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        prt.put(KEY_NOMBRE, nom);
                        prt.put(KEY_FECHA, fec);

                        // adding contact to contact list
                        festejoList.add(prt);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, festejoList,
                    R.layout.list_cumple, new String[] {KEY_NOMBRE, KEY_FECHA}, new int[] {R.id.nombre2_2,R.id.fecha2_2});

            setListAdapter(adapter);
        }
    }
}
