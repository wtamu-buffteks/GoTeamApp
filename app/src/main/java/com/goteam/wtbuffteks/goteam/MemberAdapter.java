package com.goteam.wtbuffteks.goteam;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

/**
 * Created by christophercoffee on 9/21/16.
 */

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.MyViewHolder> {

    private List<Member> mMemberList;
    private Context mContext;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView picView;

        public MyViewHolder(View view) {
            super(view);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Member member = mMemberList.get(getAdapterPosition());
                    Bundle args = new Bundle();
                    args.putSerializable("member", member);
                    Fragment detailViewFragment = new MemberDetailView();
                    detailViewFragment.setArguments(args);
                    FragmentActivity mycontext = (FragmentActivity)mContext;
                  /* mycontext.getSupportFragmentManager()
                    //mycontext.getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.start_content, detailViewFragment, "memberDetailViewFragment")
                            .addToBackStack("memberDetailViewFragment").commit();
                            */

                    Intent intent = new Intent(mycontext, MemberDetail.class);
                    intent.putExtras(args);
                    mycontext.startActivity(intent);




                }
            });

            name = (TextView) view.findViewById(R.id.name);
            name.setTextColor(Color.BLACK);

            picView = (ImageView)view.findViewById(R.id.imageView);




        }
    }




    public MemberAdapter(List<Member> memberList, Context context) {
        this.mMemberList = memberList;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_member_list_items, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Member member = mMemberList.get(position);
        holder.name.setText(member.getFirstName() + " " + member.getLastName());
        Log.d("parse","image name:" + member.getPic() );
        Glide.with(mContext)
                .load(member.getPic()).asBitmap()
                .override(100,100)
                .into(new BitmapImageViewTarget(holder.picView){
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable =
                                RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        circularBitmapDrawable.setCircular(true);
                        holder.picView.setImageDrawable(circularBitmapDrawable);
                    }
                });

        //final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(mContext, R.anim.bounce_interpolator);
        // holder.itemView.setAnimation(animAnticipateOvershoot);

    }

    @Override
    public int getItemCount() {
        return mMemberList.size();
    }


}
