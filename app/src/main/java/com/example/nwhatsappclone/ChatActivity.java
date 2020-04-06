package com.example.nwhatsappclone;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText writeMessage;
    private Button sendButton;
    RecyclerViewAdapterM recyclerViewAdapterM;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = findViewById(R.id.messages_recycler_view);
        writeMessage=findViewById(R.id.w_message);
        sendButton=findViewById(R.id.send_button);
        recyclerViewAdapterM=new RecyclerViewAdapterM();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapterM);
    }
}
