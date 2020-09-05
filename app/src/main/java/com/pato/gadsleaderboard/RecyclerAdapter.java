package com.pato.gadsleaderboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pato.gadsleaderboard.model.LearnerHours;
import com.pato.gadsleaderboard.model.LearnerSkillIq;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<LearnerHours> lstHours;
    private List<LearnerSkillIq> lstSkillIq;
    private Context mContext;


    public RecyclerAdapter(List<LearnerHours> lstHours, List<LearnerSkillIq> lstSkillIq, Context context) {
        this.lstHours = lstHours;
        this.lstSkillIq = lstSkillIq;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (lstHours != null) {
            // Load Learners with Top Hours.
            LearnerHours objLeanerHours = lstHours.get(position);

            // load image into => imageView.
            Picasso.with(mContext).load(objLeanerHours.getBadgeUrl())
                    .into(holder.img_view);

            holder.tv_name.setText(objLeanerHours.getName());
            holder.tv_desc.setText(String.valueOf(objLeanerHours.getHours()) + " Learning Hours, " + objLeanerHours.getCountry() + ".");

        } else if (lstSkillIq != null) {
            // Load Learners with Top SKILL_IQ.
            LearnerSkillIq objSkillIq = lstSkillIq.get(position);

            // Load image into => imageView.
            Picasso.with(mContext).load(objSkillIq.getBadgeUrl())
                    .into(holder.img_view);

            holder.tv_name.setText(objSkillIq.getName());
            holder.tv_desc.setText(String.valueOf(objSkillIq.getScore()) + " Skill IQ Score, " + objSkillIq.getCountry() + ".");
        }


    }

    @Override
    public int getItemCount() {
        int lSize = 0;
        if (lstHours != null)
            lSize = lstHours.size();
        else if (lstSkillIq != null)
            lSize = lstSkillIq.size();

        return lSize;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name, tv_desc;
        private ImageView img_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            img_view = itemView.findViewById(R.id.img_badge);
            tv_desc = itemView.findViewById(R.id.tv_desc);

        }
    }
}
