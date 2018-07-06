package com.brian.gymapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CustomRecylerAdapter_pastworkouts extends RecyclerView.Adapter<CustomRecylerAdapter_pastworkouts.ViewHolder>{
    private Context context;
    private List<pastWorkoutsUtils> pastWorkoutsUtils;

    public CustomRecylerAdapter_pastworkouts(Context context, List pastWorkoutsUtils){
        this.context = context;
        this.pastWorkoutsUtils = pastWorkoutsUtils;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item_workout_sessions,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(pastWorkoutsUtils.get(position));
        pastWorkoutsUtils pw = pastWorkoutsUtils.get(position);

        holder.tworkoutDate.setText(pw.getWorkoutDate());
        holder.tworkoutType.setText(pw.getWorkoutType());
        holder.tworkoutReps.setText(String.valueOf(pw.getWorkoutReps()));
        holder.tworkoutLocation.setText(pw.getWorkoutLocation());
    }

    @Override
    public int getItemCount() {
        return pastWorkoutsUtils.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tworkoutDate;
        public TextView tworkoutLocation;
        public TextView tworkoutType;
        public TextView tworkoutReps;

        public ViewHolder(View itemView){
            super(itemView);

            tworkoutDate = (TextView)itemView.findViewById(R.id.workout_date);
            tworkoutLocation = (TextView)itemView.findViewById(R.id.workout_location);
            tworkoutType = (TextView)itemView.findViewById(R.id.workout_type);
            tworkoutReps = (TextView)itemView.findViewById(R.id.workout_reps);
        }


    }

}
