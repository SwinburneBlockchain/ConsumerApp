package com.swinblockchain.consumerapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class InformationActivity extends AppCompatActivity {

    TextView resultTextView;
    ArrayList<ProductLocation> plList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        init();
    }

    private void init() {

        Bundle args = getIntent().getBundleExtra("BUNDLE");
        plList = (ArrayList<ProductLocation>) args.getSerializable("ARRAYLIST");

        //mainTableLayout = (TableLayout) findViewById(R.id.mainTableLayout);

        //plList = new ArrayList<>();

        displayProductInformation(plList);



        //Intent i = getIntent();
        //resultTextView = (TextView)findViewById(R.id.resultTextView);
        //resultTextView.setText(i.getStringExtra("results"));
    }

    private void displayProductInformation(ArrayList<ProductLocation> plList) {
        // Draw the top, main window
        TextView productName = (TextView) findViewById(R.id.productName);
        TextView batchID = (TextView) findViewById(R.id.batchID);

        productName.setText("Product name: " + plList.get(0).getProductName());
        batchID.setText("Batch ID: " + plList.get(0).getBatchID());

        // Draw a new column for each location
        for (ProductLocation pl : plList) {
            createTableRow(pl);
            // Clean up bottom of table
            createTableRowFinal();
        }
    }

    private void createTableRowFinal() {
        final TableLayout detailsTable = (TableLayout) findViewById(R.id.mainTableLayout);
        final TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.tablerowbottom, null);
        detailsTable.addView(tableRow);
    }

    private void createTableRow(ProductLocation pl) {

            final TableLayout detailsTable = (TableLayout) findViewById(R.id.mainTableLayout);
            final TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.tablerow, null);
            TextView tv;

            //Filling in cells


            tv = (TextView) tableRow.findViewById(R.id.informationCell);
            tv.setText("Date: " + pl.getDate() + "\n" + "Registered by: " + pl.getRegBy() + "\n" + "Location: " + pl.getLocation());

            //Add row to the table
            detailsTable.addView(tableRow);
            //} //End for


            //TableRow tr = new TableRow(this);
            //TextView tv;

            //tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        /* Create a Button to be the row-content. */
            //Button b = new Button(this);
            //b.setText("Dynamic Button");
            //b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        /* Add Button to row. */
            //tr.addView(b);
        /* Add row to TableLayout. */
            //tr.setBackgroundResource(R.drawable.sf_gradient_03);
            //mainTableLayout.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

    }


    /**
     * On back pressed sends the user to the main activity to prevent unexpected results
     */
    @Override
    public void onBackPressed() {
        Intent i = new Intent(InformationActivity.this, MainActivity.class);
        startActivity(i);
    }
}
