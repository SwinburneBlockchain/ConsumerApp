package com.swinblockchain.consumerapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void scanProduct(View view) {
        Intent i = new Intent(MainActivity.this, ScanActivity.class);
        startActivity(i);
    }

    public void displayAck(View view) {
        Intent i = new Intent(MainActivity.this, AcknowledgementsActivity.class);
        startActivity(i);
    }

    /**
     * On back pressed does nothing to prevent unexpected results
     */
    @Override
    public void onBackPressed() {

    }
}
