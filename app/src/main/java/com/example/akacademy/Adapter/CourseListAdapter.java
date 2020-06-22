package com.example.akacademy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.akacademy.Model.MyCourse;
import com.example.akacademy.R;

import java.util.ArrayList;

public class CourseListAdapter extends ArrayAdapter<MyCourse> {

    private final Context context;
    private final ArrayList<MyCourse> values;

    public CourseListAdapter(Context context,ArrayList<MyCourse> list){
        super(context, R.layout.list_view,list);
        this.context=context;
        this.values=list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_view,parent,false);

        TextView courseName = (TextView)rowView.findViewById(R.id.course_name);
        courseName.setText(values.get(position).getName());


        return rowView;
    }


}
