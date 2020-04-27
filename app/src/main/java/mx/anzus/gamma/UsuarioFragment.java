package mx.anzus.gamma;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class UsuarioFragment extends Fragment {

    TextView textView;
    String user,mode,tipo;
    MenuUsuario menuUsuario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuario, container, false);
        try {
            textView = (TextView) view.findViewById(R.id.id_textView_usuario);
            menuUsuario = (MenuUsuario) getActivity();
            ArrayList<String> array = (ArrayList<String>) menuUsuario.getData();
            mode = array.get(1);
            user = array.get(0);
            tipo = array.get(2);
            ArrayList alu = Database.getDataUser(Database.getIdMail(user));
            textView.setText("Correo: " + alu.get(0) + "  Nombre: " + alu.get(1) + " CURP: " + alu.get(2)+" Tipo: "+tipo);
        }catch (Exception e){
            System.out.println("Error in UsuarioFragment: "+e.getMessage());
        }
        return view;
    }
}
