package com.king.ipcdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Activity";

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager bookManager = IBookManager.Stub.asInterface(service);
            try {
                List<Book> list = bookManager.getBooksList();
                Log.i(TAG, "onServiceConnected: "+list.getClass().getCanonicalName());
                Log.i(TAG, "onServiceConnected: "+list.get(0).Name+" 作者 "+list.get(0).Author);
                Log.i(TAG, "onServiceConnected: "+"添加了一本书");
                Book book = new Book("hahah","dsadasds");
                bookManager.addBook(book);
                list = bookManager.getBooksList();
                for(Book books:list){
                    Log.i(TAG, "onServiceConnected: "+books.Name +"  "+books.Author);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this,BookManagerService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
