package com.brian.gymapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomRecylerAdapter_Instructors extends RecyclerView.Adapter<CustomRecylerAdapter_Instructors.ViewHolder>{
    private Context context;
    private List<InstructorsUtils> instructorsUtils;

    public CustomRecylerAdapter_Instructors(Context context, List instructorsUtils){
        this.context = context;
        this.instructorsUtils = instructorsUtils;
    }


    @NonNull
    @Override
    public CustomRecylerAdapter_Instructors.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item_trainer,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomRecylerAdapter_Instructors.ViewHolder holder, int position) {
        holder.itemView.setTag(instructorsUtils.get(position));//item view
        InstructorsUtils iu = instructorsUtils.get(position);//model

        holder.instructor_name.setText(iu.getInstructor_name());
        holder.instructor_gender.setText(iu.getGender());
        holder.instructor_email.setText(iu.getEmail());
        holder.instructor_phone.setText(iu.getInstructor_contacts());
        holder.instructor_gym.setText(iu.getGym_name());
        //go into the images section
        String image_url = public_url.instructors_images+iu.getInstructor_image();
        Picasso.with(context).load(image_url).fit().centerInside().into(holder.instructor_image);
    }

    @Override
    public int getItemCount() {
        return instructorsUtils.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //create instance of textviews
        public TextView instructor_name;
        public TextView instructor_gender;
        public TextView instructor_phone;
        public TextView instructor_email;
        public TextView instructor_gym;
        public ImageView instructor_image;

        public ViewHolder(View itemView){
            super(itemView);

            instructor_name =(TextView)itemView.findViewById(R.id.instructor_name);
            instructor_gender = (TextView)itemView.findViewById(R.id.instructor_gender);
            instructor_phone = (TextView)itemView.findViewById(R.id.instructor_phone);
            instructor_email = (TextView)itemView.findViewById(R.id.instructor_email);
            instructor_gym = (TextView)itemView.findViewById(R.id.instructor_gym);
            instructor_image = (ImageView)itemView.findViewById(R.id.instructor_image);
        }

    }

}
