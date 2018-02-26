package com.masterdomservis.android_5;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class ZayavkaActivity extends ListActivity implements AdapterView.OnItemLongClickListener {

    private String[] mZayavka = new String[] {"Заявка №1", "Заявка №2", "Заявка №3",
            "Заявка №4", "Заявка №5"};

    private String[] mZayavkaParam = new String[] {"Заявка: №1", "Дата исполнения: 27.12.1990", "Текст заявки: Все сломалось, ничего не работает",
            "Адрес клиента: 3-я ул. Строителей, д. 27, кв. 12", "Контактное лицо: Путин Владимир Владимирович", "Номер телефона: 8 (960) 558 86 75"};

    private ArrayAdapter<String> mZayavkaAdapter, mWeekOfDayAdapter;
    private String mMonth, mDayOfWeek;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        mZayavkaAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, mZayavka);
        mWeekOfDayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, mZayavkaParam);

        setListAdapter(mZayavkaAdapter);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (getListAdapter() == mZayavkaAdapter) {
            mMonth = (String) l.getItemAtPosition(position);
            setListAdapter(mWeekOfDayAdapter);
            mWeekOfDayAdapter.notifyDataSetChanged();
        } else {
            mDayOfWeek = (String) l.getItemAtPosition(position);
            Toast.makeText(getBaseContext(), mMonth + ": " + mDayOfWeek,
                    Toast.LENGTH_LONG).show();
            setListAdapter(mZayavkaAdapter);
            mZayavkaAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();

        mZayavkaAdapter.remove(selectedItem);
        mZayavkaAdapter.notifyDataSetChanged();

        Toast.makeText(getApplicationContext(),
                selectedItem + " исполнена.",
                Toast.LENGTH_SHORT).show();
        return true;
    }
}
