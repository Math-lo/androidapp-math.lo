package mx.anzus.gamma;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class GroupsActivityFragment extends Fragment {

    ListView listViewGroups;
    String mode,tipo,user;
    MenuUsuario menuUsuario;

    public GroupsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        menuUsuario = (MenuUsuario) getActivity();
        ArrayList<String> array= (ArrayList<String>) menuUsuario.getData();
        View view =inflater.inflate(R.layout.fragment_groups,container, false);
        listViewGroups = (ListView) view.findViewById(R.id.listview_groups);

        ArrayList bandArray = new ArrayList<String>();
        mode = array.get(1);
        user = array.get(0);
        tipo = array.get(2);

        Toast.makeText((MenuUsuario)getActivity(), mode + " " + user+" "+tipo, Toast.LENGTH_LONG).show();
        ArrayList groups = Database.getGroups(user);
        for (int i = 0; i < groups.size(); i++) {
            bandArray.add(groups.get(i).toString());
        }
        // Creamos el adaptdor
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>((MenuUsuario)getActivity(), R.layout.item_group, R.id.id_textView_group, bandArray);
        //Asignamos el adaptador a la listView
        listViewGroups.setAdapter(arrayAdapter);
        listViewGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);
                // Display the selected item text on TextView
                Toast.makeText((MenuUsuario)getActivity(), selectedItem, Toast.LENGTH_SHORT).show();
                goAlumnos(selectedItem);
            }
        });
        return view;
    }

    public void goAlumnos(String grupo){
        Intent alumnos = new Intent((MenuUsuario)getActivity(), AlumnosActivity.class);
        alumnos.putExtra("TOKEN",user);
        alumnos.putExtra("MODE",mode);
        alumnos.putExtra("GRUPO",grupo);
        startActivity(alumnos);
    }

}
