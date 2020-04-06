package com.example.nwhatsappclone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterM extends RecyclerView.Adapter<RecyclerViewAdapterM.MyViewHolder> {

    private ArrayList<ParseObject> receivedParseObjects;

    public RecyclerViewAdapterM(ArrayList receivedParseObjects) {
        this.receivedParseObjects = receivedParseObjects;
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
        try{
            if(receivedParseObjects.get(position).get("mSender")== ParseUser.getCurrentUser().getUsername()){
                /*holder.messageSender.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                holder.theMessage.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                holder.theMessage.setBackgroundResource(R.drawable.message_bg);*/
                holder.messageSender.setText(ParseUser.getCurrentUser().getUsername());
                holder.theMessage.setText(receivedParseObjects.get(position).get("theMessage")+"");
            } else{
                /*holder.messageSender.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                holder.theMessage.setBackgroundResource(R.drawable.message_bg2);
                holder.theMessage.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);*/
                holder.messageSender.setText(receivedParseObjects.get(position).get("mSender")+"");
                holder.theMessage.setText(receivedParseObjects.get(position).get("theMessage")+"");
            }
        } catch (Exception e){
            e.printStackTrace();
        }




    }

    @Override
    public int getItemCount() {
        return receivedParseObjects.size();
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
