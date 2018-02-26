import android.app.ListActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.Toast;

        import java.util.ArrayList;

public class ZayavkaActivityNew extends ListActivity {
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        ArrayList<Listing> listings = new ArrayList<Listing>();
        listings.add(new Listing("Artem", "10", "London"));
        listings.add(new Listing("Artem1", "11", "London1"));
        listings.add(new Listing("Artem2", "12", "London2"));
        listings.add(new Listing("Artem3", "13", "London3"));
        String[] values = new String[] {
                "Android",
                "iPhone",
                "WindowsMobile",
                "Blackberry",
                "WebOS",
                "Ubuntu",
                "Windows7",
                "Max OS X",
                "Linux",
                "OS/2" };
        ArrayAdapter<Listing> adapter = new ArrayAdapter<Listing>(this,
                android.R.layout.simple_list_item_1, listings);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this, item + " выбран", Toast.LENGTH_LONG).show();
    }
}

class Listing
{
    Listing(String name, String ID, String Adress){
        this.name = name;
        this.ID = ID;
        this.Adress = Adress;
    }
    public String name;
    public String ID;
    public String Adress;

    @Override
    public String toString() {
        return String.format("%s, %s, %s.", name, ID, Adress);
    }
}
