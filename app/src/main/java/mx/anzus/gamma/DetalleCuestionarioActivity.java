package mx.anzus.gamma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetalleCuestionarioActivity extends AppCompatActivity {

    Intent lastActivity;
    String mode,user,grupo,cuest;
    TextView textView;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detallecuestionario);
        lastActivity = getIntent();
        mode = lastActivity.getStringExtra("MODE");
        user = lastActivity.getStringExtra("TOKEN");
        grupo = lastActivity.getStringExtra("GRUPO");
        cuest = lastActivity.getStringExtra("CUEST");
        textView = (TextView) findViewById(R.id.id_textview_aprrpr);
        String[] AprRpr = Database.getCuestAprRpr(cuest);
        textView.setText("Aprobados: "+AprRpr[0]+" Reprobados: "+AprRpr[1]);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView_detallecuestionario);
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

    }

    public HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        ArrayList preguntas = Database.getPreguntasCuestionario(cuest);
        for (int i =0;i<preguntas.size();i++){
            ArrayList pregunta = (ArrayList)preguntas.get(i);
            ArrayList detalle = new ArrayList();
            detalle.add("Respuesta: "+pregunta.get(1));
            detalle.add("Opcion A: "+pregunta.get(2));
            detalle.add("Opcion B: "+pregunta.get(3));
            detalle.add("Opcion C: "+pregunta.get(4));
            detalle.add("Opcion D: "+pregunta.get(5));
            detalle.add("Tema: "+pregunta.get(6));
            expandableListDetail.put("Pregunta: "+pregunta.get(0).toString(),detalle);
        }
        return expandableListDetail;
    }

}
