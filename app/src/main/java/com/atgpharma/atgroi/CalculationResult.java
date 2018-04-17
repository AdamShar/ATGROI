package com.atgpharma.atgroi;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;

public class CalculationResult extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "CalculationResult";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation_result);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer =  findViewById(R.id.drawer_layout_calculation);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent i = getIntent();
        final Estimate estimate = (Estimate)i.getSerializableExtra("currentEstimate");
        SaveEstimate(estimate);
        final String email = estimate.getFormattedInfo();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent e = new Intent(Intent.ACTION_SENDTO);
                e.setData(Uri.parse("mailto:" + estimate.getEmail()));
                e.putExtra(Intent.EXTRA_SUBJECT, "ATG Pharma: Return on Investment");
                e.putExtra(Intent.EXTRA_TEXT, estimate.getFormattedInfo());
                if(e.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(e, "Send Email"));
                }
                Log.d(TAG, email);
            }
        });

        double ROI = 0;
        double PBP = 0;
        double ROI_dollars = 0;

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            ROI = extras.getDouble("ROI");
            PBP = extras.getDouble("PBP");
            ROI_dollars = extras.getDouble("ROI_Dollars");
        }

        final EditText ROI_out = findViewById(R.id.return_on_investment);
        final EditText PBP_out = findViewById(R.id.product_buyback_period);
        final EditText ROI_dollars_out = findViewById(R.id.return_on_investment_dollars);

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String PBP_string = String.format("%.1f", PBP);
        String ROI_string = String.format("%.2f%%", ROI);

        ROI_out.setText(ROI_string);
        PBP_out.setText(PBP_string);
        ROI_dollars_out.setText(formatter.format(ROI_dollars));

    }

    public void SaveEstimate (Estimate estimate) {
        try {
            Context context = getApplicationContext();
            File path = context.getFilesDir();

            File file = new File(path, "ATG.txt");
            file.createNewFile();

            FileOutputStream stream = openFileOutput("ATG.txt", MODE_APPEND);

            stream.write(estimate.getFormattedInfo().getBytes());

            Log.d(TAG, file.getPath());

            stream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout_calculation);
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout_calculation);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
