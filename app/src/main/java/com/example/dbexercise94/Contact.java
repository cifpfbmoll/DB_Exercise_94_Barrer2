package com.example.dbexercise94;

public class Contact {

    private int _id;
    private String _name;
    private int _phone;

    public Contact(int id, String name, int phone) {
        this._id = id;
        this._name = name;
        this._phone = phone;
    }

    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_phone() {
        return _phone;
    }

    public void set_phone(int _phone) {
        this._phone = _phone;
    }
}
