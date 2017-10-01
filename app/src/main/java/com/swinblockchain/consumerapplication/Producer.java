package com.swinblockchain.consumerapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by john on 1/10/17.
 */

public class Producer implements Parcelable{
    String producerName;
    String producerTimestamp;
    String producerLocation;

    public Producer(String producerName, String producerTimestamp, String producerLocation) {
        this.producerName = producerName;
        this.producerTimestamp = producerTimestamp;
        this.producerLocation = producerLocation;
    }

    public Producer(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Producer> CREATOR = new Parcelable.Creator<Producer>() {
        public Producer createFromParcel(Parcel in) {
            return new Producer(in);
        }

        public Producer[] newArray(int size) {

            return new Producer[size];
        }

    };

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getProducerTimestamp() {
        return producerTimestamp;
    }

    public void setProducerTimestamp(String producerTimestamp) {
        this.producerTimestamp = producerTimestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.producerName);
        parcel.writeString(this.producerTimestamp);
        parcel.writeString(this.producerLocation);
    }

    public void readFromParcel(Parcel in) {
       producerName = in.readString();
       producerTimestamp = in.readString();
       producerLocation = in.readString();
    }
}