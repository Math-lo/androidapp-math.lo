package mx.anzus.gamma.loadingscreens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import mx.anzus.gamma.Database;
import mx.anzus.gamma.GroupsActivityFragment;
import mx.anzus.gamma.MenuPrAuAdActivity;
import mx.anzus.gamma.MenuUsuario;
import mx.anzus.gamma.ProcesosApi;
import mx.anzus.gamma.R;
import mx.anzus.gamma.procesos.cifrado;

public class LoadingN2Fragment extends Fragment {

    String user,tipo,mode;
    Database databaseHandler;
    TextView textView;
    MenuUsuario menuUsuario;

    public LoadingN2Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_loadingscreen,container, false);

        menuUsuario = (MenuUsuario) getActivity();
        textView = (TextView) view.findViewById(R.id.id_textview_loading);

        ArrayList<String> array= (ArrayList<String>) menuUsuario.getData();
        mode = array.get(1);
        user = array.get(0);
        tipo = array.get(1);

        if(menuUsuario.getLoadingGroups() == 1){
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
                GroupsActivityFragment fragment = new GroupsActivityFragment();
                ft.add(R.id.flContent_menu, fragment, "Groups");
                setModeActivity();
                switch (Database.userType(user)){
                    case 1:
                        textView.setText("Tipo de Usuario invalido.");
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
                menuUsuario.setLoadingGroups(1);
                try {
                    textView.setText("Buscando grupos de "+user);
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
                                    textView.setText("Tipo de Usuario invalido.");
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
                            sleep(1000 * 7  );
                        } catch (Exception e) {
                            Database.changeFailed(user);
                            System.out.println("Error in LoadingN2Fragment: " + e.getMessage());
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
                            GroupsActivityFragment fragment = new GroupsActivityFragment();
                            ft.add(R.id.flContent_menu, fragment, "Groups");
                            setModeActivity();
                            switch (Database.userType(user)){
                                case 1:
                                    textView.setText("Tipo de Usuario invalido.");
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
                System.out.println("Error en LoadingN2Activity: " + e.getMessage());
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
