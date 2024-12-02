package com.example.projectcomponents4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditText unameEdit = findViewById(R.id.usernameEditText);
        EditText pwdEdit = findViewById(R.id.passwordEditText);

        Button login = findViewById(R.id.loginBtn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uname;
                String pwd;
                uname = unameEdit.getText().toString();
                pwd = pwdEdit.getText().toString();

                String url = "http://10.0.2.2:8080/loyaltyfirst/loginservlet?user="+uname+"&pass="+pwd;

//
                System.out.println(url);

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
//
                StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String res) {
                        if (res.trim().equals("No")) {
                            System.out.println(res);
                            Toast.makeText(MainActivity.this, "Wrong Username or Password", Toast.LENGTH_LONG).show();
                        }
                        else {
                            System.out.println(res.toString());
                            String temp[] = res.trim().split(":");
                            String cid = temp[1];
                            Intent loginIntent = new Intent(MainActivity.this, MainActivity2.class);
                            loginIntent.putExtra("cid", cid);
                            startActivity(loginIntent);
                        }
                    }
                }, volleyError -> System.out.println(volleyError.toString()));
                queue.add(req);
            }
        });
    }
}