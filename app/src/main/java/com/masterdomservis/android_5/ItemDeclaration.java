package com.masterdomservis.android_5;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.masterdomservis.android_5.pojo.Declaration;

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
}
