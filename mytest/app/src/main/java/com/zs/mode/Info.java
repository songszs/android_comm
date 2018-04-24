package com.zs.mode;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author:      ZangSong
 * Email:       gnoszsong@gmail.com
 * Date:        17-11-10 下午4:52
 * Description: mytest
 */
public class Info implements Parcelable {

    String name;
    String id;

    public Info(Parcel in) {
        name = in.readString();
        id = in.readString();
    }

    public Info()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static final Creator<Info> CREATOR = new Creator<Info>() {
        @Override
        public Info createFromParcel(Parcel in) {
            return new Info(in);
        }

        @Override
        public Info[] newArray(int size) {
            return new Info[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(id);
    }

    public void readFromParcel(Parcel dest) {
        name = dest.readString();
        id = dest.readString();
    }

}
