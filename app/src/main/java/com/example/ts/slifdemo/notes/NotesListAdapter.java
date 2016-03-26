/*
 * Copyright (c) 2010-2011, The MiCode Open Source Community (www.micode.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.ts.slifdemo.notes;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.TextView;


import com.example.ts.slifdemo.R;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;


public class NotesListAdapter extends CursorAdapter {
    private static final String TAG = "NotesListAdapter";
    private Context mContext;
    private HashMap<Integer, Boolean> mSelectedIndex;

    public NotesListAdapter(Context context, Cursor c) {
        super(context, c);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        ViewHolder viewHolder= new ViewHolder();
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View view=inflater.inflate(R.layout.item ,parent,false);

        viewHolder.title=(TextView) view.findViewById(R.id.title );
        viewHolder.content=(TextView) view.findViewById(R.id.content );
        viewHolder.itemId=(TextView) view.findViewById(R.id.item_id );
        viewHolder.date=(TextView) view.findViewById(R.id.date );
        view.setTag(viewHolder);
        return view;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder=(ViewHolder) view.getTag();
        //从数据库中查询标题
        int id = cursor.getInt(0);
        String title=cursor.getString(1);
        //从数据库中查内容
        String content=cursor.getString(2);
        String date = cursor.getString(4);
        Log.i(TAG, id+"__"+title + "__" + content+ "__" + date);
        viewHolder.title.setText(title);
        viewHolder.content.setText(content);
        viewHolder.itemId.setText(""+id);
        viewHolder.date.setText(date);
    }

    private static class ViewHolder
    {
        TextView title;
        TextView content;
        TextView itemId;
        TextView date;

    }
}
