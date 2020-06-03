package com.example.dkuqa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> implements OnCategoryItemClickListener {
    ArrayList<CategoryModel> items = new ArrayList<CategoryModel>();  // CategoryModel 아이템이 담긴 ArrayList
    OnCategoryItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.category_item, viewGroup, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        CategoryModel item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(OnCategoryItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView category;

        public ViewHolder(View itemView, final CategoryAdapter listener) {
            super(itemView);

            category = itemView.findViewById(R.id.category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(CategoryModel item) {
            category.setText(item.getCategory());
        }
    }

    public void addItem(CategoryModel item) {
        items.add(item);
    }

    public void setItems(ArrayList<CategoryModel> items) {
        this.items = items;
    }

    public CategoryModel getItem(int position) {
        return items.get(position);
    }

    public CategoryModel setItem(int position, CategoryModel item) {
        return items.set(position, item);
    }

    public void clearItems() { items.clear(); }
}