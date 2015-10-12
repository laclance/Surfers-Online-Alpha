package com.coesolutions.surfersonline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coesolutions.surfersonline.R;
import com.coesolutions.surfersonline.model.SurfArea;

import java.util.List;

public class SurfAreasAdapter extends
        RecyclerView.Adapter<SurfAreasAdapter.ViewHolder> {


    public static class ViewHolder extends RecyclerView.ViewHolder{

        public final ImageView avatarImageView;
        public final TextView nameTextView;
        public final TextView descriptionTextView;
        public final TextView spotsTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            avatarImageView = (ImageView) itemView.findViewById((R.id.surfarea_avatar));
            nameTextView = (TextView) itemView.findViewById(R.id.surfarea_name);
            descriptionTextView = (TextView) itemView.findViewById(R.id.surfarea_description);
            spotsTextView = (TextView) itemView.findViewById(R.id.surfarea_spots);
        }
    }

    private final List<SurfArea> surfAreas;
    private static long lastId = 0;

    public SurfAreasAdapter(List<SurfArea> areas) {
        surfAreas = areas;
    }
    @Override
    public SurfAreasAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View areaView = inflater.inflate(R.layout.item_surfarea, parent, false);

        ViewHolder viewHolder = new ViewHolder(areaView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SurfAreasAdapter.ViewHolder viewHolder, int position) {
        SurfArea area = surfAreas.get(position);

        TextView nameView = viewHolder.nameTextView;
        nameView.setText(area.getBasicInfo().getName());

        TextView descriptionView = viewHolder.descriptionTextView;
        descriptionView.setText(area.getBasicInfo().getDescription());

        if (area.getSurfSpots() != null) {
            TextView spotsView = viewHolder.spotsTextView;
            spotsView.setText(area.getSurfSpotCount() + " spots");
        }
    }

    @Override
    public int getItemCount() {
        return surfAreas.size();
    }

    public SurfArea getItem(int position){
        return surfAreas.get(position);
    }

    public void addItem(String name, String description) {
        surfAreas.add(new SurfArea(++lastId, name, description));
        this.notifyItemInserted(surfAreas.size() - 1);
    }

    public void editItem(String name, String description, int position) {
        SurfArea area = surfAreas.get(position - 1);
        area.setBasicInfo(name, description);
        surfAreas.set(position - 1, area);
        this.notifyItemChanged(position - 1);
    }

    public void deleteItem(int position) {
        surfAreas.remove(position - 1);
        this.notifyItemRemoved(position  - 1);
    }
}