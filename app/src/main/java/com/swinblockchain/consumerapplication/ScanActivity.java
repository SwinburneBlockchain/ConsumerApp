package com.swinblockchain.consumerapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ScanActivity extends AppCompatActivity {

    String accNo;
    String batchID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
    }

    /**
     * Simulates scanning a QR code
     * TODO Replace with actual QR code scanner
     */
    private void scan() {
        // TODO Scan QR code and read data into variables
        accNo = "NXT-HP3G-T95S-6W2D-AEPHE";
        batchID = "001";
    }

    public void testScanQRCode(View view) {
        Intent i = new Intent(ScanActivity.this, QueryBlockchainActivity.class);

        scan();

        // Send scanned values to query the blockchain
        i.putExtra("accNo", accNo);
        i.putExtra("batchID", batchID);

        startActivity(i);
    }


}
