package com.example.projectcomponents4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class MainActivity4<request> extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Spinner spinner = findViewById(R.id.transpinner);
        TextView selectTranView = findViewById(R.id.selecttran);
        TextView dateView = findViewById(R.id.dateview);
        TextView pointView = findViewById(R.id.pointview);
        TextView displayTrans = findViewById(R.id.transdisplay);
        TextView displayTrans1 = findViewById(R.id.textView13);
        TextView displayTrans2 = findViewById(R.id.textView17);
        List<String> txnref = new ArrayList<>();

        // Getting data from Main Activity 2
        Intent fourthIntent = getIntent();
        String cid = fourthIntent.getStringExtra("cid");

        String custIdUrl = "http://10.0.2.2:8080/loyaltyfirst/Transactions.jsp?cid=" + cid;


        ArrayList<String> transactions = new ArrayList<String>();

        RequestQueue queue = Volley.newRequestQueue(MainActivity4.this);

        StringRequest req = new StringRequest(Request.Method.GET, custIdUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String res) {
                String res1[] = res.split("#");
                for (int i = 0; i < res1.length - 1; i++) {
                    String res2[] = res1[i].split(",");

                    txnref.add(res2[0]);
                    System.out.println(res2[0]);

                    transactions.add(res2[0]);

                    ArrayAdapter<String> txnAdapter = new ArrayAdapter<String>(MainActivity4.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, transactions);
                    spinner.setAdapter(txnAdapter);
                }
            }
        }, null);

        queue.add(req);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> av, View view, int i, long l) {
                String transaction = av.getSelectedItem().toString();
                System.out.println("this is selected");
                System.out.println(transaction);

                for (int j = 0; j < txnref.size(); j++)
                {
                    if(transaction == txnref.get(j).toString())
                    {
                        String sptUrl = "http://10.0.2.2:8080/loyaltyfirst/TransactionDetails.jsp?cid="+cid+"&tref="+txnref.get(j);
                        RequestQueue txnrefQueue = Volley.newRequestQueue(MainActivity4.this);
                        StringRequest txnrefReq = new StringRequest(Request.Method.GET, sptUrl, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String res) {
                                final String[] date = new String[1];

                                String res1[] = res.split("#");
                                String res2 = new String();
                                String res3 = new String();
                                String res4 = new String();
                                int tpts = 0;
                                for(int i = 0; i<res1.length-1; i++)
                                {
                                    String temp[] = res1[i].split(",");
                                    res2 = res2.concat(temp[2]+"\n");
                                    res3 = res3.concat(temp[3]+"\n");
                                    tpts += Integer.valueOf(temp[3]);
                                    res4 = res4.concat(temp[4]+"\n");
                                }
                                String temp2[] = res.split("#");
                                for(int k = 0; k < temp2.length-1; k++)
                                {
                                    String s[] = temp2[k].split(",");
                                    date[0] = s[0];
                                }

                                String displayDate = date[0];
                                String txpt = tpts+"";

                                dateView.setText(displayDate);
                                pointView.setText(txpt);
                                displayTrans.setText(res2);
                                displayTrans1.setText(res4);
                                displayTrans2.setText(res3);

                            }
                        }, null);
                        txnrefQueue.add(txnrefReq);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> av) {}
        });

    }
}