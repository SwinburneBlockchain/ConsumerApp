package com.swinblockchain.consumerapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import org.w3c.dom.Text;

public class InformationActivity extends AppCompatActivity {

    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        init();
    }

    private void init() {
        Intent i = getIntent();
        resultTextView = (TextView)findViewById(R.id.resultTextView);
        resultTextView.setText(i.getStringExtra("results"));
    }

    // TODO move printTransactions back in here


    /**
     * On back pressed sends the user to the main activity to prevent unexpected results
     */
    @Override
    public void onBackPressed() {
        Intent i = new Intent(InformationActivity.this, MainActivity.class);
        startActivity(i);
    }
}
