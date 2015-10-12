package com.coesolutions.surfersonline.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coesolutions.surfersonline.R;
import com.coesolutions.surfersonline.activity.EditSurfAreaActivity;
import com.coesolutions.surfersonline.adapter.RecyclerItemClickListener;
import com.coesolutions.surfersonline.adapter.SurfAreasAdapter;
import com.coesolutions.surfersonline.model.SurfArea;
import com.coesolutions.surfersonline.repository.database.SurfAreaDAO;
import com.coesolutions.surfersonline.repository.database.impl.SurfAreaDAOImpl;

import java.util.List;

public class SurfAreasFragment extends Fragment {
    private SurfAreasAdapter adapter;
    private SurfAreaDAO dao;
    private TextView instructionsView;

    public void addItem(String name, String description){
        SurfArea area = new SurfArea(name, description);
        dao.createSurfArea(area);
        adapter.addItem(name, description);

        if (adapter.getItemCount() == 1)
            instructionsView.setVisibility(View.INVISIBLE);
    }

    public void editItem(int id, String name, String description){
        adapter.editItem(name, description, id);
        dao.updateSurfArea(adapter.getItem(id - 1));
    }

    public void deleteItem(int id){
        dao.deleteSurfArea(adapter.getItem(id - 1));
        adapter.deleteItem(id);

        if (adapter.getItemCount() == 0)
            instructionsView.setVisibility(View.VISIBLE);
    }

    public static SurfAreasFragment newInstance(int page) {
        SurfAreasFragment fragment = new SurfAreasFragment();
        Bundle args = new Bundle();
        args.putInt("PAGE", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dao = new SurfAreaDAOImpl(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_surfareas, container, false);
        instructionsView = (TextView) rootView.findViewById(R.id.instructions);

        RecyclerView recyclerSurfspots = (RecyclerView) rootView.findViewById(R.id.recyclerSurfareas);
        setUpRecyclerView(recyclerSurfspots);

        return rootView;
    }

    private void setUpRecyclerView(final RecyclerView recyclerSurfArea) {
        List<SurfArea> areas = dao.getSurfAreaList();
        adapter = new SurfAreasAdapter(areas);

        if (areas.isEmpty())
            instructionsView.setVisibility(View.VISIBLE);
        else
            instructionsView.setVisibility(View.INVISIBLE);

        recyclerSurfArea.setAdapter(adapter);
        recyclerSurfArea.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerSurfArea.setHasFixedSize(true);

        recyclerSurfArea.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerSurfArea, new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onSingleClick(int position) {
                        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
                        if (ab != null)
                            ab.setHomeAsUpIndicator(R.drawable.ic_action_back);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.rootFragment, SurfSpotsFragment.newInstance(5), "SurfSpots").addToBackStack("SurfSpots");
                        transaction.commit();
                    }

                    @Override
                    public void onLongClick(int position) {
                        Intent intent = new Intent(getActivity(), EditSurfAreaActivity.class);
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