package com.example.myrxjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myrxjava.api.DataItem;
import com.example.myrxjava.databinding.ItemsLayoutBinding;

import java.util.Arrays;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    List<DataItem> list;
    List<String> strings = Arrays.asList("Yesterday", "last Week","Last Month");

    public Adapter(List<DataItem> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsLayoutBinding binding = ItemsLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,
                false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull  Adapter.MyViewHolder holder, int position) {

        holder.layoutBinding.setData(list.get(position));
        holder.layoutBinding.date.setText(strings.get(position));

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {



        ItemsLayoutBinding layoutBinding;

        public MyViewHolder(@NonNull ItemsLayoutBinding itemView) {
            super(itemView.getRoot());
            this.layoutBinding = itemView;

        }
    }
}
