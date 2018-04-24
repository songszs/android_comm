package com.zs.test.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.zs.mode.Info;

/**
 * Author:      ZangSong
 * Email:       gnoszsong@gmail.com
 * Date:        17-11-10 下午4:56
 * Description: mytest
 */
public class OnCallInfo implements Parcelable {

    protected OnCallInfo(Parcel in) {
    }

    public static final Creator<OnCallInfo> CREATOR = new Creator<OnCallInfo>() {
        @Override
        public OnCallInfo createFromParcel(Parcel in) {
            return new OnCallInfo(in);
        }

        @Override
        public OnCallInfo[] newArray(int size) {
            return new OnCallInfo[size];
        }
    };

    public OnCallInfo() {

    }

    int onCall(Info info) {
        Log.e("ss",info.getName());
        return 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {}

    public void readFromParcel(Parcel dest) {

    }
}
