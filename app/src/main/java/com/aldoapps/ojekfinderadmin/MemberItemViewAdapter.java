package com.aldoapps.ojekfinderadmin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.aldoapps.ojekfinderadmin.model.Member;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by user on 10/12/2015.
 */
public class MemberItemViewAdapter extends RecyclerView.Adapter<MemberItemViewAdapter.ViewHolder> {

    private final ArrayList<Member> mMemberList;

    public MemberItemViewAdapter(ArrayList<Member> members){
        mMemberList = members;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.member_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Member member = mMemberList.get(position);
        holder.mName.setText(member.getUserName());
        holder.mRating.setRating(member.getRating());
        holder.mStatus.setText(member.getStatus());

        holder.mAvatar.setImageDrawable(null);
        if(member.getAvatarUrl() != null){
            Picasso.with(holder.mView.getContext())
                    .load(member.getAvatarUrl())
                    .transform(new CircleTransform())
                    .into(holder.mAvatar);
        }else{
            holder.mAvatar.setIsColorful(true);
            holder.mAvatar.setLetter(member.getUserName());
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, MemberDetailActivity.class);
                intent.putExtra("asdf", member.getUserName());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mMemberList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public final View mView;
        public final LetterImageView mAvatar;
        public final TextView mName;
        public final TextView mStatus;
        public final RatingBar mRating;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            mAvatar = (LetterImageView) itemView.findViewById(R.id.member_avatar);
            mName = (TextView) itemView.findViewById(R.id.member_name);
            mStatus = (TextView) itemView.findViewById(R.id.member_status);
            mRating = (RatingBar) itemView.findViewById(R.id.member_rating_bar);
        }
    }
}
