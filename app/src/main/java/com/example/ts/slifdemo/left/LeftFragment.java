package com.example.ts.slifdemo.left;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ts.slifdemo.R;
import com.example.ts.slifdemo.notes.Notes;

public class LeftFragment extends Fragment {
    SharedPreferences sharedPreferences;
    TextView sex;
    TextView label;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left_fragment, null);
        view.findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                sharedPreferences.edit().clear().commit();
                getActivity().finish();
            }
        });
        ((TextView) view.findViewById(R.id.name)).setText(Notes.name);
        if (Notes.sex != null && !"".equals(Notes.sex)) {
            ((TextView) view.findViewById(R.id.sex)).setText(Notes.sex);
        }
        if (Notes.label != null && !"".equals(Notes.label)) {
            ((TextView) view.findViewById(R.id.label)).setText(Notes.label);
        }
        return view;
    }
//
//不识别
//    public void test(View view){
//
//    }

    //无响应
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.btn_exit:
//                sharedPreferences = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
//                sharedPreferences.edit().clear().commit();
//                getActivity().finish();
//        }
//    }
}
