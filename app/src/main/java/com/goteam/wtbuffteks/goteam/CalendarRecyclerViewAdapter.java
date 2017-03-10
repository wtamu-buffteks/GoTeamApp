package com.goteam.wtbuffteks.goteam;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goteam.wtbuffteks.goteam.constants.ParseConstants;
import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Credit for source code goes to Brett Ponder, created on 8/6/16.
 * Adapted for goteam on 3/3/17.
 */
public class CalendarRecyclerViewAdapter extends RecyclerView.Adapter<CalendarRecyclerViewAdapter.GenericViewHolder> {

    private final List<ParseObject> objects;
    private final CalendarFragment.OnListFragmentInteractionListener fListener;

    public CalendarRecyclerViewAdapter(List<ParseObject> items, CalendarFragment
            .OnListFragmentInteractionListener listener) {
        objects = items;
        fListener = listener;
    }

    @Override
    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_calendarrecyclerview, parent, false);
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GenericViewHolder holder, int position) {

        final CalendarViewHolder cholder = (CalendarViewHolder) holder;
        cholder.calendarObject = objects.get(position);
        final SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("h:mm a", Locale
                .getDefault());
//        Log.d("ParseObject", (String) objects.get(position).get(ParseConstants.dateCalendar));
        cholder.timeTextView.setText(dateFormatForMonth.format((Date) objects.get(position).get
                (ParseConstants.dateCalendar)));
        cholder.eventNameTextView.setText((String)objects.get(position).get(ParseConstants.titleCalendar));
        cholder.eventDescriptionTextView.setText((String) objects.get(position).get
                (ParseConstants.descriptionCalendar));

        cholder.cView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != fListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    fListener.onListFragmentInteractionCalendarDate(cholder.calendarObject);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public class CalendarViewHolder extends GenericViewHolder {
        public final View cView;
        public final TextView timeTextView;
        public final TextView eventNameTextView;
        public final TextView eventDescriptionTextView;
        public ParseObject calendarObject;

        public CalendarViewHolder(View view) {
            super(view);
            cView = view;
            timeTextView = (TextView) view.findViewById(R.id.timeTextView);
            eventNameTextView = (TextView) view.findViewById(R.id.eventNameTextView);
            eventDescriptionTextView = (TextView) view.findViewById(R.id.eventDescTextView);
        }

    }

    public abstract class GenericViewHolder extends RecyclerView.ViewHolder {
        public GenericViewHolder(View view) {
            super(view);
        }

    }
}