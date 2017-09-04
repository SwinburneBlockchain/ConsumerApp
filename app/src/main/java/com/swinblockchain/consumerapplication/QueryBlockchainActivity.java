package com.swinblockchain.consumerapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

// TODO refactor and clean up entire class
public class QueryBlockchainActivity extends AppCompatActivity {


    private final String USER_AGENT = "Mozilla/5.0";
    private String accNo = "NXT-HP3G-T95S-6W2D-AEPHE"; // TODO Final?
    private String batchID; // TODO Final?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_blockchain);

        init();

        displayResults(testQueryBlockchain());
    }

    private void init() {
        Intent i = getIntent();

        accNo = i.getStringExtra("accNo");
        batchID = i.getStringExtra("batchID");
    }

    private void displayResults(String results) {
        Intent i = new Intent(QueryBlockchainActivity.this, InformationActivity.class);
        i.putExtra("result", results);
        startActivity(i);
    }

    /**
     * testScanProduct will emulate data from a QR code by sending a string containing values to the
     * blockchain.
     * TODO Remove test function
     */
    public String testQueryBlockchain() {
        String mainSecretPhrase = "curve%20excuse%20kid%20content%20gun%20horse%20leap%20poison%20girlfriend%20gaze%20poison%20comfort";
        String url = "http://ec2-52-63-253-206.ap-southeast-2.compute.amazonaws.com:6876/nxt?";

        QueryBlockchainActivity http = new QueryBlockchainActivity();

        System.out.println("Testing 1 - Send Http GET request");
        try {
            //http.getStatus(url);
            //http.sendToBlockchain(url, mainSecretPhrase, mainAccountNum);
            //http.createAccount(url, "averyhardtofindpswd");

            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8)
            {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                 return http.getFromBlockchain(url);

            } else {
                System.out.println("Error: SDK_INT < 8");
                return "Error";
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Error";
    }

    private void getStatus(String url) throws Exception {
        String USER_AGENT = "Mozilla/5.0";
        url += "requestType=getBlockchainStatus";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());

    }

    /*
    private void createAccount(String url, String password) throws Exception {
        String USER_AGENT = "Mozilla/5.0";
        url += "requestType=getAccountId&secretPhrase=" + password;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());

    }
    */

    @NonNull
    private String getFromBlockchain(String url) throws Exception {
        String USER_AGENT = "Mozilla/5.0";

        url += "&requestType=getBlockchainTransactions&account=" + accNo;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        System.out.println(response.toString());
        return response.toString();
    }

    /*
    private void sendToBlockchain(String url, String secretPhrase, String accountNum) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "requestType=sendMessage&secretPhrase=" + secretPhrase + "&recipient=" + accountNum + "&message={\"batchID\":\"001\",\"quantity\":\"100\",\"location\":\"Melbourne\"}&deadline=60&feeNQT=0";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }
    */

}
