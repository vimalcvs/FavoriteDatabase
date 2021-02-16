package com.example.libeery.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class Beer implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("hindi")
    @Expose
    private String name;

    @SerializedName("english")
    @Expose
    private final String abv;

    private final boolean isFavorite;

    protected Beer(Parcel in) {
        id = in.readString();
        name = in.readString();
        abv = in.readString();
        isFavorite = in.readByte() != 0;
    }

    public static final Creator<Beer> CREATOR = new Creator<Beer>() {
        @Override
        public Beer createFromParcel(Parcel in) {
            return new Beer(in);
        }

        @Override
        public Beer[] newArray(int size) {
            return new Beer[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbv() {
        return abv;
    }

    @NotNull
    @Override
    public String toString() {
        return "Beer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", abv='" + abv + '\'' +
                '}';
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(abv);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
    }



}