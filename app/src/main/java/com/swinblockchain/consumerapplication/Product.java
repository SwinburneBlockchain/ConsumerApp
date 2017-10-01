package com.swinblockchain.consumerapplication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by john on 1/10/17.
 */

public class Product implements Parcelable{

    String productName;
    String productId;
    String batchId;

    public Product(String productName, String productId, String batchId) {
        this.productName = productName;
        this.productId = productId;
        this.batchId = batchId;
    }

    public Product(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {

            return new Product[size];
        }

    };

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.productName);
        parcel.writeString(this.productId);
        parcel.writeString(this.batchId);
    }

    public void readFromParcel(Parcel in) {
        productName = in.readString();
        productId = in.readString();
        batchId = in.readString();
    }
}

