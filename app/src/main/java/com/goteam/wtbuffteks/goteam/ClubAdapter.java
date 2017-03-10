package com.goteam.wtbuffteks.goteam;


import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goteam.wtbuffteks.goteam.constants.ParseConstants;
import com.parse.ParseFile;
import com.parse.ParseObject;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by rbroome on 2/24/17.
 */

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.ClubViewHolder> {

    private ArrayList<Club> clubList;
    private Fragment clubFragment;
    private Context mContext;
    private List<ParseObject> clubObjects;

    public ClubAdapter(List<ParseObject> objects){
        clubObjects = objects;
    }
    public ClubAdapter(ArrayList<Event> eventList, Fragment targetFragment) {
        this.clubList = clubList;
        clubFragment = targetFragment;
        mContext = targetFragment.getActivity().getApplicationContext();
    }

    @Override
    public ClubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_club_list_items, parent, false);
        return new ClubViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ClubViewHolder holder, final int position) {

        ParseObject clubObject = clubObjects.get(position);
        holder.Name.setText(clubObject.getString(ParseConstants.nameClub));
        ParseFile logoView = clubObject.getParseFile(ParseConstants.logoImageClub);
        ParseFile backgroundView = clubObject.getParseFile(ParseConstants.backgroundImageClub);
//        if(logoView.getUrl().contains("http://buffteks.net/goteam/files/")){
//            Ion.with(holder.logoView).load(logoView.getUrl());
//        }else{
//            logoView.getDataInBackground(new GetDataCallback() {
//                @Override
//                public void done(byte[] data, ParseException e) {
//                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
//                    holder.logoView.setImageBitmap(bmp);
//                }
//            });
//        }
//        if(backgroundView.getUrl().contains("http://buffteks.net/goteam/files/")){
//            Ion.with(holder.backgroundView).load(backgroundView.getUrl());
//        }else{
//            backgroundView.getDataInBackground(new GetDataCallback() {
//                @Override
//                public void done(byte[] data, ParseException e) {
//                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
//                    holder.backgroundView.setImageBitmap(bmp);
//                }
//            });
//        }



    }

    @Override
    public int getItemCount() {
        return clubObjects.size();
    }


    public class ClubViewHolder extends RecyclerView.ViewHolder {

        public ImageView logoView;
        public ImageView backgroundView;
        public TextView Name;
        public ClubViewHolder(View view) {
            super(view);

            logoView = (ImageView) view.findViewById(R.id.logoImage);
            backgroundView = (ImageView) view.findViewById(R.id.clubBackgroundView);
            Name = (TextView) view.findViewById(R.id.clubNameTextView);



        }


    }

}
