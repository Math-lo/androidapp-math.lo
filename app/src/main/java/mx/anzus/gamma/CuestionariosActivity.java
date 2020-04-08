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

public class CuestionariosActivity extends AppCompatActivity {

    ListView listViewCuestionarios;
    Intent lastActivity;
    String mode,user,grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionarios);
        try {
            listViewCuestionarios = (ListView) findViewById(R.id.listview_cuestionarios);
            ArrayList bandArray = new ArrayList<String>();
            lastActivity = getIntent();
            mode = lastActivity.getStringExtra("MODE");
            user = lastActivity.getStringExtra("TOKEN");
            grupo = lastActivity.getStringExtra("GRUPO");
            Toast.makeText(this, mode + " " + user, Toast.LENGTH_LONG).show();
            ArrayList cuestionarios = Database.getCuestionariosProf(user);
            System.out.println(cuestionarios);
            for (int i = 0; i < cuestionarios.size(); i++) {
                ArrayList cuestionario = (ArrayList)cuestionarios.get(i);
                bandArray.add(cuestionario.get(0).toString()+" Grupo: "+cuestionario.get(4).toString());
            }
            // Creamos el adaptdor
            ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_cuestionario, R.id.id_textView_cuestionario, bandArray);
            //Asignamos el adaptador a la listView
            listViewCuestionarios.setAdapter(arrayAdapter);
            listViewCuestionarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Get the selected item text from ListView
                    String selectedItem = (String) parent.getItemAtPosition(position);
                    // Display the selected item text on TextView
                    Toast.makeText(CuestionariosActivity.this, selectedItem, Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            System.out.println("Error en CuestionariosActivity: "+e.getMessage());
        }

    }
}
