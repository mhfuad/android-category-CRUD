package com.fuad.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fuad.category.model.Category;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private RequestQueue requestQueue;
    private SwipeRefreshLayout refresh;
    private ArrayList<Category> category = new ArrayList<>();
    private JsonArrayRequest arrayRequest;
    private RecyclerView recyclerView;
    private Dialog dialog;
    private CategoryAdapter categoryAdapter;
    private ImageView addCategory;
    private String url = "https://64a40253c3b509573b56ea44.mockapi.io/category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refresh = (SwipeRefreshLayout) findViewById(R.id.swipedown);
        recyclerView = (RecyclerView) findViewById(R.id.category);
        addCategory = (ImageView) findViewById(R.id.addCategoryButton);
        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goAddPage();
            }
        });

        dialog = new Dialog(this);

        refresh.setOnRefreshListener(this);
        refresh.post(new Runnable() {
            @Override
            public void run() {
                category.clear();
                getData();
            }
        });
    }

    private void getData() {
        refresh.setRefreshing(true);
        arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);

                        Category cat = new Category();
                        cat.setId(jsonObject.getInt("id"));
                        cat.setCategory(jsonObject.getString("category"));
                        category.add(cat);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                adapterPush(category);
                refresh.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(arrayRequest);
    }

    private void adapterPush(ArrayList<Category> category) {
        categoryAdapter = new CategoryAdapter(this, category);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public void onRefresh(){
        category.clear();
        getData();
    }

    public void goAddPage(){
        Intent intent = new Intent(this, AddCategoryActivity2.class);
        startActivity(intent);
    }

}