package com.example.libeery.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.libeery.model.BeerRoom;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {BeerRoom.class}, version = 1, exportSchema = false)
public abstract class FavoritesDatabase extends RoomDatabase {

    public abstract FavoritesDao beerDAO();

    private static volatile FavoritesDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static FavoritesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FavoritesDatabase.class) {
                if (INSTANCE == null)
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FavoritesDatabase.class, "beer_database").build();
            }
        }
        return INSTANCE;
    }
}
