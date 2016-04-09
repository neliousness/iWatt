package uk.ac.hw.macs.nl148.iwatt;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;



/**
 * Author: Neio Lucas
 * File : TimeTableAdapter.java
 * Platform : Android Operating System
 * Date:  19/02/2016.
 * Description: This class uses an Array adapter to display each item in a list view using a pojos(TimeTableData) properties
 */
public class TimeTableAdapter extends ArrayAdapter<TimeTableData> {


    public TimeTableAdapter(Context context, ArrayList<TimeTableData> tableData) {
        super(context, R.layout.rowlayoutt, tableData);
    }

    private static class ViewHolder{
        TextView coursename;
        TextView type;
        TextView room1;
        TextView room2;
        TextView starttime;
        TextView endtime;
        TextView current_time;
        TextView semester;

    }

    public View getView(int position , View convertView , ViewGroup parent)
    {
        TimeTableData data = getItem(position);

        ViewHolder viewHolder;
        if(convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.rowlayoutt,parent,false);
            viewHolder.coursename = (TextView) convertView.findViewById(R.id.coursetitle);
            viewHolder.type = (TextView) convertView.findViewById(R.id.coursetype);
            viewHolder.room1 = (TextView) convertView.findViewById(R.id.room1);
            viewHolder.room2 = (TextView) convertView.findViewById(R.id.room2);
            viewHolder.starttime = (TextView) convertView.findViewById(R.id.start_time);
            viewHolder.endtime = (TextView) convertView.findViewById(R.id.endtime);
            viewHolder.current_time = (TextView) convertView.findViewById(R.id.current_time);
            viewHolder.semester = (TextView) convertView.findViewById(R.id.semester);
            convertView.setTag(viewHolder);


        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Simple tfb.ttf");
        //viewHolder.coursename.setTypeface(tf);
        viewHolder.coursename.setText(data.getTitle());
        viewHolder.type.setText("Type :"+data.getType());
        viewHolder.room1.setText("Room 1 :" +data.getRoom1());
        viewHolder.room2.setText("Room 2 :" +data.getRoom2());
        viewHolder.starttime.setText(data.getStarttime());
        viewHolder.endtime.setText(data.getEndtime());
        viewHolder.semester.setText("Semester :"+data.getSemester());

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String formattedDate = df.format(c.getTime());

        viewHolder.current_time.setText(formattedDate);

        return convertView;
    }
}
