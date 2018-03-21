        package com.masterdomservis.android_5;
        import android.app.ListActivity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ListView;
        import com.masterdomservis.android_5.adapter.DeclarationAdapter;
        import com.masterdomservis.android_5.pojo.Declaration;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.concurrent.CopyOnWriteArrayList;

        public class ZayavkaActivity extends ListActivity implements AdapterView.OnItemClickListener {

            private ListView listView;
            private static List<Declaration> listing;
            @Override
            protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_zayavka);

                listing =  getIntent().getParcelableArrayListExtra("data");
                DeclarationAdapter adapter = new DeclarationAdapter(this, new CopyOnWriteArrayList<Declaration>(listing));
                setListAdapter(adapter);
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // PASS
            }

            @Override
            protected void onListItemClick(ListView l, View v, int position, long id) {
                super.onListItemClick(l, v, position, id);

                if(listing != null && listing.size() > 0){
                    Intent intent = new Intent("android.intent.action.ITEMDECLARATION");
                    intent.putExtra(Declaration.class.getCanonicalName(), listing.get(position));
                    startActivity(intent);
                }
            }
        }

