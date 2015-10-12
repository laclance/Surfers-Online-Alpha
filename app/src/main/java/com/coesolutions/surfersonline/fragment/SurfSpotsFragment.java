package com.coesolutions.surfersonline.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coesolutions.surfersonline.R;
import com.coesolutions.surfersonline.activity.EditSurfSpotActivity;
import com.coesolutions.surfersonline.adapter.RecyclerItemClickListener;
import com.coesolutions.surfersonline.adapter.SurfSpotsAdapter;
import com.coesolutions.surfersonline.model.SurfSpot;
import com.coesolutions.surfersonline.repository.database.SurfSpotDAO;
import com.coesolutions.surfersonline.repository.database.impl.SurfSpotDAOImpl;

import java.util.List;

public class SurfSpotsFragment extends Fragment {
    private SurfSpotsAdapter adapter;
    private SurfSpotDAO dao;

    public void addItem(String name, String description){
        SurfSpot spot = new SurfSpot(name, description);
        dao.createSurfSpot(spot);
        adapter.addItem(name, description);
    }

    public void editItem(int id, String name, String description){
        adapter.editItem(name, description, id);
        dao.updateSurfSpot(adapter.getItem(id - 1));
    }

    public void deleteItem(int id){
        dao.deleteSurfSpot(adapter.getItem(id - 1));
        adapter.deleteItem(id);
        if (adapter.getItemCount() == 0)
            getFragmentManager().popBackStackImmediate();
    }

    public static SurfSpotsFragment newInstance(int page) {
        SurfSpotsFragment fragment = new SurfSpotsFragment();
        Bundle args = new Bundle();
        args.putInt("PAGE", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new SurfSpotDAOImpl(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_surfspots, container, false);

        RecyclerView recyclerSurfspots = (RecyclerView) rootView.findViewById(R.id.recyclerSurfspots);
        setUpRecyclerView(recyclerSurfspots);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_surfspot, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setUpRecyclerView(final RecyclerView recyclerSurfspot){
        List<SurfSpot> spots = dao.getSurfSpotList();
        adapter = new SurfSpotsAdapter(spots);

        recyclerSurfspot.setAdapter(adapter);
        recyclerSurfspot.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerSurfspot.setHasFixedSize(true);

        recyclerSurfspot.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerSurfspot, new RecyclerItemClickListener.OnItemClickListener() {
                    public void onSingleClick(int position) {
                        Intent intent = new Intent(getActivity(), EditSurfSpotActivity.class);
                        intent.putExtra("ID", position + 1);
                        intent.putExtra("NAME", adapter.getItem(position).getBasicInfo().getName());
                        intent.putExtra("DESCRIPTION", adapter.getItem(position).getBasicInfo().getDescription());
                        startActivityForResult(intent, 4);
                    }

                    @Override
                    public void onLongClick(int position) {
                        Intent intent = new Intent(getActivity(), EditSurfSpotActivity.class);
                        intent.putExtra("ID", position + 1);
                        intent.putExtra("NAME", adapter.getItem(position).getBasicInfo().getName());
                        intent.putExtra("DESCRIPTION", adapter.getItem(position).getBasicInfo().getDescription());
                        startActivityForResult(intent, 3);
                    }

                    @Override
                    public void onFling(int position) {
                        deleteItem(position + 1);
                    }
                })
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        editItem(data.getIntExtra("ID", 0), data.getStringExtra("NAME"), data.getStringExtra("DESCRIPTION"));
    }
}
