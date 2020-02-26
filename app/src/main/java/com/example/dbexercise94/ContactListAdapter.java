package com.example.dbexercise94;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

    private LinkedList<Contact> mContactList;
    private LayoutInflater mInflater;
    private Context context;

    public ContactListAdapter(Context context, LinkedList<Contact> contactList) {
        mInflater = LayoutInflater.from(context);
        this.mContactList = contactList;
        this.context = context;
    }

    @Override
    @NonNull
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.row, parent, false);
        return new ContactViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact mCurrent = mContactList.get(position);
        holder.contactView.setText(mCurrent.get_name());
        holder.id = mCurrent.get_id();
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView contactView;
        private int id;
        private ContactListAdapter clAdapter;

        public ContactViewHolder(View itemView, ContactListAdapter adapter) {
            super(itemView);
            contactView = itemView.findViewById(R.id.contactName);
            this.clAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            view.setTag(id);
            ((MainActivity) context).showEditContactDialog(view);
        }
    }
}
