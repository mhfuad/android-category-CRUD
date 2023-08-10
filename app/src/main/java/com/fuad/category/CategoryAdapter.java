package com.fuad.category;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fuad.category.model.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Category> categories;
    private String url = "";

    public CategoryAdapter(Context context, ArrayList<Category> category) {
        this.context = context;
        this.categories = category;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.category_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder holder,int position) {
        final Category category = categories.get(position);

        holder.title.setText(categories.get(position).getCategory());
        holder.no.setText("#" + String.valueOf(position+1));
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickPosition = holder.getAbsoluteAdapterPosition();
                if (clickPosition != RecyclerView.NO_POSITION){
                    Toast.makeText(context, category.getDescription(), Toast.LENGTH_SHORT).show();

                    goToEditPage(category);
                }
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickPosition = holder.getAbsoluteAdapterPosition();
                if (clickPosition != RecyclerView.NO_POSITION){
                    deleteCategory(category);
                }
            }
        });
    }

    private void deleteCategory(Category category) {
        Intent intent = new Intent(context, DeleteCategoryActivity.class);
        intent.putExtra("id", category.getId());
        intent.putExtra("category", category.getCategory());
        context.startActivity(intent);
    }

    private void goToEditPage(Category category) {
        Intent intent = new Intent(context, EditActivity.class);
        intent.putExtra("id", category.getId());
        intent.putExtra("category", category.getCategory());
        intent.putExtra("description", category.getDescription());
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView title, no;
        private ImageView edit, delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            no = (TextView) itemView.findViewById(R.id.no);
            title = (TextView) itemView.findViewById(R.id.nameCat);
            edit = itemView.findViewById(R.id.editCategory);
            delete = itemView.findViewById(R.id.deleteCat);
        }
    }


}
