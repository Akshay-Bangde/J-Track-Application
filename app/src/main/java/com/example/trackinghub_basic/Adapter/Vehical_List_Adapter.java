package com.example.trackinghub_basic.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackinghub_basic.Model.Vehical_List_Model;
import com.example.trackinghub_basic.R;

import java.util.ArrayList;
import java.util.List;

public class Vehical_List_Adapter extends
        RecyclerView.Adapter<Vehical_List_Adapter.CustomViewHolder> implements Filterable {
    public List<Vehical_List_Model> exampleList;
    public List<Vehical_List_Model> exampleListFull;
    private ItemClickListner listner;

    public Vehical_List_Adapter(List<Vehical_List_Model> mexampleList, ItemClickListner listner) {
        exampleList = mexampleList;
        exampleListFull = exampleList;
        this.listner = listner;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_vehical_list, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.vehical_no.setText(exampleListFull.get(position).getVehical_number());
        holder.address.setText(exampleListFull.get(position).getAddress());
        holder.lastUpdate.setText(exampleListFull.get(position).getDate());
        holder.speed.setText(exampleListFull.get(position).getSpeed());

    }

    @Override
    public int getItemCount() {
        return exampleListFull.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView vehical_no;
        public TextView address;
        public TextView lastUpdate;
        public TextView speed;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            vehical_no = itemView.findViewById(R.id.vehical_number);
            address = itemView.findViewById(R.id.vehical_address);
            lastUpdate = itemView.findViewById(R.id.vehical_last_DateTime);
            speed = itemView.findViewById(R.id.vehical_speed);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listner.onClick(itemView, getAdapterPosition());
        }
    }


    // Code For recycler view ItemClickListner interface
    public interface ItemClickListner {
        void onClick(View view, int position);
    }

    // Code for Search View Filter
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    exampleListFull = exampleList;
                } else {
                    ArrayList<Vehical_List_Model> filteredList = new ArrayList<>();

                    for (Vehical_List_Model androidVersion : exampleList) {
                        if (
                                androidVersion.getVehical_number().toLowerCase().contains(charString)) {
                            filteredList.add(androidVersion);
                        }
                    }
                    exampleListFull = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = exampleListFull;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {


                exampleListFull = (ArrayList<Vehical_List_Model>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
