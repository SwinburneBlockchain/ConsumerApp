package com.swinblockchain.consumerapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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
        resultTextView.setText(i.getStringExtra("result"));
    }
}
