// IBookManager.aidl
package com.king.ipcdemo;

import com.king.ipcdemo.Book;
interface IBookManager {

    List<Book> getBooksList();
    void addBook(in Book book);
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
