package com.king.ipcdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by 16230 on 2017/3/24.
 */

public class BookManagerService extends Service{

    private static final String TAG = "BMS";

    private AtomicBoolean mIsServiceDestoryed = new AtomicBoolean(false);

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<Book>();

    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>();

    private Binder mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBooksList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);
        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
          mListenerList.register(listener);
        }

        @Override
        public void unRegister(IOnNewBookArrivedListener listener) throws RemoteException {
           mListenerList.unregister(listener);
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mBookList.add(new Book("Android","king"));
        mBookList.add(new Book("Android","noob"));
        new Thread(new ServiceWorker()).start();
    }

    @Override
    public void onDestroy() {
        mIsServiceDestoryed.set(true);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private void onNewBookArrived(Book book) throws RemoteException{
        mBookList.add(book);
        Log.d(TAG, "onNewBookArrived: 通知listener大小改变:"+mBookList.size());
        //给每个listener通知
        final int N = mListenerList.beginBroadcast();
        for(int i = 0;i<N;i++){

            IOnNewBookArrivedListener listener = mListenerList.getBroadcastItem(i);
            if(listener!=null){
                listener.onNewBookArrvied(book);
            }
        }
        mListenerList.finishBroadcast();


    }

    private class  ServiceWorker implements Runnable{

        @Override
        public void run() {
            while(!mIsServiceDestoryed.get()){
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String name = mBookList.size()+" ";
                String Author = new String("haha");
                Book newBook = new Book(name,Author);
                try {
                    onNewBookArrived(newBook);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
