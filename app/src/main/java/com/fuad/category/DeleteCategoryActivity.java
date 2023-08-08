package com.fuad.category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fuad.category.model.Category;

public class DeleteCategoryActivity extends AppCompatActivity {

    private TextView title;
    private Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_category);

        title = findViewById(R.id.title);
        delete = findViewById(R.id.submit);

        int id = getIntent().getIntExtra("id", -1);
        title.setText(getIntent().getStringExtra("category"));

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCategory(id);
            }
        });
    }

    private void deleteCategory(int id) {
        StringRequest request = new StringRequest(Request.Method.DELETE, "https://64a40253c3b509573b56ea44.mockapi.io/category/"+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(DeleteCategoryActivity.this, "Data delete successful", Toast.LENGTH_LONG).show();
                goToHome();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DeleteCategoryActivity.this, "Data delete Fail", Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(DeleteCategoryActivity.this).add(request);
    }

    private void goToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}