package com.example.dbexercise94;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private LinkedList<Contact> mContactList;
    private ContactListAdapter mAdapter;
    private DBAssistant db;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBAssistant(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        mContactList = new LinkedList<>();
        populateList();

        mAdapter = new ContactListAdapter(this, mContactList);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    private void populateList() {
        Cursor cursor = db.getEntries();
        int sizeCursor = cursor.getCount();

        if (sizeCursor != 0) {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex("_id"));
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    int phone = cursor.getInt(cursor.getColumnIndex("phone"));
                    mContactList.add(new Contact(id, name, phone));
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
    }

    public void showNewContactDialog(View view) {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_new_contact);
        dialog.show();
    }

    public void showEditContactDialog(View view) {
        dialog = new DialogWithTag(this, view.getTag());
        dialog.setContentView(R.layout.dialog_edit_contact);

        Contact contact = mContactList.get(searchContact((int) ((DialogWithTag) dialog).getTag()));
        ((EditText) dialog.findViewById(R.id.inputNameField)).setText(contact.get_name());
        ((EditText) dialog.findViewById(R.id.inputPhoneField)).setText(String.valueOf(contact.get_phone()));

        dialog.show();
    }

    public void cancel(View view) {
        dialog.dismiss();
    }

    public void addNewContact(View view) {
        String name = ((EditText) dialog.findViewById(R.id.inputNameField)).getText().toString();
        int phone = Integer.parseInt(((EditText) dialog.findViewById(R.id.inputPhoneField)).getText().toString());

        addContact(name, phone);
        dialog.dismiss();
    }

    public void updateOldContact(View view) {
        String name = ((EditText) dialog.findViewById(R.id.inputNameField)).getText().toString();
        int phone = Integer.parseInt(((EditText) dialog.findViewById(R.id.inputPhoneField)).getText().toString());

        updateContact((int) ((DialogWithTag) dialog).getTag(), name, phone);
        dialog.dismiss();
    }

    public void deleteOldContact(View view) {
        deleteContact((int) ((DialogWithTag) dialog).getTag());
        dialog.dismiss();
    }

    private void addContact(String name, int phone) {
        Contact newContact = db.addContact(name, phone);
        if (newContact != null) {
            mContactList.add(newContact);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void updateContact(int id, String name, int phone) {
        Contact contactUpdated = db.updateContact(id, name, phone);
        if (contactUpdated != null) {
            int index = searchContact(id);
            if (index != -1) {
                mContactList.get(index).set_name(name);
                mContactList.get(index).set_phone(phone);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    private void deleteContact(int id) {
        if (db.deleteContact(id)) {
            mContactList.remove(searchContact(id));
            mAdapter.notifyDataSetChanged();
        }
    }

    private int searchContact(int id) {
        for (int i = 0; i < mContactList.size(); i++) {
            if (id == mContactList.get(i).get_id()) {
                return i;
            }
        }
        return -1;
    }
}
