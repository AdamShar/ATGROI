package com.atgpharma.atgroi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.text.NumberFormat;

public class CalculationResult extends AppCompatActivity {

    private static final String TAG = "CalculationResult";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        Estimate estimate = (Estimate)i.getSerializableExtra("currentEstimate");
        final String email = estimate.getFormattedInfo();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

        ROI = ROI * 100;

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String PBP_string = String.format("%.1f", PBP);
        String ROI_string = String.format("%.2f%%", ROI);

        ROI_out.setText(ROI_string);
        PBP_out.setText(PBP_string);
        ROI_dollars_out.setText(formatter.format(ROI_dollars));

    }

    public void SaveEstimate (Estimate estimate) {
        Context context = getApplicationContext();
        File path = context.getFilesDir();

        File file = new File(path, "ATG.txt");

    }

}
