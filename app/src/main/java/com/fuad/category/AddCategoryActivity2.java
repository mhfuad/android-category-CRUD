package com.fuad.category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddCategoryActivity2 extends AppCompatActivity {

    private TextView cat;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category2);

        cat = findViewById(R.id.cat);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = cat.getText().toString();
                saveCategory(title);
            }
        });

    }

    private void saveCategory(String title) {
        StringRequest request = new StringRequest(Request.Method.POST, "https://64a40253c3b509573b56ea44.mockapi.io/category", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(AddCategoryActivity2.this, "Category add successful", Toast.LENGTH_LONG).show();
                goToHome();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddCategoryActivity2.this, "Category add faild", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("category", title);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
    private void goToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}