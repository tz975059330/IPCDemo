// IOnNewBookArrivedListener.aidl
package com.king.ipcdemo;
import com.king.ipcdemo.Book;
// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {

    void onNewBookArrvied(in Book newBook);
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
