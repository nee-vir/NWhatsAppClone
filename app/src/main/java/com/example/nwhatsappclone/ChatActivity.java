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

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText writeMessage;
    private Button sendButton;
    private ArrayList<ParseObject> parseObjectsList;
    RecyclerViewAdapterM adapterM;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent=getIntent();
        final String clickedUsername= intent.getStringExtra("userName");
        recyclerView = findViewById(R.id.messages_recycler_view);
        writeMessage=findViewById(R.id.w_message);
        sendButton=findViewById(R.id.send_button);
        parseObjectsList=new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapterM=new RecyclerViewAdapterM(parseObjectsList);
        recyclerView.setAdapter(adapterM);
        try {
            ParseQuery<ParseObject> parseQuery = new ParseQuery("Chat");
            parseQuery.whereEqualTo("mSender", ParseUser.getCurrentUser().getUsername()+"");
            parseQuery.whereEqualTo("mReceiver", clickedUsername+"" );
            ParseQuery<ParseObject> parseQueryS = new ParseQuery<ParseObject>("Chat");
            parseQueryS.whereEqualTo("mSender", clickedUsername+"");
            parseQueryS.whereEqualTo("mReceiver", ParseUser.getCurrentUser().getUsername()+"");
            ArrayList<ParseQuery<ParseObject>> Queries = new ArrayList<>();
            Queries.add(parseQuery);
            Queries.add(parseQueryS);
            ParseQuery<ParseObject> allQueries = ParseQuery.or(Queries);
            allQueries.orderByAscending("createdAt");
            allQueries.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e == null) {
                        if (objects.size() > 0) {
                            for (ParseObject parseObject : objects) {
                                parseObjectsList.add(parseObject);

                            }
                            parseObjectsList.notifyAll();
                            adapterM.notifyDataSetChanged();


                        }
                    }
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
//        recyclerView.setAdapter(recyclerViewAdapterM);

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
