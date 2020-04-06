package com.example.nwhatsappclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ArrayList<String> arrayList;
    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.users_list_view);
        arrayList=new ArrayList<>();
        swipeRefreshLayout=findViewById(R.id.swipe_refresh_layout);
        try{
            ParseQuery<ParseUser> parseQuery=ParseUser.getQuery();
            parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
            parseQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if(e==null){
                        if(objects.size()>0){
                            for(ParseUser user: objects){
                                arrayList.add(user.getUsername());
                            }
                            arrayAdapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,arrayList);
                            listView.setAdapter(arrayAdapter);
                        }
                    }
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
        listView.setOnItemClickListener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                try{
                    ParseQuery<ParseUser> parseQuery=ParseUser.getQuery();
                    parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
                    parseQuery.whereNotContainedIn("username",arrayList);
                    parseQuery.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> objects, ParseException e) {
                            if(e==null){
                                if(objects.size()>0){
                                    for(ParseUser user: objects){
                                        arrayList.add(user.getUsername());
                                    }
                                    arrayAdapter.notifyDataSetChanged();
                                    if(swipeRefreshLayout.isRefreshing()){
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                } else{
                                    if(swipeRefreshLayout.isRefreshing()){
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                }
                            } else{
                                if(swipeRefreshLayout.isRefreshing()){
                                    swipeRefreshLayout.setRefreshing(false);
                                }
                            }
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.log_out){
            ParseUser.getCurrentUser().logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    startActivity(new Intent(MainActivity.this,LogInActivity.class));
                    FancyToast.makeText(MainActivity.this,"Successfully Logged Out!",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                    finish();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(MainActivity.this,ChatActivity.class);
        intent.putExtra("userName", arrayList.get(position));
        startActivity(intent);
    }
}
