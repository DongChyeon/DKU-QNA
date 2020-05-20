package com.example.dkuqa;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> implements OnQuestionItemClickListener {
    ArrayList<Question> items = new ArrayList<Question>();  // Question 아이템이 담긴 ArrayList
    OnQuestionItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.question_item, viewGroup, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Question item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(OnQuestionItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView question;
        TextView category;

        public ViewHolder(View itemView, final OnQuestionItemClickListener listener) {
            super(itemView);

            question = itemView.findViewById(R.id.question);
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

        public void setItem(Question item) {
            question.setText(item.getTitle());
            category.setText(item.getCategory());
        }
    }

    public void addItem(Question item) {
        items.add(item);
    }

    public void setItems(ArrayList<Question> items) {
        this.items = items;
    }

    public Question getItem(int position) {
        return items.get(position);
    }

    public Question setItem(int position, Question item) {
        return items.set(position, item);
    }
}