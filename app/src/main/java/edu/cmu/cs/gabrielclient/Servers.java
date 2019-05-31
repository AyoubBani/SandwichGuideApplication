package edu.cmu.cs.gabrielclient;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import edu.cmu.cs.gabrielclient.Models.Server;
import edu.cmu.cs.gabrielclient.adapters.RecyclerViewAdapter;

public class Servers extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private ArrayList<Server> data;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servers);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        LinearLayoutManager llm = new LinearLayoutManager(this);


        data = getServersFromSharedPrefs();
        if (data == null){
            data = new ArrayList<>();
        }
        if(getIntent().hasExtra("Server")){
            Server server = (Server) getIntent().getSerializableExtra("Server");
            Toast.makeText(this,"New Server: "+server.getServername(), Toast.LENGTH_LONG).show();
            data.add(server);
        }
        recyclerView = (RecyclerView) findViewById(R.id.rvServers);
        recyclerView.setLayoutManager(llm);
        mAdapter = new RecyclerViewAdapter(data, getApplicationContext());
        recyclerView.setAdapter(mAdapter);
        enableSwipeToDeleteAndUndo();
    }


    public void saveServerToSharedPrefs() {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(data); //tasks is an ArrayList instance variable
        prefsEditor.putString("currentServers", json);
        prefsEditor.commit();
    }

    public ArrayList<Server> getServersFromSharedPrefs() {
        SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("currentServers", "");
        data = gson.fromJson(json, new TypeToken<ArrayList<Server>>(){}.getType());
        return data;
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveServerToSharedPrefs();
    }

    private void enableSwipeToDeleteAndUndo() {
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(this) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                final int position = viewHolder.getAdapterPosition();
                final Server item = mAdapter.getData().get(position);

                mAdapter.removeItem(position);


                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Item was removed from the list.", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mAdapter.restoreItem(item, position);
                        recyclerView.scrollToPosition(position);
                    }
                });

                snackbar.show();

            }
        };

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchhelper.attachToRecyclerView(recyclerView);
    }
}
