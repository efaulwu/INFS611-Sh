package com.example.projectcomponents4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView nameText = findViewById(R.id.nameTextView);
        TextView pointsText = findViewById(R.id.pointTextView);

        Button alltxns = findViewById(R.id.alltxnBtn);
        Button txndetail = findViewById(R.id.txndetailBtn);
        Button redmptnDetail = findViewById(R.id.redemptiondetailBtn);
        Button addToFamily = findViewById(R.id.addtofamilyBtn);
        Button exit = findViewById(R.id.exitBtn);

        ImageView customerimageView = findViewById(R.id.customerimageView);

        // Getting data from Main Activity 1
        Intent secondIntent = getIntent();
        String cid = secondIntent.getStringExtra("cid");

        String url = "http://10.0.2.2:8080/loyaltyfirst/Info.jsp?cid="+cid;
        String imgUrl = "http://10.0.2.2:8080/loyaltyfirst/Images/"+cid+".jpeg";

        System.out.println(url);

        RequestQueue queue = Volley.newRequestQueue(MainActivity2.this);

        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String res) {
                String temp[] = res.trim().split("#");
                String temp1[] = temp[0].split(",");
                String name = temp1[0];
                String pts = temp1[1];
                nameText.setText(name);
                pointsText.setText(pts);
                System.out.println(name);
                System.out.println(pts);
            }
        }, null);
        queue.add(req);

        RequestQueue imgQueue = Volley.newRequestQueue(this);
        ImageRequest imgReq = new ImageRequest(imgUrl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bm) {
                customerimageView.setImageBitmap(bm);
            }
        },0,0, null, null);
        imgQueue.add(imgReq);


        alltxns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent alltxnIntent = new Intent(MainActivity2.this, MainActivity3.class  );
                alltxnIntent.putExtra("cid", cid);
                startActivity(alltxnIntent);
            }
        });


        txndetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent txndetailIntent = new Intent(MainActivity2.this, MainActivity4.class);
                txndetailIntent.putExtra("cid", cid);
                startActivity(txndetailIntent);
            }
        });


        redmptnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redmptnDetailIntent = new Intent(MainActivity2.this, MainActivity5.class);
                redmptnDetailIntent.putExtra("cid",cid);
                startActivity(redmptnDetailIntent);
            }
        });

        addToFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addtofamilyIntent = new Intent(MainActivity2.this, MainActivity6.class);
                addtofamilyIntent.putExtra("cid", cid);
                startActivity(addtofamilyIntent);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAndRemoveTask();
            }
        });

    }
}