package com.example.dbexercise94;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAssistant extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "myDB";
    private static final String TABLE_NAME = "ContactList";

    private SQLiteDatabase db;

    public DBAssistant(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "phone INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int previousVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void close() {
        db.close();
        super.close();
    }

    public Cursor getEntries() {
        Cursor cur = db.rawQuery("SELECT id AS _id, name, phone FROM " + TABLE_NAME,
                null);
        return cur;
    }

    public Contact addContact(String name, int phone) {
        ContentValues contents = new ContentValues();
        contents.put("name", name);
        contents.put("phone", phone);

        int id = (int) db.insert(TABLE_NAME, null, contents);
        if (id != -1) return new Contact(id, name, phone);
        else          return null;
    }

    public Contact updateContact(int id, String name, int phone) {
        String[] ids = {String.valueOf(id)};

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone", phone);

        int updatedContacts = db.update(TABLE_NAME, values, "id=?", ids);

        if (updatedContacts == 1) return new Contact(id, name, phone);
        else return null;
    }

    public boolean deleteContact(int id) {
        String[] ids = {String.valueOf(id)};

        int deletedContacts = db.delete(TABLE_NAME, "id=?", ids);

        if (deletedContacts == 1) return true;
        else return false;
    }
}
