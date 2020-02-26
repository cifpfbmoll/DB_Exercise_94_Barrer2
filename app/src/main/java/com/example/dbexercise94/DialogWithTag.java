package com.example.dbexercise94;

import android.app.Dialog;
import android.content.Context;

public class DialogWithTag extends Dialog {

    private Object tag;

    public DialogWithTag(Context context, Object tag) {
        super(context);
        this.tag = tag;
    }

    public Object getTag() {
        return tag;
    }
}
