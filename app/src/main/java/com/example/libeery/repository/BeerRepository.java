package com.example.libeery.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.libeery.api.ApiService;
import com.example.libeery.api.SecondApi;
import com.example.libeery.db.FavoritesDao;
import com.example.libeery.db.FavoritesDatabase;
import com.example.libeery.model.Beer;
import com.example.libeery.model.BeerRoom;
import com.example.libeery.model.Beers;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BeerRepository {

    private final FavoritesDao favoritesDao;
    private final SecondApi secondApi;
    private LiveData<List<BeerRoom>> listBeersRoom;
    private final LiveData<List<BeerRoom>> favoriteBeersRoom;
    private final MutableLiveData<Beers> beerListResponse = new MutableLiveData<>();

    public BeerRepository(Application application) {
        FavoritesDatabase db = FavoritesDatabase.getDatabase(application);
        favoritesDao = db.beerDAO();
        secondApi = ApiService.createService(SecondApi.class);
        favoriteBeersRoom = favoritesDao.getFavoriteBeers();
        listBeersRoom = favoritesDao.getAlphabetizedBeers();
        getBeers();
    }

    public LiveData<List<BeerRoom>> getListBeersRoom() {
        return listBeersRoom;
    }

    public LiveData<List<BeerRoom>> getFavoriteBeersRoom() {
        return favoriteBeersRoom;
    }

    public void insert(BeerRoom beer) {
        FavoritesDatabase.databaseWriteExecutor.execute(() -> {
            favoritesDao.update(beer);
        });
    }

    public void delete(BeerRoom beer) {
        FavoritesDatabase.databaseWriteExecutor.execute(() -> {
            favoritesDao.update(beer);
        });
    }

    public void getBeers(){
        secondApi.getBeers("apps/fast-english/beers.json").enqueue(new Callback<Beers>() {
            @Override
            public void onResponse(@NotNull Call<Beers> call, @NotNull Response<Beers> response) {
                if (response.isSuccessful()) {
                    beerListResponse.setValue(response.body());
                    BeerAPItoBeerRoom(beerListResponse.getValue());
                    listBeersRoom = favoritesDao.getAlphabetizedBeers();
                }
            }

            @Override
            public void onFailure(@NotNull Call<Beers> call, @NotNull Throwable t) { beerListResponse.setValue(null); }
        });
    }

    public void BeerAPItoBeerRoom(Beers beers) {
        List<BeerRoom> dbBeers = new ArrayList<>();
        for(Beer beer : beers.getBeers())
            dbBeers.add(new BeerRoom(beer));
        FavoritesDatabase.databaseWriteExecutor.execute(() -> {
            favoritesDao.insertAll(dbBeers);
        });
    }
}
