package com.atgpharma.atgroi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SavedEstimates extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_estimates);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout_saved);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ListView listView = findViewById(R.id.list_view);

        ArrayList<Estimate> estimates = getEstimates();

        ArrayAdapter<Estimate> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, estimates);

        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout_saved);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.new_roi) {
            Intent i = new Intent(getApplicationContext(), InfoInput.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else if (id == R.id.saved) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout_saved);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public ArrayList<Estimate> getEstimates() {
        try {
            Context context = getApplicationContext();
            File path = context.getFilesDir();

            FileReader file = new FileReader(path + "/ATG.txt");
            BufferedReader bufferedReader = new BufferedReader(file);
            String myLine;

            ArrayList<Estimate> estimates = new ArrayList<>();
            String[] inputs = new String[11];
            String value;

            while ((myLine = bufferedReader.readLine()) != null){
                String[] line = new String[2];
                line = myLine.split("=");

                if(line.length > 1){
                    value = line[1];
                }
                else {
                    value = " ";
                }
                switch (line[0]) {
                    case "name":
                        inputs[0] = value;
                        break;
                    case "company":
                        inputs[1] = value;
                        break;
                    case "email":
                        inputs[2] = value;
                        break;
                    case "phone":
                        inputs[3] = value;
                        break;
                    case "num_operators":
                        inputs[4] = value;
                        break;
                    case "hourly_pay":
                        inputs[5] = value;
                        break;
                    case "hours_per_week":
                        inputs[6] = value;
                        break;
                    case "bottles_per_operator":
                        inputs[7] = value;
                        break;
                    case "roi_percent":
                        inputs[8] = value;
                        break;
                    case "pbp":
                        inputs[9] = value;
                        break;
                    case "roi_dollars":
                        inputs[10] = value;
                        Estimate estimate = new Estimate(inputs);
                        estimates.add(estimate);
                        Log.d("W/E", estimate.toString());
                        break;
                }
            }

            return estimates;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
