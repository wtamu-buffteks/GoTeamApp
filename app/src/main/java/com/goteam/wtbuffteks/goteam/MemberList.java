package com.goteam.wtbuffteks.goteam;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.goteam.wtbuffteks.goteam.constants.ParseConstants;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by christophercoffee on 9/20/16.
 */


public class MemberList extends Fragment {

    private RecyclerView mMemberRecyclerView;
    private MemberAdapter mMemberAdapter;
    private List<Member> mMemberList ;
    private List<Member> mMemberListSave ;


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);



    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.vertical_recycler_view,container,false);
        v.setBackgroundColor(Color.WHITE);



        mMemberList = new ArrayList<>();
        //used to get back all members, after search query
        mMemberListSave = new ArrayList<>();


        mMemberRecyclerView = (RecyclerView) v.findViewById(R.id.vertical_recycler_view);
        mMemberRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        mMemberAdapter = new MemberAdapter(mMemberList,getActivity());
        mMemberRecyclerView.setAdapter(mMemberAdapter);


        //add members to memberlist. Pass adapter so it can notify addition of members, after pulling data from online
        getMembersFromParse(mMemberList,mMemberAdapter, mMemberListSave,getActivity());

        ColorDrawable dividerColor = new ColorDrawable(Color.BLACK);
        mMemberRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));

        mMemberAdapter.notifyDataSetChanged();



        return v;
    }



    @Override
    public void onResume()
    {
        super.onResume();
        mMemberAdapter.notifyDataSetChanged();

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
                lookForMember(query);
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
                lookForMember("");
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

    public void lookForMember(String query)
    {
        List<Member> new_memberList = new ArrayList<>();

        if(!query.trim().isEmpty())
        {
            for(int i = 0;i < mMemberList.size();i++)
            {
                String firstname = mMemberList.get(i).getFirstName();
                String lastname = mMemberList.get(i).getLastName();

                char firstInitial = firstname.charAt(0);
                String restOfName = firstname.substring(1);
                String secondFullName = String.valueOf(firstInitial).toLowerCase() + restOfName;

                char firstInitial2 = lastname.charAt(0);
                String restOfName2 = lastname.substring(1);
                String thirdFullName = String.valueOf(firstInitial2).toLowerCase() + restOfName2;


                if(secondFullName.contains(query) || thirdFullName.contains(query) || firstname.equalsIgnoreCase(query) || lastname.equalsIgnoreCase(query))
                {
                    new_memberList.add(mMemberList.get(i));
                    Log.d("add member search",mMemberList.get(i).getFirstName());
                }
            }
            mMemberList.clear();
            mMemberList.addAll(new_memberList);
            mMemberAdapter.notifyDataSetChanged();
        }
        else
        {
            mMemberList.clear();
            mMemberList.addAll(mMemberListSave);
            mMemberAdapter.notifyDataSetChanged();


        }

    }

    public void getMembersFromParse(final List<Member> members, final MemberAdapter adapter, final List<Member> saveAdapter, final Context context)
    {
        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    // The query was successful.
                    for(int i = 0;i < objects.size(); i++) {

                        ParseUser user = objects.get(i);
                        Member member = new Member();
                        member.setFirstName(user.getString(ParseConstants.firstNameUser));
                        member.setLastName(user.getString(ParseConstants.lastNameUser));
                        member.setId(user.getObjectId());
                        Log.d("parsegmem", user.getObjectId());
                        member.setEmail(user.getString(ParseConstants.emailUser));

                        member.setPrivilege(Integer.parseInt(user.getString(ParseConstants.userPrivilege)));
                        member.setUserName(user.getString(ParseConstants.userName));
                        member.setNumber(user.getString(ParseConstants.phoneNumberUser));

                        member.setPic(user.getParseFile(ParseConstants.pictureUser).getUrl());
                        members.add(member);
                    }
                    saveAdapter.addAll(members);
                    adapter.notifyDataSetChanged();

                } else {
                    // Something went wrong.
                    Log.d("Parse", e.toString());

                }
            }
        });



    }


    private class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {
            mDivider = context.getResources().getDrawable(R.drawable.line_divider);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

}


