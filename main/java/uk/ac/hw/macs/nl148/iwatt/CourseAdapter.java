package uk.ac.hw.macs.nl148.iwatt;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mrnel on 14/03/2016.
 */
public class CourseAdapter extends ArrayAdapter<Course> {

    private ViewHolder publicholder;

    public CourseAdapter(Context context, ArrayList<Course> courses) {
       super(context, R.layout.course, courses);
    }

    private static class ViewHolder {
        TextView course;
        TextView coordinator;
        CheckBox checkbox;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        //Get the data item for this position
        Course course = getItem(position);

        //Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.course, parent, false);

            //Initialing view for data population
            viewHolder.course = (TextView) convertView.findViewById(R.id.dp_course);
            viewHolder.coordinator = (TextView) convertView.findViewById(R.id.dp_coordinator);
            viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.dp_checkbox);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        //Populate the data into the template view using the data object

        viewHolder.course.setText(course.getCoursename());
        viewHolder.coordinator.setText(course.getCoordinator());
        publicholder = viewHolder;



        //Return the completed view to render on screen
        return convertView;
    }

    public String getCourseName()
    {
        return publicholder.course.getText().toString();
    }

    public String getCoordinator()
    {
        return publicholder.coordinator.getText().toString();
    }


}



