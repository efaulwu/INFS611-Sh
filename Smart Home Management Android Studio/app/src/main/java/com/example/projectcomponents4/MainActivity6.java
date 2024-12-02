package com.example.projectcomponents4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class MainActivity6 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        TextView txnpointsTextView = findViewById(R.id.txnpointsTextView);
        TextView familyidTextView = findViewById(R.id.familyidTextView);
        TextView familypercentTextView = findViewById(R.id.familypercentTextView);

        Spinner transactionSpinner = findViewById(R.id.transactionSpinner);

        Button addfamilyBtn = findViewById(R.id.addfamilyBtn);

        Intent sixthAcitvityIntent = getIntent();
        String cid = sixthAcitvityIntent.getStringExtra("cid");
        List<String> tref = new ArrayList<>();

        String cidUrl = "http://10.0.2.2:8080/loyaltyfirst/Transactions.jsp?cid="+cid;


        ArrayList<String> transactionList = new ArrayList<String>();

        RequestQueue queue = Volley.newRequestQueue(MainActivity6.this);

        StringRequest request = new StringRequest(Request.Method.GET, cidUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String response[] = s.split("#");
                for (int i = 0; i < response.length-1; i++)
                {
                    String c[] = response[i].split(",");

                    tref.add(c[0]);
                    System.out.println(c[0]);
                    transactionList.add(c[0]);
                    ArrayAdapter<String> transactionAdapter = new ArrayAdapter<String>(MainActivity6.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, transactionList);
                    transactionSpinner.setAdapter(transactionAdapter);
                }
            }
        }, null);

        queue.add(request);

        transactionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String transaction = adapterView.getSelectedItem().toString();
                System.out.println("this is selected");
                System.out.println(transaction);

                for (int j = 0; j < tref.size(); j++)
                {
                    if(transaction == tref.get(j).toString())
                    {
                        String supportUrl = "http://10.0.2.2:8080/loyaltyfirst/SupportFamilyIncrease.jsp?cid="+cid+"&tref="+tref.get(j);
                        RequestQueue trefQueue = Volley.newRequestQueue(MainActivity6.this);
                        StringRequest trefReq = new StringRequest(Request.Method.GET, supportUrl, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                final String[] familyId = new String[1];
                                final String[] familyPercent = new String[1];
                                final String[] txnPoints = new String[1];

                                String response[] = s.split("#");
                                for(int k = 0; k < response.length-1; k++)
                                {
                                    String c[] = response[k].split(",");
                                     familyId[0] = c[0];
                                     familyPercent[0]= c[1];
                                     txnPoints[0] = c[2];
                                }

                                String fid = familyId[0];
                                String txn = txnPoints[0];
                                String fpercent = familyPercent[0];

                                txnpointsTextView.setText(txnPoints[0]);
                                familyidTextView.setText(familyId[0]);
                                familypercentTextView.setText(familyPercent[0]);



                                addfamilyBtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        int ans = (int) (Double.parseDouble(fpercent)/100*Double.parseDouble(txn));
                                        String familyincreaseUrl = "http://10.0.2.2:8080/loyaltyfirst/FamilyIncrease.jsp?fid="+fid+"&cid="+cid+"&npoints="+ans;

                                        StringRequest increaseRequest = new StringRequest(Request.Method.GET, familyincreaseUrl, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String s) {
                                                // Displaying toast
                                                Toast.makeText(MainActivity6.this, ans+" Points added to the members of Family ID"+fid+"!", Toast.LENGTH_LONG).show();
                                            }
                                        }, null);
                                        trefQueue.add(increaseRequest);
                                    }
                                });

                            }
                        }, null);
                        trefQueue.add(trefReq);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




    }
}