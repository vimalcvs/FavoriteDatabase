package com.example.libeery.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@Entity(tableName = "beer_table")
public class BeerRoom implements Parcelable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private final String id;
    @NonNull
    @ColumnInfo(name = "name")
    private final String name;

    @ColumnInfo(name = "favorite")
    private int favorite;

    @ColumnInfo(name = "abv")
    private final String abv;

    public BeerRoom(@NonNull String id, @NonNull String name,  int favorite,  String abv) {
        this.id = id;
        this.name = name;
        this.favorite = favorite;
        this.abv = abv;
    }

    public BeerRoom(@NonNull Beer b) {
        this.id = b.getId();

        this.name = b.getName();

        if (b.getAbv()!=null) this.abv = b.getAbv();
        else this.abv = "/";

        this.favorite = b.isFavorite()?1:0;
    }

    protected BeerRoom(Parcel in) {
        id = Objects.requireNonNull(in.readString());
        name = Objects.requireNonNull(in.readString());

        favorite = in.readInt();
        abv = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(favorite);
        dest.writeString(abv);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BeerRoom> CREATOR = new Creator<BeerRoom>() {
        @Override
        public BeerRoom createFromParcel(Parcel in) {
            return new BeerRoom(in);
        }

        @Override
        public BeerRoom[] newArray(int size) {
            return new BeerRoom[size];
        }
    };

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public int getFavorite() { return favorite; }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getAbv() {
        return abv;
    }

    @NotNull
    @Override
    public String toString() {
        return "BeerRoom{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", abv='" + abv + '\'' +
                ", favorite=" + favorite +
                '}';
    }

}
