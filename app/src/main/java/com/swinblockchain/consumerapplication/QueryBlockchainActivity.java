package com.swinblockchain.consumerapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// TODO refactor and clean up entire class

/**
 * Class is used to query the blockchain and return the response.
 */
public class QueryBlockchainActivity extends AppCompatActivity {


    private final String USER_AGENT = "Mozilla/5.0";
    private final String NODE_URL = "http://ec2-52-63-253-206.ap-southeast-2.compute.amazonaws.com:6876/nxt?";
    private String accNo; // TODO Final?
    private String batchID; // TODO Final?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_blockchain);

        init();

        /*
        JsonObject jsonResponse = null;
        try {
            objectResponse = Json.parse(testQueryBlockchain(accNo)).asObject();
        } catch (ParseException pe) {
            System.out.println("Could not parse reponse to JsonObject...");
        }
        String stringResponse = objectResponse.getString("message", "Error");

        displayResults(jsonResponse);
        */
    }

    private void init() {
        Intent i = getIntent();

        accNo = i.getStringExtra("accNo");
        batchID = i.getStringExtra("batchID");
    }

    private void displayResults(JSONObject jsonResponse) {
        Intent i = new Intent(QueryBlockchainActivity.this, InformationActivity.class);
        i.putExtra("result", jsonResponse);
        startActivity(i);
    }

    /**
     * testScanProduct will emulate data from a QR code by sending a string containing values to the
     * blockchain
     *
     * @param accNo The account number of the QR code to query
     * TODO Remove test function
     */
    public String testQueryBlockchain(String accNo) {

            System.out.println("Testing 1 - Send Http GET request");
            try {

                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                // Check if the Android SDK supports the Threadpolicy
                if (SDK_INT > 8) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    // Query blockchain
                    return getFromBlockchain();

                } else {
                    System.out.println("Error: SDK_INT < 8");
                    return "Error"; // TODO // FIXME: 5/9/17
                }

            } catch (Exception e) { // TODO // FIXME: 5/9/17
                e.printStackTrace();
            }
            return "Error"; // TODO // FIXME: 5/9/17
        }

    /**
     * Used to query the blockchain and returns the JSON object
     *
     * @return String containing the respose JSON
     * @throws Exception If url issues
     */
    private String getFromBlockchain() throws Exception {
        String queryURL = NODE_URL + "&requestType=getBlockchainTransactions&account=" + accNo;

        URL obj = new URL(queryURL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + queryURL);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println("Response: " + response.toString());
        return response.toString();
    }

}
