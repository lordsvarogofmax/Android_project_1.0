package com.masterdomservis.android_5;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.masterdomservis.android_5.pojo.Declaration;
import com.masterdomservis.android_5.utility.Utility;

import java.util.List;

public class ItemDeclaration extends AppCompatActivity{

    private Declaration declaration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.declaration_layout);
        declaration = (Declaration) getIntent().getParcelableExtra(Declaration.class.getCanonicalName());

        TextView item_text_declar_1 = (TextView) findViewById(R.id.item_text_declar_1);
        TextView item_text_declar_2 = (TextView) findViewById(R.id.item_text_declar_2);
        TextView item_text_declar_3 = (TextView) findViewById(R.id.item_text_declar_3);
        TextView item_text_declar_4 = (TextView) findViewById(R.id.item_text_declar_4);
        TextView item_text_declar_5 = (TextView) findViewById(R.id.item_text_declar_5);
        TextView item_text_declar_6 = (TextView) findViewById(R.id.item_text_declar_6);
        TextView item_text_declar_7 = (TextView) findViewById(R.id.item_text_declar_7);

        item_text_declar_1.setText(declaration.toString());
        item_text_declar_2.setText(declaration.getDECLARATION());
        item_text_declar_3.setText(declaration.getFIO());
        item_text_declar_4.setText(declaration.getNOTES());
        item_text_declar_5.setText(declaration.getZAPLANIR());
        item_text_declar_6.setText(declaration.getTELEFON());
        //item_text_declar_7.setText(declaration.toString());
    }

    public void getMaps(View view){
        Intent intent = new Intent("android.intent.action.KARTA");
        intent.putExtra("adress", "Dom 4" );
        startActivity(intent);
    }

    public void SetStatus(View view){

        MyTask myTask = new MyTask();
        myTask.execute(String.valueOf(declaration.getDECLID()), "2", "");
    }

    class MyTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            int maxItem = 5;

            String HASH = Utility.read(getApplicationContext(), Utility.HASHDir);
            String DECLID = params[0];
            String SOSTZAKAZID = params[1];

            if(HASH.equals("")){
                return "Нет ключа авторизации";
            }


            try {
                for(int i = 0; i < maxItem; i++){
                    String tempHASH = Utility.setStatusDeclaration(HASH, DECLID, SOSTZAKAZID);


                    if(tempHASH == null || tempHASH.equals("") || tempHASH.equals("\n")){
                        if(i + 1 == maxItem){
                            return "Ошибка подтвержения заказа";
                        }
                    }
                    else if(tempHASH.equals("error") || tempHASH.equals("error\n")){
                        if(i + 1 == maxItem){
                            return "Ошибка подтвержения заказа 1";
                        }
                    }
                    else if(tempHASH.equals("false") || tempHASH.equals("false\n")){
                        if(i + 1 == maxItem){
                            return "Ошибка подтвержения заказа (сервис)";
                        }
                    }
                    else if(tempHASH.equals("true") || tempHASH.equals("true\n")) {
                        return "Заявка исполнена";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Eroor Ошибка Авторизации !!!";
            }
            return "Ошибка Авторизации !!!";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast toast = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }


}
