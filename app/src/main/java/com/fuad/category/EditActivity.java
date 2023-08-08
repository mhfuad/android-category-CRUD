package com.fuad.category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class EditActivity extends AppCompatActivity {
    private EditText cat;
    private Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        cat = findViewById(R.id.cat);
        submit = findViewById(R.id.submit);
        int id = getIntent().getIntExtra("id", -1);

        cat.setText(getIntent().getStringExtra("category"));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveCategory(id, cat.getText().toString());
            }
        });
    }

    private void saveCategory(int id, String title) {
        //Toast.makeText(EditActivity.this, "Update button clicked"+ title, Toast.LENGTH_LONG).show();
        StringRequest request = new StringRequest(Request.Method.PUT, "https://64a40253c3b509573b56ea44.mockapi.io/category/"+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(EditActivity.this, "Category update successful", Toast.LENGTH_LONG).show();
                goToHome();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditActivity.this, "Category add faild", Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
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