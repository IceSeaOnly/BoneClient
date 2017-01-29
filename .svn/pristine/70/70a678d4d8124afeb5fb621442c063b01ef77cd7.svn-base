package com.qdxiaogutou.boneclient.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qdxiaogutou.boneclient.Entity.School;
import com.qdxiaogutou.boneclient.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/12/3.
 */
public class SchoolItemAdapter extends BaseAdapter {
    private ArrayList<School> schools;
    private Activity activity;
    private LayoutInflater inflater;

    public SchoolItemAdapter(ArrayList<School> schools, Activity activity) {
        this.schools = schools;
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return schools.size();
    }

    @Override
    public School getItem(int position) {
        return schools.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }


    class VH{
        TextView schoolname;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH vh = null;
        if(convertView == null){
            vh = new VH();
            convertView = inflater.inflate(R.layout.school_item,null);
            vh.schoolname = (TextView) convertView.findViewById(R.id.schoolitem);
        }else{
            vh = (VH) convertView.getTag();
        }

        vh.schoolname.setText(getItem(position).getSchoolName()+" "+getItem(position).getTag());
        return convertView;
    }
}
