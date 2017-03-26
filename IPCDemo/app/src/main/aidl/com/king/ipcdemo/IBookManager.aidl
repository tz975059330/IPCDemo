// IBookManager.aidl
package com.king.ipcdemo;

import com.king.ipcdemo.Book;
import com.king.ipcdemo.IOnNewBookArrivedListener;
interface IBookManager {

    List<Book> getBooksList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unRegister(IOnNewBookArrivedListener listener);
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
