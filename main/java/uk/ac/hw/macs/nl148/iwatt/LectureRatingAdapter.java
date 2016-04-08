package uk.ac.hw.macs.nl148.iwatt;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LectureRatingAdapter extends ArrayAdapter<LectureRateData> {

    private AppCompatActivity activity;
    private List<LectureRateData> movieList;
    private ArrayList<LectureRateData> data = new ArrayList<>();

    public LectureRatingAdapter(AppCompatActivity context, int resource, List<LectureRateData> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.movieList = objects;
    }

    @Override
    public LectureRateData getItem(int position) {
        return movieList.get(position);
    }

    public ArrayList<LectureRateData> getResults()
    {
        return data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.question_layout, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
            //holder.ratingBar.getTag(position);
        }

        holder.ratingBar.setOnRatingBarChangeListener(onRatingChangedListener(holder, position));

        holder.ratingBar.setTag(position);
        holder.ratingBar.setRating(getItem(position).getRating());
        holder.question.setText(getItem(position).getQuestion());

        return convertView;
    }

    private RatingBar.OnRatingBarChangeListener onRatingChangedListener(final ViewHolder holder, final int position) {
        return new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                LectureRateData item = getItem(position);
                item.setRating(v);

                    data.add(item);

                Log.i("Adapter", "star: " + v + item.getQuestion());
            }
        };
    }

    private static class ViewHolder {
        private RatingBar ratingBar;
        private TextView question;

        public ViewHolder(View view) {
            ratingBar = (RatingBar) view.findViewById(R.id.rate_course);
            question = (TextView) view.findViewById(R.id.question);
        }
    }
}