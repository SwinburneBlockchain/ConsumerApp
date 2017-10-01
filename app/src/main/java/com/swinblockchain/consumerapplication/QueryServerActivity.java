package com.swinblockchain.consumerapplication;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.R.id.list;

/**
 * Class is used to query the blockchain and return the response.
 */
public class QueryServerActivity extends AppCompatActivity {

    Scan s;
    ArrayList<Producer> prodArrayList = new ArrayList<>();
    String URL = "http://ec2-54-153-202-123.ap-southeast-2.compute.amazonaws.com:3000/productInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_blockchain);

        init();

        makeRequest();
    }

    private void init() {
        s = getIntent().getParcelableExtra("scan");
        makeRequest();
    }

    /**
     * Create request to send to the QR code generating web server
     */
    private void makeRequest() {
        RequestQueue queue = Volley.newRequestQueue(QueryServerActivity.this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                JsonObject returnedJsonObject = stringToJsonObject(response);

                try {
                    // May not be required
                    String qrAddress = returnedJsonObject.getString("qrAddress", "qrAddressError");
                    String qrPubKey = returnedJsonObject.getString("qrPubKey", "qrPubKeyError");
                    String producerAddr = returnedJsonObject.getString("producerAddr", "producerAddrError");

                    // Single product information
                    String productName = returnedJsonObject.getString("productName", "productNameError");
                    String productId = returnedJsonObject.getString("productId", "productIdError");
                    String batchId = returnedJsonObject.getString("batchId", "batchIdError");

                    //TODO fix this name
                    JsonArray prodArray = returnedJsonObject.get("producers").asArray();

                    for (JsonValue p : prodArray) {
                        // Each producer information
                        String producerName = returnedJsonObject.getString("producerName", "producerNameError");
                        String producerTimestamp = returnedJsonObject.getString("timestamp", "timestampError");
                        String producerLocation = returnedJsonObject.getString("producerLocation", "producerLocationError");

                        prodArrayList.add(new Producer(producerName, producerTimestamp, producerLocation));
                    }

                    displayProductInformation();

                } catch (Exception e) {
                    e.printStackTrace();
                    startError("The returned data from the server is invalid.\nError Code: Data returned from server is in an unexpected form.");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.print(error.toString());
                startError("Error querying server\nError Code: " + error.toString());
            }
        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("accAddr", s.getAccAddr());
                System.out.println("Parameters: " + params);
                return params;
            }
        };
        // Add the request to the RequestQueue.
        System.out.print(stringRequest);
        queue.add(stringRequest);
    }

    private void displayProductInformation() {
        Intent i = new Intent(QueryServerActivity.this, InformationActivity.class);

        i.putParcelableArrayListExtra("prodArrayList", prodArrayList);
        startActivity(i);
    }

    private void startError(String errorMessage) {
        Intent i = new Intent(QueryServerActivity.this, MainActivity.class);
        i.putExtra("errorMessage", errorMessage);
        startActivity(i);
    }

    private JsonObject stringToJsonObject(String stringToJson) {
        JsonValue jsonResponse;

        try {
            // Parses the string response into a JsonValue
            jsonResponse = Json.parse(stringToJson);
            // Converts the JsonValue into an Object
            JsonObject objectResponse = jsonResponse.asObject();
            // Returns JsonObject
            return objectResponse;
        } catch (Exception e) {
            e.printStackTrace();
            startError("The scanned QR code is not valid.\nError Code: Cannot convert QR code to JSON object");
        }
        return null;
    }

    private void displayProductInformation(String qrAddress, String qrPubKey, String producerAddr, String productName, String productId, String batchId) {

    }

    /**
     * On back pressed sends the user to the main activity to prevent unexpected results
     */
    @Override
    public void onBackPressed() {
        Intent i = new Intent(QueryServerActivity.this, MainActivity.class);
        startActivity(i);
    }

}
