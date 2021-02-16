package com.example.libeery;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libeery.adapters.SecondAdapter;
import com.example.libeery.model.BeerRoom;
import com.example.libeery.viewModel.BeersViewModel;
import com.example.libeery.viewModel.BeersViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    private SecondAdapter adapter;
    private BeersViewModel viewModel;
    private List<BeerRoom> beers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search_beer);

     //   String apiId = getIntent().getExtras().getString("apiId");



        Button button = findViewById(R.id.button);
        button.setOnClickListener(view -> {
            Intent i = new Intent(this, FavoritesActivity.class);
            startActivity(i);
        });


        BeersViewModelFactory factory = BeersViewModelFactory.getInstance();
        BeersViewModelFactory.initFactory(SecondActivity.this.getApplication());
        viewModel = new ViewModelProvider(SecondActivity.this, factory).get(BeersViewModel.class);
        initRecyclerView();
        observeData();
    }



    private void initRecyclerView() {

        RecyclerView recyclerView = findViewById(R.id.recyclerViewSearch);

        RecyclerView.LayoutManager layoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            layoutManager = new GridLayoutManager(SecondActivity.this, 2);
        else
            layoutManager = new LinearLayoutManager(SecondActivity.this);
        adapter = new SecondAdapter(viewModel, beers);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    private void observeData() {
        viewModel.getBeerList().observe(SecondActivity.this, list -> {
            this.beers = new ArrayList<>(list);
            adapter.updateBeers(beers);
            if (list.size() != 0) {

                findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        });
    }
}