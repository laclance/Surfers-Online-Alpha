package com.coesolutions.surfersonline.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coesolutions.surfersonline.R;
import com.coesolutions.surfersonline.adapter.ContactsAdapter;
import com.coesolutions.surfersonline.adapter.RecyclerItemClickListener;
import com.coesolutions.surfersonline.model.Contact;

public class GroupsFragment extends Fragment {

    public static GroupsFragment newInstance(int page) {
        GroupsFragment fragment = new GroupsFragment();
        Bundle args = new Bundle();
        args.putInt("PAGE", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups, container, false);

        RecyclerView recyclerContacts = (RecyclerView) view.findViewById(R.id.recyclerGroups);
        setUpRecyclerView(recyclerContacts);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void setUpRecyclerView(RecyclerView recyclerContacts){
        ContactsAdapter adapter = new ContactsAdapter(Contact.createContactsList(20));
        // Attach the adapter to the recyclerview to populate items
        recyclerContacts.setAdapter(adapter);
        // Set layout manager to position the items
        recyclerContacts.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerContacts.setHasFixedSize(true);

        recyclerContacts.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerContacts, new RecyclerItemClickListener.OnItemClickListener() {
                    public void onSingleClick(int position) {
                    }

                    @Override
                    public void onLongClick(int position) {

                    }

                    @Override
                    public void onFling(int position) {

                    }
                })
        );
    }
}
