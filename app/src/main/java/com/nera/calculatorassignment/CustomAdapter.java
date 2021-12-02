package com.nera.calculatorassignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    ArrayList<Expression> expressionArrayList;

    public CustomAdapter(Context applicationContext) {
        expressionArrayList = (new HistoryFileManagement(applicationContext)).getExpressionList();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getEquationTextView().setText(expressionArrayList.get(position).expression);
        holder.getResultTextView().setText(expressionArrayList.get(position).result);
    }

    @Override
    public int getItemCount() {
        return expressionArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView equationTextView;
        private final TextView resultTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            equationTextView = itemView.findViewById(R.id.history_text_equation);
            resultTextView = itemView.findViewById(R.id.history_text_result);
        }

        public TextView getEquationTextView() {
            return equationTextView;
        }

        public TextView getResultTextView() {
            return resultTextView;
        }
    }
}
