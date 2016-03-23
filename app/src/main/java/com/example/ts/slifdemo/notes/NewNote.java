package com.example.ts.slifdemo.notes;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.ts.slifdemo.MainActivity;
import com.example.ts.slifdemo.R;
import com.example.ts.slifdemo.database.MySQLiteHelper;

public class NewNote extends Activity {
    private static String TAG = "NewNote";
    private MySQLiteHelper helper;
    EditText title, content;
    Cursor cursor;
    int selectedId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_note);
        title = (EditText) findViewById(R.id.title);
        content = (EditText) findViewById(R.id.content);
        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle.get("id").toString() != null) {
                helper = new MySQLiteHelper(this);
                String index = bundle.get("id").toString();
                Log.i(TAG, "" + index);
                cursor = helper.getReadableDatabase().rawQuery("select * from note where _id =?", new String[]{index});
                Log.i(TAG, "长度：" + cursor.getColumnCount());
                if (cursor.moveToFirst()) {
                    selectedId = cursor.getInt(0);

                    title.setText(cursor.getString(1));
                    content.setText(cursor.getString(2));
                    Log.i(TAG, "数据：" + cursor.getInt(0) + "_" + cursor.getString(1) + "_" + cursor.getString(2));
                    cursor.close();
                }


            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        String t = title.getText().toString();
        String c = content.getText().toString();
        Log.i(TAG, "插入数据：" + t + "++" + c);
        helper = new MySQLiteHelper(this);
        if (selectedId != 0) {
            helper.getReadableDatabase().execSQL("update note set title = ?, content = ? where _id = ?", new String[]{title.getText().toString(), content.getText().toString(), "" + selectedId});
        } else if (!"".equals(t) && !"".equals(c)) {
            helper.getReadableDatabase().execSQL("insert into note values(null,?,?)", new String[]{t, c});
        }
        Intent intent = getIntent();
        intent.putExtra("update", true);
        setResult(1, intent);
        finish();
        return;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            if (selectedId != 0) {
                Log.i(TAG, "删除条目ID为：" + selectedId);
                helper.getReadableDatabase().execSQL("delete from note where _id = ?", new String[]{"" + selectedId});
            }
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
