package edu.cmu.cs.gabrielclient.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import java.util.ArrayList;

import edu.cmu.cs.gabrielclient.Const;
import edu.cmu.cs.gabrielclient.GabrielClientActivity;
import edu.cmu.cs.gabrielclient.Models.Server;
import edu.cmu.cs.gabrielclient.R;

public class RecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private ArrayList<Server> data;
    private Context context;
    public RecyclerViewAdapter(ArrayList<Server> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView serverName;
        public TextView serverAddresse;
        public ImageButton launchButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            serverName = (TextView) itemView.findViewById(R.id.server_name);
            serverAddresse = (TextView) itemView.findViewById(R.id.server_address);
            launchButton = (ImageButton) itemView.findViewById(R.id.launch_button);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View mView = inflater.inflate(R.layout.item_server, parent, false);

        // Return a new holder instance
        MyViewHolder viewHolder = new MyViewHolder(mView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder,final int position) {
        holder.serverName.setText(data.get(position).getServername());
        holder.serverAddresse.setText(data.get(position).getServerAddresse());
        holder.launchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Server s = data.get(position);
                Const.SERVER_IP = s.getServerAddresse();
                Intent intent = new Intent(context, GabrielClientActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Server item, int position) {
        data.add(position, item);
        notifyItemInserted(position);
    }

    public ArrayList<Server> getData() {
        return data;
    }


}
