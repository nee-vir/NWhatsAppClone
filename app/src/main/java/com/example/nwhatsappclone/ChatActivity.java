package com.example.nwhatsappclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText writeMessage;
    private Button sendButton;
    RecyclerViewAdapterM recyclerViewAdapterM;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent=getIntent();
        final String clickedUsername= intent.getStringExtra("userName");
        recyclerView = findViewById(R.id.messages_recycler_view);
        writeMessage=findViewById(R.id.w_message);
        sendButton=findViewById(R.id.send_button);
        recyclerViewAdapterM=new RecyclerViewAdapterM();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerViewAdapterM);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ParseObject parseObject = ParseObject.create("Chat");
                    parseObject.put("mSender", ParseUser.getCurrentUser().getUsername()+"");
                    parseObject.put("mReceiver", clickedUsername+"");
                    parseObject.put("theMessage", writeMessage.getText()+"");
                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(ChatActivity.this, "Message Sent", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                                writeMessage.setText("");
                            } else {
                                FancyToast.makeText(ChatActivity.this, "Failed to send message!", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                            }
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
