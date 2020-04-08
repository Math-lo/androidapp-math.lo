package mx.anzus.gamma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import mx.anzus.gamma.loadingscreens.LoadingN2Activity;

public class GroupsActivity  extends AppCompatActivity {

    ListView listViewGroups;
    Intent lastActivity;
    String mode;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        try {
            listViewGroups = (ListView) findViewById(R.id.listview_groups);
            ArrayList bandArray = new ArrayList<String>();
            lastActivity = getIntent();
            mode = lastActivity.getStringExtra("MODE");
            user = lastActivity.getStringExtra("TOKEN");
            Toast.makeText(this, mode + " " + user, Toast.LENGTH_LONG).show();
            ArrayList groups = Database.getGroupsProf(user);
            for (int i = 0; i < groups.size(); i++) {
                bandArray.add(groups.get(i).toString());
            }
            // Creamos el adaptdor
            ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_group, R.id.id_textView_group, bandArray);
            //Asignamos el adaptador a la listView
            listViewGroups.setAdapter(arrayAdapter);
            listViewGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Get the selected item text from ListView
                    String selectedItem = (String) parent.getItemAtPosition(position);
                    // Display the selected item text on TextView
                    Toast.makeText(GroupsActivity.this, selectedItem, Toast.LENGTH_SHORT).show();
                    goAlumnos(selectedItem);
                }
            });
        }catch (Exception e){
            System.out.println("Error en GroupsActivity: "+e.getMessage());
        }
    }

    public void goAlumnos(String grupo){
        Intent alumnos = new Intent(GroupsActivity.this, AlumnosActivity.class);
        alumnos.putExtra("TOKEN",user);
        alumnos.putExtra("MODE",mode);
        alumnos.putExtra("GRUPO",grupo);
        startActivity(alumnos);
    }
}
