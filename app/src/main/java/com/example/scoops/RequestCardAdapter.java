package com.example.scoops;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class RequestCardAdapter extends RecyclerView.Adapter<RequestCardAdapter.ViewHolder> {

    private Context context;


    //List to store all Requests
    ArrayList<RequestV> requestVs;

    //Constructor of this class
    public RequestCardAdapter(ArrayList<RequestV> requestVs, Context context){
        super();
        //Getting all requests
        this.requestVs = requestVs;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Getting the particular item from the list
        final RequestV requestV =  requestVs.get(position);


        //Showing data on the views

        holder.textViewName.setText(requestV.getName());
        holder.textViewDate.setText(requestV.getDate());
        holder.textViewitems.setText(requestV.getItem());
        holder.textViewLocation.setText(requestV.getLocation());
        holder.buttonViewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,RequestInfo.class);
                intent.putExtra("email",requestV.getEmail());
                intent.putExtra("name",requestV.getName());
                intent.putExtra("location",requestV.getLocation());
                intent.putExtra("phone",requestV.getPhone());
                intent.putExtra("date",requestV.getDate());
                context.startActivity(intent);
            }
        });

        holder.btnTakeRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,RequestInfo.class);
                intent.putExtra("email",requestV.getEmail());
                intent.putExtra("name",requestV.getName());
                intent.putExtra("location",requestV.getLocation());
                intent.putExtra("phone",requestV.getPhone());
                intent.putExtra("date",requestV.getDate());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return requestVs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //Views
        public TextView textViewName;
        public TextView textViewitems;
        public TextView textViewDate;
        public TextView textViewLocation;
        public LinearLayout buttonViewInfo;
        public LinearLayout btnTakeRequest;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.user_name);
            textViewitems = itemView.findViewById(R.id.items);
            textViewLocation = itemView.findViewById(R.id.post);
            textViewDate = itemView.findViewById(R.id.date);
            buttonViewInfo =  itemView.findViewById(R.id.btnViewDetails);
            btnTakeRequest = itemView.findViewById(R.id.btnTakeRequest);

        }
    }
    public void openRequestInfo(){

    }
}
