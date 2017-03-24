package com.king.ipcdemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 16230 on 2017/3/24.
 */

public class Book implements Parcelable{
    public String Name;
    public String Author;

    public Book(String name,String author){
        this.Name = name;
        this.Author = author;
    }
    protected Book(Parcel in) {
        Name = in.readString();
        Author = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(Name);
        dest.writeString(Author);
    }

}
