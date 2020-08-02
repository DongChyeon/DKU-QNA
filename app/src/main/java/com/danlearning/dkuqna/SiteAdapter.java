package com.danlearning.dkuqna;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.ViewHolder> implements OnSiteItemClickListener {
    ArrayList<SiteModel> items = new ArrayList<SiteModel>();  // SiteModel 아이템이 담긴 ArrayList
    OnSiteItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.site_item, viewGroup, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        SiteModel item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(OnSiteItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;

        public ViewHolder(View itemView, final SiteAdapter listener) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);

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

        public void setItem(SiteModel item) {
            title.setText(item.getTitle());
            content.setText(item.getContent());
        }
    }

    public void addItem(SiteModel item) {
        items.add(item);
    }

    public void setItems(ArrayList<SiteModel> items) {
        this.items = items;
    }

    public SiteModel getItem(int position) {
        return items.get(position);
    }

    public SiteModel setItem(int position, SiteModel item) {
        return items.set(position, item);
    }

    public void clearItems() { items.clear(); }
}
