package com.example.nwhatsappclone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapterM extends RecyclerView.Adapter<RecyclerViewAdapterM.MyViewHolder> {
    public RecyclerViewAdapterM() {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout,parent,false);
        MyViewHolder holder=new MyViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.messageSender.setText("Neevir");
        holder.theMessage.setText("Hi, How are you?");

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView messageSender,theMessage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.message_layout);
            messageSender=itemView.findViewById(R.id.message_sender);
            theMessage=itemView.findViewById(R.id.message_text_view);
        }
    }
}
