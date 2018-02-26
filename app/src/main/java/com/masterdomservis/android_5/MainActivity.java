package com.masterdomservis.android_5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

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

    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()){
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
                startActivity(intent);
                break;
            case R.id.adress_button:
                 intent = new Intent("android.intent.action.ADRESS");
                 startActivity(intent);
                 break;
            case R.id.help_button:
                 intent = new Intent("android.intent.action.HELP");
                 startActivity(intent);
                 break;}
        }


    }
