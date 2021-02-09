package com.ardy.androiddevelopertest.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ardy.androiddevelopertest.R;
import com.ardy.androiddevelopertest.response.JobResponse;
import com.ardy.androiddevelopertest.view.JobDetail;
import com.bumptech.glide.Glide;

import java.util.List;


public class AdapterJob extends RecyclerView.Adapter <AdapterJob.ViewHolder>{

    private List<JobResponse> jobList;
    private Context context;


    public AdapterJob(Context context, List<JobResponse> jobLists)
    {
        this.context = context;
        this.jobList = jobLists;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.job_list_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final JobResponse job = jobList.get(position);

        holder.jobCompany.setText(job.getCompany());
        holder.jobLocation.setText(job.getLocation());
        holder.jobTitle.setText(job.getTitle());

        Glide
                .with(context)
                .load(job.getCompanyLogo())
                .thumbnail(0.3f)
                .placeholder(R.drawable.ic_baseline_broken_image_24)
                .into(holder.jobImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fulltime="";
                if (job.getType().equals("Full Time")){
                    fulltime = "Yes";
                }else{
                    fulltime = "No";
                }
                Intent intent = new Intent(context, JobDetail.class);
                intent.putExtra("company",job.getCompany());
                intent.putExtra("location",job.getLocation());
                intent.putExtra("logo",job.getCompanyLogo());
                intent.putExtra("link",job.getCompanyUrl());
                intent.putExtra("title",job.getTitle());
                intent.putExtra("fulltime",fulltime);
                intent.putExtra("deskripsi",job.getDescription());
                context.startActivity(intent);
            }
        });


    }



    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView jobImage;
        public TextView jobTitle,jobLocation,jobCompany;


        public ViewHolder(View itemView) {
            super(itemView);

            jobImage = itemView.findViewById(R.id.jobImage);
            jobTitle = itemView.findViewById(R.id.companyTxt);
            jobLocation = itemView.findViewById(R.id.locationTxt);
            jobCompany = itemView.findViewById(R.id.jobCompany);


        }
    }

    public void filter(String text,List<JobResponse> itemsCopy) {

        if(text.isEmpty()){
            jobList.clear();
            jobList.addAll(itemsCopy);
        } else{
            jobList.clear();
            text = text.toLowerCase();
            for(JobResponse item: itemsCopy){
                if(item.getDescription().toLowerCase().contains(text) || item.getTitle().toLowerCase().contains(text) || item.getCompany().toLowerCase().contains(text)){
                    jobList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void applyFilter(String text,String location,List<JobResponse> itemsCopy,boolean fulltime) {

        if(text.isEmpty() &&location.isEmpty() && !fulltime){
            jobList.clear();
            jobList.addAll(itemsCopy);

        } else if (text.isEmpty() && location.isEmpty() && fulltime) {
            jobList.clear();
            for(JobResponse item: itemsCopy) {
                if(item.getType().toLowerCase().contains("full time")){
                    jobList.add(item);
                }
            }
        } else if (text.isEmpty() && !location.isEmpty() && !fulltime) {
            jobList.clear();
            for(JobResponse item: itemsCopy) {
                if(item.getLocation().toLowerCase().contains(location)){
                    jobList.add(item);
                }
            }
        }
        else if (text.isEmpty() && !location.isEmpty() && fulltime) {
            jobList.clear();
            for(JobResponse item: itemsCopy) {
                if(((item.getLocation().toLowerCase().contains(location)) && (item.getType().toLowerCase().contains("full time")))){
                    jobList.add(item);
                }
            }
        }  else if (!text.isEmpty() && location.isEmpty() && !fulltime) {
            jobList.clear();
            for(JobResponse item: itemsCopy) {
                if(item.getDescription().toLowerCase().contains(text) || item.getTitle().toLowerCase().contains(text) || item.getCompany().toLowerCase().contains(text)){
                    jobList.add(item);
                }
            }
        } else if (!text.isEmpty() && location.isEmpty() && fulltime) {
            jobList.clear();
            for (JobResponse item : itemsCopy) {
                if (item.getDescription().toLowerCase().contains(text) || item.getTitle().toLowerCase().contains(text) || item.getCompany().toLowerCase().contains(text) && (item.getType().toLowerCase().contains("full time"))) {
                    jobList.add(item);
                }
            }
        } else if (!text.isEmpty() && !location.isEmpty() && !fulltime) {
            jobList.clear();
            for (JobResponse item : itemsCopy) {
                if (item.getDescription().toLowerCase().contains(text) || item.getTitle().toLowerCase().contains(text) || item.getCompany().toLowerCase().contains(text) && (item.getLocation().toLowerCase().contains(location))) {
                    jobList.add(item);
                }
            }
        }else if (!text.isEmpty() && !location.isEmpty() && fulltime) {
            jobList.clear();
            for (JobResponse item : itemsCopy) {
                if ((item.getDescription().toLowerCase().contains(text) || item.getTitle().toLowerCase().contains(text) || item.getCompany().toLowerCase().contains(text)) && (item.getLocation().toLowerCase().contains(location)) && (item.getType().toLowerCase().contains("full time")) ) {
                    jobList.add(item);
                }
            }
        }

        notifyDataSetChanged();
    }








}

