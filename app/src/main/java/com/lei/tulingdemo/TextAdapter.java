package com.lei.tulingdemo;

import android.content.Context;
import android.content.ReceiverCallNotAllowedException;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yanle on 2018/2/22.
 */

public class TextAdapter extends BaseAdapter {

    private List<ListData> list;
    private Context mContext;//上下文
    private RelativeLayout layout;

    public TextAdapter(List<ListData> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if(list.get(position).getFlag() == ListData.RECIVER) {
            layout = (RelativeLayout) inflater.inflate(R.layout.leftitem, null);
        }
        if(list.get(position).getFlag() == ListData.SEND) {
            layout = (RelativeLayout) inflater.inflate(R.layout.rightitem, null);
        }
        TextView tv = layout.findViewById(R.id.tv);
        TextView time= layout.findViewById(R.id.time);
        tv.setText(list.get(position).getContent());
        time.setText(list.get(position).getTime());
        return layout;
    }
}
