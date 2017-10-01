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

    private final String URL = "http://ec2-54-153-202-123.ap-southeast-2.compute.amazonaws.com:3000/productInfo";
    private final String BLOCKCHAIN_ACC = "NXT-HP3G-T95S-6W2D-AEPHE";
    private final String VALID_MESSAGE = "VALIDATE";

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
                if (checkValid(response)) {
                    JsonObject jsonProduct = createJsonProduct(response);
                    ArrayList<JsonObject> jsonProducer = createJsonProducer(response);

                    try {
                        // May not be required
                        String qrAddress = jsonProduct.getString("qrAddress", "qrAddressError");
                        String qrPubKey = jsonProduct.getString("qrPubKey", "qrPubKeyError");
                        String producerAddr = jsonProduct.getString("producerAddr", "producerAddrError");

                        // Single product information
                        String productName = jsonProduct.getString("productName", "productNameError");
                        String productId = jsonProduct.getString("productId", "productIdError");
                        String batchId = jsonProduct.getString("batchId", "batchIdError");

                        Product newProduct = new Product(productName, productId, batchId);

                        for (JsonObject p : jsonProducer) {
                            // Each producer information
                            String producerName = p.getString("producerName", "producerNameError");
                            int producerTimestamp = p.getInt("timestamp", 0);
                            String producerLocation = p.getString("producerLocation", "producerLocationError");

                            prodArrayList.add(new Producer(producerName, producerTimestamp, producerLocation));
                        }
                        displayProductInformation(newProduct, prodArrayList);


                    } catch (Exception e) {
                        e.printStackTrace();
                        startError("The returned data from the server is invalid.\nError Code: Data returned from server is in an unexpected form.");
                    }
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

    private JsonObject createJsonProduct(String response) {
        String[] cleanedResponseArr = response.split("\\|");
        return stringToJsonObject(cleanedResponseArr[0]);
    }

    private ArrayList<JsonObject> createJsonProducer(String response) {
        String[] cleanedResponseArr = response.split("\\|");
        ArrayList<JsonObject> jsonProducers = new ArrayList<>();

        for (int i = 2; i < cleanedResponseArr.length; i++) {
            jsonProducers.add(stringToJsonObject(cleanedResponseArr[i]));
        }
        return jsonProducers;
    }

    private boolean checkValid(String response) {
        String[] cleanedResponseArr = response.split("\\|");
        JsonObject json = stringToJsonObject(cleanedResponseArr[1]);

        try {
            String blockchainAcc = json.getString("actionAddress", "actionAddressError");
            String validMessage = json.getString("action", "actionError");

            if (blockchainAcc.equals(BLOCKCHAIN_ACC) && validMessage.equals(VALID_MESSAGE)) {
                return true;
            } else {
                startError("The product/producer information is not valid.\nError Code: Blockchain account address incorrect or invalidated.");
                return false;
            }
        } catch (Exception e) {
            startError("The product/producer information is not valid.\nError Code: Blockchain account address incorrect or invalidated.");
            e.printStackTrace();
            return false;
        }
    }

    private void displayProductInformation(Product newProduct, ArrayList<Producer> producerList) {
        Intent i = new Intent(QueryServerActivity.this, InformationActivity.class);

        i.putExtra("product", newProduct);
        i.putParcelableArrayListExtra("prodArrayList", producerList);
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

    /**
     * On back pressed sends the user to the main activity to prevent unexpected results
     */
    @Override
    public void onBackPressed() {
        Intent i = new Intent(QueryServerActivity.this, MainActivity.class);
        startActivity(i);
    }

}
