package com.coesolutions.surfersonline.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coesolutions.surfersonline.R;
import com.coesolutions.surfersonline.model.Contact;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsAdapter extends
        RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView nameTextView;
        public final TextView statusTextView;
        public final CircleImageView avatarImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.contact_name);
            statusTextView = (TextView) itemView.findViewById(R.id.contact_status);
            avatarImageView = (CircleImageView) itemView.findViewById((R.id.contact_avatar));
        }
    }

    private final List<Contact> contacts;

    public ContactsAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_contact, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContactsAdapter.ViewHolder viewHolder, int position) {
        Contact contact = contacts.get(position);

        CircleImageView circleView = viewHolder.avatarImageView;

        TextView nameView = viewHolder.nameTextView;
        nameView.setText(contact.getName());

        TextView statusView = viewHolder.statusTextView;

        if (contact.isOnline()) {
            statusView.setText("Online");
        }
        else {
            statusView.setText("Offline");
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void addItem(String name) {
        contacts.add(new Contact(name, false));
        this.notifyItemInserted(contacts.size()-1);
    }

    public void editItem(String name, int position) {
        contacts.get(position).setName(name);
        this.notifyItemInserted(position);
    }

    public void deleteItem(int position) {
        contacts.remove(position);
        this.notifyItemRemoved(position);
    }
}