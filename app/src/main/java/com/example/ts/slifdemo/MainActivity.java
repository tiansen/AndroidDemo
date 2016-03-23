package com.example.ts.slifdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.ts.slifdemo.database.MySQLiteHelper;
import com.example.ts.slifdemo.login.Login;
import com.example.ts.slifdemo.myLayout.MyLinearLayout;
import com.example.ts.slifdemo.notes.NewNote;
import com.example.ts.slifdemo.notes.NotesListAdapter;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.HashMap;

public class MainActivity extends Activity implements  AdapterView.OnItemClickListener {
    private static String TAG = "MainActivity:";
    private MySQLiteHelper helper;
    MyLinearLayout myLL;
    Cursor cursor;
    public static int screenWidth;
    public static int scrrenHeight;
    LinearLayout leftItem;
    ListView mNotesListView;
    NotesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate");
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        init();
    }

    void initList(){
        helper = new MySQLiteHelper(this);
        cursor = helper.getReadableDatabase().rawQuery("select * from note", null);
        Log.i(TAG, "" + cursor.getCount());
        adapter = new NotesListAdapter(this,cursor);
        mNotesListView.setAdapter(adapter);
    }
    void init() {
        myLL = (MyLinearLayout) findViewById(R.id.fl);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels;
        scrrenHeight = metric.heightPixels;
        mNotesListView = (ListView) findViewById(R.id.notes_list);
        initList();
        mNotesListView.setOnItemClickListener(this);




        //添加一个监听，当此控件被重绘的时候获取出空间的宽度，直接在此处回去的时候是空值
        leftItem = (LinearLayout) findViewById(R.id.left);
        ViewTreeObserver vto = leftItem.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                int width = leftItem.getMeasuredWidth();
                myLL.setLeftWidth(width);
                return true;
            }

        });
    }

    public void move(View view) {
        myLL.smoothScrollTo();
    }

    /**
     * 相应登录按钮
     *
     * @param view
     */
    public void login(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    /**
     * 创建新的note
     *
     * @param view
     */
    public void newNote(View view) {
        Intent intent = new Intent(this, NewNote.class);
        startActivityForResult(intent, 1);
    }

    /**
     * Activity的返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1){
            Bundle result = data.getExtras();
            if (result.getBoolean("update")){
                initList();
            }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView tId = (TextView) view.findViewById(R.id.item_id);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView content = (TextView) view.findViewById(R.id.content);
        Log.i(TAG, "点击的控件Id:"+tId.getText());
        Log.i(TAG, "点击的控件Id:"+title.getText());
        Log.i(TAG, "点击的控件Id:"+content.getText());

        Intent intent = new Intent(this,NewNote.class);
        Bundle bundle = new Bundle();
        bundle.putString("id",tId.getText().toString());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        //finish之后，在返回的时候会进行重新onCreate(),有一个问题，finish了怎么还能返回？
        //确切的而说就没有调用
//        finish();

    }
}
