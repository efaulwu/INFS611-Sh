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

public class MainActivity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        Spinner prizespinner = findViewById(R.id.prizespinner);

        TextView pdesc = findViewById(R.id.pdescription);
        TextView displayPts = findViewById(R.id.pointsdisplay);
        TextView redemptioninfo = findViewById(R.id.redemptioninfo);
        TextView centers = findViewById(R.id.textView14);
        List<String> prizes = new ArrayList<>();


        Intent fifthIntent = getIntent();
        String cid = fifthIntent.getStringExtra("cid");

        String redemptionUrl = "http://10.0.2.2:8080/loyaltyfirst/PrizeIds.jsp?cid="+cid;

        ArrayList<String> prizeArray = new ArrayList<String>();

        RequestQueue queue = Volley.newRequestQueue(MainActivity5.this);

        StringRequest req = new StringRequest(Request.Method.GET, redemptionUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String res) {
                String res1[] = res.split("#");
                for (int i = 0; i < res1.length - 1; i++) {
                    String res2[] = res1[i].split(",");

                    prizes.add(res2[0]);
                    System.out.println(res2[0]);

                    prizeArray.add(res2[0]);

                    ArrayAdapter<String> txnAdapter = new ArrayAdapter<String>(MainActivity5.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, prizeArray);
                    prizespinner.setAdapter(txnAdapter);
                }
            }
        }, null);

        queue.add(req);


        prizespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> av, View view, int i, long l) {
                String prizeid = av.getSelectedItem().toString();
                System.out.println("is selected");
                System.out.println(prizeid);

                for (int j = 0; j < prizes.size(); j++)
                {
                    if(prizeid == prizes.get(j).toString())
                    {
                        String sptUrl = "http://10.0.2.2:8080/loyaltyfirst/RedemptionDetails.jsp?prizeid="+prizes.get(j)+"&cid="+cid;
                        RequestQueue txnrefQueue = Volley.newRequestQueue(MainActivity5.this);
                        StringRequest txnrefReq = new StringRequest(Request.Method.GET, sptUrl, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String res) {
                                final String[] ptreq = new String[1];
                                final String[] pdesc1 = new String[1];
                                final String[] prod = new String[1];


                                String temp[] = res.split("#");
                                String temp3 = new String();
                                String temp4 = new String();
                                for(int i = 0; i<temp.length-1; i++)
                                {
                                    String temp2[] = temp[i].split(",");
                                    temp3 = temp3.concat(temp2[2]+"\n");
                                    temp4 = temp4.concat(temp2[3]+"\n");
                                }
                                String ptemp[] = res.split("#");
                                for(int k = 0; k < ptemp.length-1; k++)
                                {
                                    String ptemp1[] = ptemp[k].split(",");
                                    ptreq[0] = ptemp1[1];
                                    pdesc1[0]= ptemp1[0];
                                }
                                String pointsDis = ptreq[0];
                                String pdesc2 = pdesc1[0];
                                displayPts.setText(pointsDis);
                                pdesc.setText(pdesc2);
                                redemptioninfo.setText(temp3);
                                centers.setText(temp4);

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