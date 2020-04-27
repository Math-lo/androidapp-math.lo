package mx.anzus.gamma;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import mx.anzus.gamma.loadingscreens.LoadingN1Activity;
import mx.anzus.gamma.procesos.cifrado;

public class SincronizarFragment extends Fragment {

    String user,mode,tipo;
    MenuUsuario menuUsuario;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sincronizar, container, false);
        textView = (TextView) view.findViewById(R.id.id_textview_sincronizando);
        menuUsuario = (MenuUsuario) getActivity();

        ArrayList<String> array= (ArrayList<String>) menuUsuario.getData();
        mode = array.get(1);
        user = array.get(0);
        tipo = array.get(2);

        Thread sincronizar = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    if (!Database.getIdMail(user).equals("")) {
                        Database.makingChange(user);
                    }
                    ProcesosApi.connectApiServiceLogInAsync(user, Database.getPass(user));
                    textView.setText("Actualizando Usuario.");
                    sleep(1000 * 5 );
                    textView.setText("Buscando grupos de "+user);
                    switch (Database.userType(user)){
                        case 1:
                            textView.setText("En proceso.");
                            break;
                        case 2:
                            textView.setText("Tipo de Usuario invalido.");
                            break;
                        case 3:
                            ProcesosApi.connectApiServiceGroupsAluProfAsync(cifrado.decrypt(Database.getToken(user)),user);
                            break;
                        case 4:
                            ProcesosApi.connectApiServiceGroupsAluAsync(cifrado.decrypt(Database.getToken(user)),user);
                            break;
                        case 5:
                            ProcesosApi.connectApiServiceGroupsAluAsync(cifrado.decrypt(Database.getToken(user)),user);
                            break;
                        default:
                            textView.setText("Tipo de Usuario invalido.");
                            break;
                    }
                    menuUsuario.setLoadingGroups(1);
                    sleep(1000 * 6  );
                    textView.setText("Buscando Cuestionarios de "+user);
                    switch (Database.userType(user)){
                        case 1:
                            textView.setText("En proceos");
                            break;
                        case 2:
                            textView.setText("Tipo de Usuario Invalido");
                            break;
                        case 3:
                            ProcesosApi.connectApiServiceCuestionariosProfAsync(cifrado.decrypt(Database.getToken(user)), user);
                            break;
                        case 4:
                            ProcesosApi.connectApiServiceCuestionariosAsync(cifrado.decrypt(Database.getToken(user)), user);
                            break;
                        case 5:
                            ProcesosApi.connectApiServiceCuestionariosAsync(cifrado.decrypt(Database.getToken(user)), user);
                            break;
                        default:
                            textView.setText("Tipo de Usuario invalido.");
                            break;
                    }
                    menuUsuario.setLoadingCuestionarios(1);
                    sleep(1000 * 6  );
                    textView.setText("Sincronizado Completado :)");
                } catch (Exception e) {
                    Database.changeFailed(user);
                    System.out.println("Error in SincronizarFragment: " + e.getMessage());
                    e.printStackTrace();
                }
                  setModeActivity();
            }
        };
        sincronizar.start();
    return view;
    }

    public void setModeActivity(){
        if (Database.checkChange(user)) {
            System.out.println("ONLINE");
            menuUsuario.setMode("ONLINE");
        } else {
            System.out.println("OFFLINE");
            menuUsuario.setMode("OFFLINE");
        }
    }
}
