package com.coesolutions.surfersonline.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coesolutions.surfersonline.R;
import com.coesolutions.surfersonline.model.SurfArea;
import com.coesolutions.surfersonline.repository.database.SurfAreaDAO;
import com.coesolutions.surfersonline.repository.database.impl.SurfAreaDAOImpl;

import java.util.List;

public class RootFragment extends Fragment {

    public static RootFragment newInstance(int page) {
        RootFragment fragment = new RootFragment();
        Bundle args = new Bundle();
        args.putInt("PAGE", page);
        args.putInt("CHILD_PAGE", 0);
        fragment.setArguments(args);
        return fragment;
    }

    public RootFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_root, container, false);

        final FragmentManager fm = getFragmentManager();
        final FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.rootFragment, SurfAreasFragment.newInstance(4), "SurfAreas").addToBackStack("SurfAreas");
        transaction.commit();
        return view;

    }
}
