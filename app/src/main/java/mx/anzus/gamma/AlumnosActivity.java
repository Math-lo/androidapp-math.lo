package mx.anzus.gamma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlumnosActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    Intent lastActivity;
    String mode,user,grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnos);
        try {
            lastActivity = getIntent();
            mode = lastActivity.getStringExtra("MODE");
            user = lastActivity.getStringExtra("TOKEN");
            grupo = lastActivity.getStringExtra("GRUPO");
            Toast.makeText(this, mode + " " + user, Toast.LENGTH_LONG).show();

            expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
            expandableListDetail = getData();
            expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
            expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
            expandableListView.setAdapter(expandableListAdapter);
            expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    Toast.makeText(getApplicationContext(),
                            expandableListTitle.get(groupPosition) + " List Expanded.",
                            Toast.LENGTH_SHORT).show();
                }
            });

            expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

                @Override
                public void onGroupCollapse(int groupPosition) {
                    Toast.makeText(getApplicationContext(),
                            expandableListTitle.get(groupPosition) + " List Collapsed.",
                            Toast.LENGTH_SHORT).show();

                }
            });

            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
                    Toast.makeText(
                            getApplicationContext(),
                            expandableListTitle.get(groupPosition)
                                    + " -> "
                                    + expandableListDetail.get(
                                    expandableListTitle.get(groupPosition)).get(
                                    childPosition), Toast.LENGTH_SHORT
                    ).show();
                    return false;
                }
            });
        }catch (Exception e){
            System.out.println("Error en AlumnosActivity: "+e.getMessage());
        }
    }


    public HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        ArrayList alumnos = Database.getAlumnosGroup(grupo,Database.getIdMail(user));
        for (int i =0;i<alumnos.size();i++){
            ArrayList alumno = (ArrayList)alumnos.get(i);
            ArrayList detalle = new ArrayList();
            detalle.add("Correo: "+alumno.get(0));
            detalle.add("CURP: "+alumno.get(2));
            expandableListDetail.put(alumno.get(1).toString(),detalle);
        }
        return expandableListDetail;
    }
}
