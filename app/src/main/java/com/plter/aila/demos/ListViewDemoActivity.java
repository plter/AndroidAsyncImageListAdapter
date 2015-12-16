package com.plter.aila.demos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.plter.aila.AsyncListViewAdapter;

/**
 * Created by plter on 12/2/15.
 */
public class ListViewDemoActivity extends AppCompatActivity {


    private ListView lv;
    private AsyncListViewAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview_demo);

        lv = (ListView) findViewById(R.id.lv);

        adapter = new AsyncListViewAdapter(this, android.R.layout.simple_list_item_1, new String[]{"name"}, new int[]{android.R.id.text1});
        lv.setAdapter(adapter);

        adapter.add(new User("ZhangSan"));
        adapter.add(new User("LiSi"));
        adapter.add(new User("WangWu"));
        adapter.add(new User("ZhaoLiu"));
    }
}
