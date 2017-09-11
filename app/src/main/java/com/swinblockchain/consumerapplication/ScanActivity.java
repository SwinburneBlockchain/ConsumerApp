package com.swinblockchain.consumerapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanActivity extends AppCompatActivity {

    String accNo;
    String batchID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        scan();
    }

    /**
     * Called when the scan activity finishes
     *
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        // Get variables
        accNo = scanningResult.getContents().toString().split(",")[0];
        batchID = scanningResult.getContents().toString().split(",")[1];

        changeActivity(accNo, batchID);
    }

    /**
     * Scans a QR code
     */
    private void scan() {
        IntentIntegrator scan = new IntentIntegrator(ScanActivity.this);

        // Display message is Scanner application is not installed on the device
        scan.setMessage("Scanner needs to be downloaded in order to use this application.");
        scan.initiateScan();

        // Currently testing variables
        //accNo = "NXT-HP3G-T95S-6W2D-AEPHE";
        //batchID = "001";
    }

    /**
     * Used to change the activity
     *
     * @param accNo The account numner
     * @param batchID The product batchID
     */
    private void changeActivity(String accNo, String batchID) {
        Intent i = new Intent(ScanActivity.this, QueryBlockchainActivity.class);
        i.putExtra("accNo", accNo);
        i.putExtra("batchID", batchID);

        startActivity(i);
    }

}
