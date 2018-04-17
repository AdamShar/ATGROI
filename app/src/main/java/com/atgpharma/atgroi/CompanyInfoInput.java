package com.atgpharma.atgroi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class CompanyInfoInput extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "CompanyInfoInput";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_info_input);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout_company);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final Spinner machine_spinner = findViewById(R.id.machine);
        final Spinner currency_spinner = findViewById(R.id.currency);

        final EditText num_operators = findViewById(R.id.num_operators);
        final EditText hourly_pay = findViewById(R.id.hourly_pay);
        final EditText hours_per_week  = findViewById(R.id.hours_per_week);
        final EditText num_bottles = findViewById(R.id.num_bottles);


        final Button button = findViewById(R.id.calculate);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "ADAMLOG");
                try {
                    int machine = machine_spinner.getSelectedItemPosition();
                    int currency = currency_spinner.getSelectedItemPosition();
                    calculateEstimate(currency, machine, Double.parseDouble(num_operators.getText().toString()), Double.parseDouble(hourly_pay.getText().toString()), Double.parseDouble(hours_per_week.getText().toString()), Double.parseDouble(num_bottles.getText().toString()));
                }catch (NullPointerException npe) {
                    Snackbar.make(v, "You cannot leave a field blank", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }catch (NumberFormatException nfe) {
                    Snackbar.make(v, "You cannot leave a field blank", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    public void calculateEstimate(int currency, int machine, double num_operators, double hourly_pay, double hours_per_week, double num_bottles) {

        String temp = String.format("%d, %d, %f, %f, %f, %f", currency, machine, num_operators, hourly_pay, hours_per_week, num_bottles);

        Log.d(TAG, temp);

        double set_operators = num_operators;
        double set_bottles = num_bottles;

        double machine_cost = 0;
        double machine_output = 0;

        if(currency == 0) {
            switch (machine) {
                case 0:
                    machine_cost = 16925;
                    machine_output = 1200;
                    break;
                case 1:
                    machine_cost = 19275;
                    machine_output = 1800;
                    break;
                case 2:
                    machine_cost = 4000;
                    machine_output = 500;
                    break;
                case 3:
                    machine_cost = 5800;
                    machine_output = 900;
                    break;
            }
        }else if(currency == 1) {
            switch (machine) {
                case 0:
                    machine_cost = 16150;
                    machine_output = 1200;
                    break;
                case 1:
                    machine_cost = 18350;
                    machine_output = 1800;
                    break;
                case 2:
                    machine_cost = 3400;
                    machine_output = 500;
                    break;
                case 3:
                    machine_cost = 5315;
                    machine_output = 900;
                    break;
            }
        }

        double bottle_out = num_operators * num_bottles * 250;

        if(num_operators  >= 2) {
            num_operators--;
        }


        double avg_salary = num_operators * hours_per_week * hourly_pay * 50;

        double ROI = (avg_salary - ((bottle_out/machine_output)*hourly_pay) - machine_cost)/machine_cost;

        double PBP = (machine_cost/(avg_salary - ((bottle_out/machine_output)*hourly_pay)))*12;

        double ROI_Dollars = ROI * machine_cost;

        ROI = ROI * 100;

        String tempstring = String.format("ROI = %f, PBP = %f, ROI $ = %f", ROI, PBP, ROI_Dollars);

        Log.d(TAG, tempstring);

        Intent i = getIntent();
        Estimate estimate = (Estimate)i.getSerializableExtra("currentEstimate");

        if(num_operators >= 2)
        estimate.setNum_operators(set_operators);
        estimate.setHourly_pay(hourly_pay);
        estimate.setHours_per_week(hours_per_week);
        estimate.setBottles_per_operator(set_bottles);

        estimate.setRoi_percent(ROI);
        estimate.setPbp(PBP);
        estimate.setRoi_dollars(ROI_Dollars);

        Intent a = new Intent(getApplicationContext(), CalculationResult.class);
        a.putExtra("ROI", ROI);
        a.putExtra("PBP", PBP);
        a.putExtra("ROI_Dollars", ROI_Dollars);
        a.putExtra("currentEstimate", estimate);
        startActivity(a);

        return;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout_company);
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
            Intent i = new Intent(getApplicationContext(), SavedEstimates.class);
            startActivity(i);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout_company);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
