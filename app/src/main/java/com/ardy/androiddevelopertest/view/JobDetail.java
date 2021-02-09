package com.ardy.androiddevelopertest.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ardy.androiddevelopertest.R;
import com.bumptech.glide.Glide;

public class JobDetail extends AppCompatActivity {

    ImageView jobImage;
    TextView companyTxt,locationTxt,linkTxt,titleTxt,fulltimeTxt,descriptionTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

        init();
        showData();
    }

    private void showData() {

        Intent  intent = getIntent();
        String companyUrl = intent.getStringExtra("link");
        String logoUrl = intent.getStringExtra("logo");

        companyTxt.setText(intent.getStringExtra("company"));
        locationTxt.setText(intent.getStringExtra("location"));
        titleTxt.setText(intent.getStringExtra("title"));
        fulltimeTxt.setText(intent.getStringExtra("fulltime"));
        descriptionTxt.setText(Html.fromHtml(intent.getStringExtra("deskripsi")));
        descriptionTxt.setMovementMethod(LinkMovementMethod.getInstance());
        String html ="<a href=\""+companyUrl+"\">Go to Website</a>";
        linkTxt.setText( Html.fromHtml(html));
        Log.d("sini ",""+html);
        linkTxt.setMovementMethod(LinkMovementMethod.getInstance());

        Glide
                .with(this)
                .load(logoUrl)
                .thumbnail(0.3f)
                .placeholder(R.drawable.ic_baseline_broken_image_24)
                .into(jobImage);
    }

    private void init() {

        ActionBar actionBar;
        jobImage = findViewById(R.id.jobImage);
        companyTxt = findViewById(R.id.companyTxt);
        locationTxt = findViewById(R.id.locationTxt);
        linkTxt = findViewById(R.id.websiteTxt);
        titleTxt = findViewById(R.id.titleTxt);
        fulltimeTxt = findViewById(R.id.fulltimeTxt);
        descriptionTxt = findViewById(R.id.deskripsiTxt);
        actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Job Detail");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}