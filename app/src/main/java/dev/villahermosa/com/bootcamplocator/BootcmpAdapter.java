package dev.villahermosa.com.bootcamplocator;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class BootcmpAdapter extends RecyclerView.Adapter<BootcmpViewHolder>{

    private ArrayList<bootcmp> locations;

    public BootcmpAdapter(ArrayList<bootcmp> locations) {
        this.locations = locations;
    }

    @Override
    public void onBindViewHolder(BootcmpViewHolder holder, int position) {
        final bootcmp location = locations.get(position);
        holder.updateUI(location);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    @Override
    public BootcmpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.bootcamp_item, parent, false);
        return new BootcmpViewHolder(card);
    }
}
