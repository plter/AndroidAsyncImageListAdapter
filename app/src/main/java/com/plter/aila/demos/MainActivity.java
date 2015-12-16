package com.plter.aila.demos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {


    private ListView lv;
    private ArrayAdapter<IntentWrapper> adapter;
    private AdapterView.OnItemClickListener lvItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            startActivity(adapter.getItem(position).getIntent());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        lv = (ListView) findViewById(R.id.lv);
        lv.setAdapter(adapter);

        adapter.add(new IntentWrapper("ListView示例", new Intent(this, ListViewDemoActivity.class)));
        adapter.add(new IntentWrapper("RecyclerView示例",new Intent(this,RecyclerViewDemoActivity.class)));

        lv.setOnItemClickListener(lvItemClickListener);
    }
}
