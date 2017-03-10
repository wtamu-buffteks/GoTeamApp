package com.goteam.wtbuffteks.goteam;


//import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import com.goteam.wtbuffteks.goteam.constants.ParseConstants;

/**
 * Created by Bubsavvy on 10/18/2016.
 */
public class EventList extends Fragment {

    private RecyclerView eventRecycler;
    private EventAdapter eventAdapter;
    //private ArrayList<Event> eventList;
    private ParseController parseController;
    private EventAdapter.eventListInterface fListener;

    private List<ParseObject> eventObjects;

    public EventList() {
        parseController = new ParseController();
        //eventList = new ArrayList<>();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.vertical_recycler_view, container, false);

        eventRecycler = (RecyclerView) v.findViewById(R.id.vertical_recycler_view);
        eventRecycler.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 1));
        //Context context = this.getContext();

        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.classNameEvent);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                Log.d("Objects", "" + objects.size());
                //Log.d("Parse Error", e.getLocalizedMessage());
                eventObjects = objects;
                eventAdapter = new EventAdapter(eventObjects, fListener);
                eventRecycler.setAdapter(eventAdapter);
            }
        });


        // Load Events Into Memory
        //parseController.getEvents(eventList, eventAdapter);
        //eventAdapter.notifyDataSetChanged();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
//        eventAdapter.notifyDataSetChanged();
//        eventList.clear();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EventAdapter.eventListInterface) {
            fListener = (EventAdapter.eventListInterface) context;
        } else {

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.search_item, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView1 = (SearchView) searchItem.getActionView();


        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("searchQuerySubmit", "query is : " + query);
                //searchEvent(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("searchQueryOnTextChange", "query textchange is : " + newText);
                return false;
            }
        });

        searchView1.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.d("search", "search closed");
                //searchEvent("");
                return false;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_clear:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

//    public void searchEvent(String query)
//    {
//        List<Event> events = new ArrayList<>();
//
//        for(int i = 0;i < eventList.size();i++)
//        {
//            String eventName = eventList.get(i).getEventName();
//
//            char firstInitial = eventName.charAt(0);
//            String restOfName = eventName.substring(1);
//            String secondFullName = String.valueOf(firstInitial).toLowerCase() + restOfName;
//
//
//            if(secondFullName.contains(query))
//                events.add(eventList.get(i));
//        }
//        eventList.clear();
//        eventList.addAll(events);
//        eventAdapter.notifyDataSetChanged();
//
//    }}
