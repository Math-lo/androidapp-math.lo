package mx.anzus.gamma.loadingscreens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import mx.anzus.gamma.CuestionariosActivityFragment;
import mx.anzus.gamma.Database;
import mx.anzus.gamma.MenuPrAuAdActivity;
import mx.anzus.gamma.MenuUsuario;
import mx.anzus.gamma.ProcesosApi;
import mx.anzus.gamma.R;
import mx.anzus.gamma.procesos.cifrado;

public class LoadingN3Fragment extends Fragment {

    String user,tipo,mode;
    Database databaseHandler;
    TextView textView;
    MenuUsuario menuUsuario;

    public LoadingN3Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_loadingscreen,container, false);
        menuUsuario = (MenuUsuario) getActivity();
        ArrayList<String> array= (ArrayList<String>) menuUsuario.getData();
        textView = (TextView) view.findViewById(R.id.id_textview_loading);
        mode = array.get(1);
        user = array.get(0);
        tipo = array.get(2);
        if(menuUsuario.getLoadingCuestionarios() == 1){
            System.out.println("Ya se ejecuto.");
            FragmentTransaction ft = null;
            try{
                ft = getFragmentManager().beginTransaction();
            }catch (Exception e){
                System.out.println("Error in set FragmentManager: "+e.getMessage());
            }
            if(ft == null){
                textView.setText("Recargando");
            }else{
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                CuestionariosActivityFragment fragment = new CuestionariosActivityFragment();
                ft.add(R.id.flContent_menu, fragment, "Cuestionarios");
                setModeActivity();
                switch (Database.userType(user)){
                    case 1:
                        ft.commit();
                        break;
                    case 2:
                        textView.setText("Tipo de Usuario invalido.");
                        break;
                    case 3:
                        ft.commit();
                        break;
                    case 4:
                        ft.commit();
                        break;
                    case 5:
                        ft.commit();
                        break;
                    default:
                        textView.setText("Tipo de Usuario invalido.");
                        break;
                }
            }
        }else{
            try {
                menuUsuario.setLoadingCuestionarios(1);
                try {
                    textView.setText("Buscando cuestionarios de "+user);
                    databaseHandler = new Database((MenuUsuario)getActivity());
                } catch (Exception e) {
                    System.out.println("Error en la BASE DE DATOS: " + e.getMessage());
                }
                Thread welcomeThread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            super.run();
                            if (!Database.getIdMail(user).equals("")) {
                                Database.makingChange(user);
                            }
                            switch (Database.userType(user)){
                                case 1:
                                    textView.setText("En proceso.");
                                    //TODO METODO PARA BUSCAR CUESTIONARIOS DE ALUMNO
                                    break;
                                case 2:
                                    textView.setText("Tipo de Usuario invalido.");
                                    break;
                                case 3:
                                    ProcesosApi.connectApiServiceGroupsAluProfAsync(cifrado.decrypt(Database.getToken(user)),user);
                                    sleep(1000 * 4  );
                                    ProcesosApi.connectApiServiceCuestionariosProfAsync(cifrado.decrypt(Database.getToken(user)), user);
                                    break;
                                case 4:
                                    ProcesosApi.connectApiServiceGroupsAluAsync(cifrado.decrypt(Database.getToken(user)),user);
                                    sleep(1000 * 4  );
                                    ProcesosApi.connectApiServiceCuestionariosAsync(cifrado.decrypt(Database.getToken(user)), user);
                                    break;
                                case 5:
                                    ProcesosApi.connectApiServiceGroupsAluAsync(cifrado.decrypt(Database.getToken(user)),user);
                                    sleep(1000 * 4  );
                                    ProcesosApi.connectApiServiceCuestionariosAsync(cifrado.decrypt(Database.getToken(user)), user);
                                    break;
                                default:
                                    textView.setText("Tipo de Usuario invalido.");
                                    break;
                            }
                            sleep(1000 * 4  );
                        } catch (Exception e) {
                            Database.changeFailed(user);
                            System.out.println("Error in LoadingN3Fragment: " + e.getMessage());
                            e.printStackTrace();
                        }
                        FragmentTransaction ft = null;
                        try{
                            ft = getFragmentManager().beginTransaction();
                        }catch (Exception e){
                            System.out.println("Error in set FragmentManager: "+e.getMessage());
                        }
                        if(ft == null){
                            textView.setText("Recargando");
                        }else{
                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            CuestionariosActivityFragment fragment = new CuestionariosActivityFragment();
                            ft.add(R.id.flContent_menu, fragment, "Cuestionarios");
                            setModeActivity();
                            switch (Database.userType(user)){
                                case 1:
                                    ft.commit();
                                    break;
                                case 2:
                                    textView.setText("Tipo de Usuario invalido.");
                                    break;
                                case 3:
                                    ft.commit();
                                    break;
                                case 4:
                                    ft.commit();
                                    break;
                                case 5:
                                    ft.commit();
                                    break;
                                default:
                                    textView.setText("Tipo de Usuario invalido.");
                                    break;
                            }
                        }
                    }
                };
                welcomeThread.start();
            } catch (Exception e) {
                System.out.println("Error en LoadingN3Activity: " + e.getMessage());
            }
        }
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
