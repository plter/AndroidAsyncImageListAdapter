package com.plter.aila.demos;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.plter.aila.AsyncListViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

        adapter = new AsyncListViewAdapter(this, R.layout.listview_cell, new String[]{"icon", "name"}, new int[]{R.id.ivIcon, R.id.tvName});
        lv.setAdapter(adapter);


        final ProgressDialog pd = ProgressDialog.show(this, "Loading...", "Loading data");
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    InputStream inputStream = new URL(String.format("%s/data.json", Config.BASE_URL)).openStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String line = null;
                    StringBuilder content = new StringBuilder();

                    while ((line = br.readLine()) != null) {
                        content.append(line);
                    }
                    inputStream.close();

                    return content.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                pd.dismiss();

                if (s != null) {
                    try {
                        JSONArray jArr = new JSONArray(s);

                        List<ListViewDemoCellData> list = new ArrayList<ListViewDemoCellData>();
                        for (int i = 0; i < jArr.length(); i++) {
                            list.add(new ListViewDemoCellData(jArr.optJSONObject(i)));
                        }

                        adapter.addAll(list);
                    } catch (JSONException e) {
                        e.printStackTrace();

                        Toast.makeText(ListViewDemoActivity.this, "JSON format error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ListViewDemoActivity.this, "Can't load data", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
}
