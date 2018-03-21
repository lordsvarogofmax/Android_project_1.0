package com.masterdomservis.android_5;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.masterdomservis.android_5.pojo.Declaration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static Intent serviceIntent;
    private MyService myService;
    private ServiceConnection serviceConnection;
    private boolean isServiceBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton settins_button = (ImageButton) findViewById(R.id.settins_button);

        ImageView master = (ImageView) findViewById(R.id.master_button);
        ImageView zayavka = (ImageView) findViewById(R.id.zayavka_button);
        ImageView adress = (ImageView) findViewById(R.id.adress_button);
        ImageView help = (ImageView) findViewById(R.id.help_button);

        settins_button.setOnClickListener(this);

        master.setOnClickListener(this);
        zayavka.setOnClickListener(this);
        adress.setOnClickListener(this);
        help.setOnClickListener(this);

        serviceIntent = new Intent(getApplicationContext(), MyService.class);
        startService(serviceIntent);
        bindService();


        List<Declaration> listing = getDeclaration();

        int i;
//        final Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    while (true) {
//
//                        Thread.sleep(3000);
//                        int count = getCountDeclaration();
//                        if (count > 0) {
//                            TextView main_info = (TextView) findViewById(R.id.main_info);
//                            main_info.setText(String.format("Количество заявок %s", String.valueOf(count)));
//                        }
//                        Thread.sleep(1000 * 60);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        //запустил поток на подчет заявок
//        thread.start();
//
//
//        if(getCountDeclaration() > 0){
//            TextView main_info = (TextView) findViewById(R.id.main_info);
//            main_info.setText(String.format("Количество заявок %s", String.valueOf(getCountDeclaration())));
//        }

    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.settins_button:
                intent = new Intent("android.intent.action.PREF");
                startActivity(intent);
                break;
            case R.id.master_button:
                intent = new Intent("android.intent.action.MASTER");
                startActivity(intent);
                break;
            case R.id.zayavka_button:
                intent = new Intent("android.intent.action.ZAYAVKA");

                List<Declaration> listing = getDeclaration();
                intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) listing);
                intent.getParcelableArrayListExtra(String.valueOf(getDeclaration()));

                startActivity(intent);
                break;
            case R.id.adress_button:
                intent = new Intent("android.intent.action.KARTA");
                startActivity(intent);
                break;
            case R.id.help_button:
                intent = new Intent("android.intent.action.HELP");
                startActivity(intent);
                break;
        }
    }

    private void bindService() {
        if (serviceConnection == null) {
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {

                    MyService.MyServiceBinder myServiceBinder = (MyService.MyServiceBinder) service;
                    myService = myServiceBinder.getService();
                    isServiceBound = true;
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    isServiceBound = false;
                }
            };
        }

        bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    private void unbindService(){
        if(isServiceBound){
            unbindService(serviceConnection);
            isServiceBound = false;
        }
    }

    private int getCountDeclaration(){

        if(isServiceBound){
            CopyOnWriteArrayList<Declaration> list = myService.getDeclaration();
            //Toast.makeText(this, String.valueOf(list.size()), Toast.LENGTH_LONG).show();
            return list.size();
        }
        else {
            return 0;
        }
    }

    private ArrayList<Declaration> getDeclaration(){
        CopyOnWriteArrayList<Declaration> list;
        if(isServiceBound){
            list = myService.getDeclaration();
            return new ArrayList<Declaration>(list);
        }
        else {
            return new ArrayList<Declaration>();
        }
    }

}
