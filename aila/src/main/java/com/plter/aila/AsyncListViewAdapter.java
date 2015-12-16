package com.plter.aila;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.plter.aila.tools.DynimacObjectUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by plter on 12/2/15.
 */
public class AsyncListViewAdapter extends BaseAdapter {


    private List<Object> dataSource = new ArrayList<>();
    private Context context = null;
    private int resId = -1;
    private String[] fromKeys;
    private int[] toIds;
    private Map<Integer, Object> viewHolder;


    public AsyncListViewAdapter(Context context, int resId, String[] fromKeys, int[] toIds) {
        this.context = context;
        this.resId = resId;
        this.fromKeys = fromKeys;
        this.toIds = toIds;
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    public <T> T getItemWithType(int position) {
        return (T) getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(Object item) {
        dataSource.add(item);
        notifyDataSetChanged();
    }

    public void addAll(Collection<?> items) {
        dataSource.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View cell, ViewGroup parent) {
        Map<Integer, Object> viewHolder = null;

        if (cell == null) {
            cell = LayoutInflater.from(getContext()).inflate(resId, null);

            viewHolder = new HashMap<>();
            for (int targetId :
                    toIds) {
                viewHolder.put(targetId, cell.findViewById(targetId));
            }

            cell.setTag(viewHolder);
        }

        Object data = getItem(position);
        viewHolder = (Map<Integer, Object>) cell.getTag();
        Object target;
        CacheableImageView targetIv;
        String value;

        for (int i = 0; i < fromKeys.length; i++) {
            target = viewHolder.get(toIds[i]);
            value = DynimacObjectUtil.getFieldValue(data, fromKeys[i]).toString();
            if (target instanceof TextView) {
                ((TextView) target).setText(value);
            } else if (target instanceof CacheableImageView) {
                targetIv = (CacheableImageView) target;

                targetIv.abortLoading();
                targetIv.tryToReleaseCurrentBitmap();
                targetIv.loadImage(value);
            }
        }
        return cell;
    }


    public Context getContext() {
        return context;
    }
}
