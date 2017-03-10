package com.goteam.wtbuffteks.goteam;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.goteam.wtbuffteks.goteam.constants.ParseConstants;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Credit for source code goes to Brett Ponder, created on 8/6/16.
 * Adapted for goteam on 3/3/17.
 */

public class CalendarFragment extends Fragment {


    private List<ParseObject> calendarObjects;
    private RecyclerView recyclerView;
    private OnListFragmentInteractionListener mListener;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);



        Context context = v.getContext();
        recyclerView = (RecyclerView) v.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        final SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM yyyy", Locale
                .getDefault());
        final TextView cTextView = (TextView) v.findViewById(R.id.monthTextView);

        final CompactCalendarView compactCalendarView = (CompactCalendarView) v.findViewById(R.id.compactcalendar_view);

        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.classNameCalendar);
        query.setLimit(1000);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                calendarObjects = list;
                addEventsToCalendar(list, compactCalendarView);
                showTheEvents(compactCalendarView.getFirstDayOfCurrentMonth());
            }
        });

        cTextView.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));



        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                showTheEvents(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                cTextView.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });

        return v;
    }

    private void showTheEvents(Date selectedDate) {
        List<ParseObject> finalObjects = new ArrayList<ParseObject>();
        for (ParseObject object : calendarObjects) {
            Date objectDate = (Date) object.get(ParseConstants.dateCalendar);
            if ((objectDate.getTime() > getStartDate(selectedDate).getTime()) && (objectDate.getTime() <
                    getEndDate
                            (selectedDate).getTime())) {

                finalObjects.add(object);

            }
        }
        final CalendarRecyclerViewAdapter adapter = new CalendarRecyclerViewAdapter(finalObjects,
                mListener);
        recyclerView.setAdapter(adapter);
    }

    private void addEventsToCalendar(List<ParseObject> calendarEventObjects, CompactCalendarView
            compactCalendarView) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        List<Event> events = new ArrayList<Event>();
        for (ParseObject object : calendarEventObjects) {
            Date theDate = (Date) object.get(ParseConstants.dateCalendar);
            calendar.setTime(theDate);
            events.add(new Event(Color.argb(255,26,92,167),calendar.getTimeInMillis()));
        }
        compactCalendarView.addEvents(events);
    }

    private Date getStartDate(Date theDate) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(theDate);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private Date getEndDate(Date theDate) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTime(theDate);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteractionCalendarDate(ParseObject item);
    }

}