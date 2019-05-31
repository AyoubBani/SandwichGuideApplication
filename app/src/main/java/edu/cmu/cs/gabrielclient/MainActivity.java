package edu.cmu.cs.gabrielclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.cmu.cs.gabrielclient.Models.Server;

public class MainActivity extends AppCompatActivity {

    Button addServer;
    TextView viewServers;

    TextView serverName;
    TextView serverAddresse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addServer = (Button) findViewById(R.id.add_server);
        viewServers = (TextView) findViewById(R.id.view_link);
        serverName = (TextView) findViewById(R.id.server_name);
        serverAddresse = (TextView) findViewById(R.id.server_addresse);

        addServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String serverNameValue = serverName.getText().toString();
                String serverAddressValue = serverAddresse.getText().toString();
                if (!serverNameValue.equals("") && !serverAddressValue.equals("")) {
                    Server server = new Server(serverNameValue, serverAddressValue);
                    Intent i = new Intent(getApplicationContext(), Servers.class);
                    i.putExtra("Server", server);
                    startActivity(i);
//                    finish();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            }
        });

        viewServers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Servers.class);
                startActivity(i);
//                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }
/*
    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }
*/
}
