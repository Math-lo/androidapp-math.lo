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

import mx.anzus.gamma.loadingscreens.LoadingN4Activity;

public class CuestionariosActivityFragment extends Fragment {

    ListView listViewCuestionarios;
    String mode,user,tipo;
    MenuUsuario menuUsuario;

    public CuestionariosActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cuestionarios,container, false);
        try {
            listViewCuestionarios = (ListView) view.findViewById(R.id.listview_cuestionarios);
            ArrayList bandArray = new ArrayList<String>();
            menuUsuario = (MenuUsuario) getActivity();
            ArrayList<String> array= (ArrayList<String>) menuUsuario.getData();
            mode = array.get(1);
            user = array.get(0);
            tipo = array.get(2);
            Toast.makeText((MenuUsuario)getActivity(), mode + " " + user+" "+tipo, Toast.LENGTH_LONG).show();
            System.out.println(user);
            final ArrayList cuestionarios = Database.getCuestionarios(user);
            System.out.println(cuestionarios);
            for (int i = 0; i < cuestionarios.size(); i++) {
                ArrayList cuestionario = (ArrayList)cuestionarios.get(i);
                String[] grupos = cuestionario.get(4).toString().split(",");
                String grups ="";
                for (int x = 0;x < grupos.length;x++){
                    grups += " "+Database.getNomGroup(grupos[x]);
                }
                bandArray.add(cuestionario.get(0).toString()+" Grupo:"+grups);
            }
            // Creamos el adaptdor
            ArrayAdapter arrayAdapter = new ArrayAdapter<String>((MenuUsuario)getActivity(), R.layout.item_cuestionario, R.id.id_textView_cuestionario, bandArray);
            //Asignamos el adaptador a la listView
            listViewCuestionarios.setAdapter(arrayAdapter);
            listViewCuestionarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Get the selected item text from ListView
                    String selectedItem = (String) parent.getItemAtPosition(position);
                    // Display the selected item text on TextView
                    System.out.println(selectedItem);
                    ArrayList cuestionario = (ArrayList) cuestionarios.get(position);
                    System.out.println(cuestionario);
                    goDetalleCuestionario(Database.getCuestId(cuestionario.get(0).toString()));
                    Toast.makeText((MenuUsuario)getActivity(), selectedItem, Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            System.out.println("Error en CuestionariosActivity: "+e.getMessage());
        }
        return view;
    }

    public void goDetalleCuestionario(String iDcuestionario){
        try {
            Intent intent = new Intent((MenuUsuario)getActivity(), LoadingN4Activity.class);
            intent.putExtra("TOKEN", user);
            intent.putExtra("MODE", mode);
            System.out.println(iDcuestionario);
            intent.putExtra("CUEST",iDcuestionario);
            startActivity(intent);
        }catch (Exception e){
            System.out.println("Error en goDetalleCuestionario: "+e.getMessage());
        }
    }
}
