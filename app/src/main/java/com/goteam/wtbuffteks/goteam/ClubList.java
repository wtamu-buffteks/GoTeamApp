package com.goteam.wtbuffteks.goteam;


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
 * Created by rbroome on 2/24/17.
 */

public class ClubList  extends Fragment{

    private RecyclerView clubRecycler;
    private ClubAdapter clubAdapter;
    private ArrayList<Club> clubList;
    private ParseController parseController;

    private List<ParseObject> clubObjects;

    public ClubList() {
        parseController = new ParseController();
        clubList = new ArrayList<>();
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.vertical_recycler_view,container,false);

        clubRecycler = (RecyclerView) v.findViewById(R.id.vertical_recycler_view);
        clubRecycler.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 1));
        //Context context = this.getContext();

        ParseQuery<ParseObject> query = ParseQuery.getQuery(ParseConstants.classNameClub);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                Log.d("Objects", "" + objects.size());
                //Log.d("Parse Error", e.getLocalizedMessage());
                clubObjects = objects;
                clubAdapter = new ClubAdapter(clubObjects);
                clubRecycler.setAdapter(clubAdapter);
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater)
    {
        super.onCreateOptionsMenu(menu,menuInflater);
        menuInflater.inflate(R.menu.search_item,menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView1 = (SearchView) searchItem.getActionView();


        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("searchQuerySubmit","query is : " + query);
                searchClub(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("searchQueryOnTextChange","query textchange is : " + newText);
                return false;
            }
        });

        searchView1.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Log.d("search","search closed");
                searchClub("");
                return false;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menu_item_clear:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void searchClub(String query)
    {
        List<Club> clubs = new ArrayList<>();

        for(int i = 0;i < clubList.size();i++)
        {
            String clubName = clubList.get(i).getName();

            char firstInitial = clubName.charAt(0);
            String restOfName = clubName.substring(1);
            String secondFullName = String.valueOf(firstInitial).toLowerCase() + restOfName;


            if(secondFullName.contains(query))
                clubs.add(clubList.get(i));
        }
        clubList.clear();
        clubList.addAll(clubs);
        clubAdapter.notifyDataSetChanged();

    }
}

