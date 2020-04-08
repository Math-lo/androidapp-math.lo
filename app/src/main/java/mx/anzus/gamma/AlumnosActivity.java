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

public class AlumnosActivity extends AppCompatActivity {

    ListView listViewGroups;
    Intent lastActivity;
    String mode,user,grupo;
    String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
            "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
            "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
            "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
            "Android", "iPhone", "WindowsMobile" };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnos);
        try {
            listViewGroups = (ListView) findViewById(R.id.listview_alumnos);
            ArrayList bandArray = new ArrayList<String>();
            lastActivity = getIntent();
            mode = lastActivity.getStringExtra("MODE");
            user = lastActivity.getStringExtra("TOKEN");
            grupo = lastActivity.getStringExtra("GRUPO");
            Toast.makeText(this, mode + " " + user, Toast.LENGTH_LONG).show();
            ArrayList alumnos = Database.getAlumnosGroup(grupo,Database.getIdMail(user));
            for (int i = 0; i < alumnos.size(); i++) {
                bandArray.add(alumnos.get(i));
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
                    Toast.makeText(AlumnosActivity.this, selectedItem, Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            System.out.println("Error en AlumnosActivity: "+e.getMessage());
        }
    }
}
