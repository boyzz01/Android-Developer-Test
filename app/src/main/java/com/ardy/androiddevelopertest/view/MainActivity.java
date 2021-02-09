package com.ardy.androiddevelopertest.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ardy.androiddevelopertest.R;
import com.ardy.androiddevelopertest.Util.PrefManager;
import com.ardy.androiddevelopertest.adapter.AdapterJob;
import com.ardy.androiddevelopertest.api.ApiClient;
import com.ardy.androiddevelopertest.api.RetrofitClient;
import com.ardy.androiddevelopertest.response.JobResponse;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterJob adapterJob;


    LabeledSwitch labeledSwitch;

    TextView searchText;
    ImageView showFilter;
    ConstraintLayout filterView;
    EditText search,location;

    Button applyFilter;

    boolean viewFilter = false;
    boolean fulltime = false;
    boolean isFilter = false;

    List<JobResponse> searchList;

    ActionBar actionBar;
    ProgressDialog progressDialog;

    ApiClient apiClient;
    CompositeDisposable compositeDisposable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        loadJob();

        showFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewFilter){
                    filterView.setVisibility(View.GONE);
                    viewFilter = false;
                }else{
                    filterView.setVisibility(View.VISIBLE);
                    viewFilter = true;
                }

            }
        });

        labeledSwitch.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if (isOn){
                    fulltime = true;
                }else{
                    fulltime = false;
                }
            }
        });

        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFilter = true;
                searchText.setVisibility(View.VISIBLE);
                String query = search.getText().toString();
                String lokasi = location.getText().toString();
                adapterJob.applyFilter(query,lokasi,searchList,fulltime);


            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString();
                String lokasi = location.getText().toString();

                if (isFilter){
                    adapterJob.applyFilter(query,lokasi,searchList,fulltime);

                }else{
                    adapterJob.filter(query,searchList);
                }

            }
        });
    }



    private void loadJob() {

        progressDialog = ProgressDialog.show(MainActivity.this, null, "Harap Tunggu...", true, false);

        compositeDisposable.add(apiClient.getAllJob()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(List<JobResponse> jobResponses) {
        adapterJob = new AdapterJob(MainActivity.this,jobResponses);
                        recyclerView.setAdapter(adapterJob);
                        searchList = new ArrayList<>();
                        searchList.addAll(jobResponses);
                        progressDialog.dismiss();
    }

    private void handleError(Throwable error) {
        Toast.makeText(this, "Error "+error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }



    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    private void init() {

        filterView = findViewById(R.id.filterView);
        showFilter = findViewById(R.id.showFilterBtn);
        searchText = findViewById(R.id.searchTxt);
        recyclerView = findViewById(R.id.jobList);
        applyFilter = findViewById(R.id.applyFilterBtn);
        labeledSwitch = findViewById(R.id.switchButton);
        location = findViewById(R.id.locationET);

        search = findViewById(R.id.searchJob);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        actionBar = getSupportActionBar();
        getSupportActionBar().setTitle("Job List");

        Retrofit retrofit = RetrofitClient.getInstance();
        apiClient = retrofit.create(ApiClient.class);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.logout:
            {
                PrefManager prefManager = new PrefManager("data",this);
                prefManager.setLogin(false);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}