package com.example.ts.slifdemo.notes;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by TS on 2016/3/23.
 */
public class Item extends LinearLayout {
    TextView title;
    TextView content;
    TextView itemId;
    public Item(Context context) {
        super(context);
    }
    public String getItemId(){
        return itemId.getText().toString();
    }
}
