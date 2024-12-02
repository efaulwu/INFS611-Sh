package com.example.projectcomponents4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // Getting data from Main Activity 2
        Intent thirdIntent = getIntent();
        String cid = thirdIntent.getStringExtra("cid");

        TextView transactionText = findViewById(R.id.transactionTextView);

        String url = "http://10.0.2.2:8080/loyaltyfirst/Transactions.jsp?cid="+cid;

        RequestQueue queue = Volley.newRequestQueue(MainActivity3.this);

        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String res) {
                String refinedStr;
                refinedStr = res.replace('#','\n');
                refinedStr = refinedStr.replace(",","\t\t\t\t\t\t\t\t");

                transactionText.setText(refinedStr);
            }
        }, null);
        queue.add(req);
    }
}