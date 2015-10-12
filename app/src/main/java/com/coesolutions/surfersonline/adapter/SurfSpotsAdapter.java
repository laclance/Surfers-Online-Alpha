package com.coesolutions.surfersonline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coesolutions.surfersonline.R;
import com.coesolutions.surfersonline.model.SurfSpot;

import java.util.List;

public class SurfSpotsAdapter extends
        RecyclerView.Adapter<SurfSpotsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public final ImageView avatarImageView;
        public final TextView nameTextView;
        public final TextView descriptionTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            avatarImageView = (ImageView) itemView.findViewById((R.id.surfarea_avatar));
            nameTextView = (TextView) itemView.findViewById(R.id.surfspot_name);
            descriptionTextView = (TextView) itemView.findViewById(R.id.surfspot_description);
        }
    }

    private final List<SurfSpot> surfSpots;
    private static long lastId = 0;

    public SurfSpotsAdapter(List<SurfSpot> spots) {
        surfSpots = spots;
    }

    @Override
    public SurfSpotsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View areaView = inflater.inflate(R.layout.item_surfspot, parent, false);

        ViewHolder viewHolder = new ViewHolder(areaView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SurfSpotsAdapter.ViewHolder viewHolder, int position) {
        SurfSpot surfSpot = surfSpots.get(position);

        TextView nameView = viewHolder.nameTextView;
        nameView.setText(surfSpot.getBasicInfo().getName());

        TextView descriptionView = viewHolder.descriptionTextView;
        descriptionView.setText(surfSpot.getBasicInfo().getDescription());
    }

    @Override
    public int getItemCount() {
        return surfSpots.size();
    }

    public SurfSpot getItem(int position){
        return surfSpots.get(position);
    }

    public void addItem(String name, String description) {
        surfSpots.add(new SurfSpot(++lastId, name, description));
        this.notifyItemInserted(surfSpots.size() - 1);
    }

    public void editItem(String name, String description, int position) {
        SurfSpot spot = surfSpots.get(position - 1);
        spot.setBasicInfo(name, description);
        surfSpots.set(position - 1, spot);
        this.notifyItemChanged(position - 1);
    }

    public void deleteItem(int position) {
        surfSpots.remove(position - 1);
        this.notifyItemRemoved(position - 1);
    }
}