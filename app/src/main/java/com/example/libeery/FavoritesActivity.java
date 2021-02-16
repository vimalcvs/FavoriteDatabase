package com.example.libeery;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libeery.adapters.FavoritesAdapter;
import com.example.libeery.model.BeerRoom;
import com.example.libeery.view.SwipeToDeleteCallBack;
import com.example.libeery.viewModel.BeersViewModel;
import com.example.libeery.viewModel.BeersViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    private FavoritesAdapter adapter;
    private List<BeerRoom> favoriteList =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_favorites);


        BeersViewModelFactory factory = BeersViewModelFactory.getInstance();
        BeersViewModel viewModel = new ViewModelProvider(FavoritesActivity.this, factory).get(BeersViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewFavorite);
        RecyclerView.LayoutManager layoutManager;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            layoutManager = new GridLayoutManager(FavoritesActivity.this,2 );
        else
            layoutManager = new LinearLayoutManager(FavoritesActivity.this);

        adapter = new FavoritesAdapter(viewModel, favoriteList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallBack(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        viewModel.getFavoriteList().observe(FavoritesActivity.this, list -> {
            favoriteList = list;
            adapter.updateFavorite(favoriteList);
        });
    }
}
