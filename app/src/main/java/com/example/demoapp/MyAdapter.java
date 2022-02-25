package com.example.demoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.clevertap.android.sdk.inbox.CTInboxMessage;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context c;
    ArrayList<CTInboxMessage> mdata;


    public MyAdapter(Context c, ArrayList<CTInboxMessage> mdata)
    {
        this.c=c;
        this.mdata=mdata;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txtName.setText(mdata.get(position).getInboxMessageContents().get(0).getMessage());

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }
}
