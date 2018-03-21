package com.masterdomservis.android_5.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.masterdomservis.android_5.R;
import com.masterdomservis.android_5.pojo.Declaration;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DeclarationAdapter extends BaseAdapter{

    private List<Declaration> list;
    private LayoutInflater layoutInflater;

    public DeclarationAdapter(Context context, CopyOnWriteArrayList<Declaration> list) {
        this.list = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if(view == null){
            view = layoutInflater.inflate(R.layout.item_layout, parent, false);
        }

        Declaration declarationModel = getDeclaration(position);

        TextView textView = (TextView) view.findViewById(R.id.item_layout_text_view);
        textView.setText(String.format("Заявка №%s",String.valueOf(declarationModel.getDECLID())));

        return view;
    }

    private Declaration getDeclaration(int position){
        return (Declaration)getItem(position);
    }
}
