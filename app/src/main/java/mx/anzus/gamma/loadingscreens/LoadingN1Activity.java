package mx.anzus.gamma.loadingscreens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import mx.anzus.gamma.Database;
import mx.anzus.gamma.MainActivity;
import mx.anzus.gamma.MenuAlumnoActivity;
import mx.anzus.gamma.MenuPrAuAdActivity;
import mx.anzus.gamma.MenuUsuario;
import mx.anzus.gamma.ProcesosApi;
import mx.anzus.gamma.R;
import mx.anzus.gamma.procesos.cifrado;

public class LoadingN1Activity extends AppCompatActivity {

    String user,pass,daysOut;
    Thread sync;
    TextView textView;
    Database databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        try {
            Intent intent = getIntent();
            textView = (TextView) findViewById(R.id.id_textview_loadingn1);
            user = intent.getStringExtra("user");
            pass = intent.getStringExtra("password");
            daysOut = intent.getStringExtra("sync");
            try{
            if(daysOut == null){
                daysOut = "0";
                System.out.println("NULL");
            }}catch (Exception e){
                System.out.println("null dayOut");
            }
            System.out.println("DAYSOUT");
            System.out.println(daysOut);
            try {
                databaseHandler = new Database(this);
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
                        ProcesosApi.connectApiServiceLogInAsync(user, pass);
                        if(Integer.parseInt(daysOut)>0){
                            sync.start();
                        }
                        sleep(1000 * 10 );
                    } catch (Exception e) {
                        Database.changeFailed(user);
                        System.out.println("Error in LoadingN1Activity: " + e.getMessage());
                        e.printStackTrace();
                    }
                    Intent main = new Intent(LoadingN1Activity.this, MainActivity.class);
                    if (Database.login(user, pass)) {
                        Intent menu;
                        switch (Database.userType(user)){
                            case 1:
                                menu = new Intent(LoadingN1Activity.this, MenuUsuario.class);
                                break;
                            case 2:
                                menu = new Intent(LoadingN1Activity.this, MenuUsuario.class);
                                break;
                            case 3:
                                menu = new Intent(LoadingN1Activity.this, MenuUsuario.class);
                                break;
                            case 4:
                                menu = new Intent(LoadingN1Activity.this, MenuUsuario.class);
                                break;
                            case 5:
                                menu = new Intent(LoadingN1Activity.this, MenuUsuario.class);
                                break;
                            default:
                                menu = new Intent(LoadingN1Activity.this, MainActivity.class);
                                break;
                        }
                        if (Database.checkChange(user)) {
                            System.out.println("ONLINE");
                            menu.putExtra("TOKEN",user);
                            menu.putExtra("MODE", "ONLINE");
                            startActivity(menu);
                        } else {
                            System.out.println("OFFLINE");
                            menu.putExtra("TOKEN",user);
                            menu.putExtra("MODE", "OFFLINE");
                            startActivity(menu);
                        }
                    } else {
                        startActivity(main);
                    }
                    finish();
                }
            };
            sync = new Thread() {
                @Override
                public void run() {
                    try {
                        super.run();
                        if (!Database.getIdMail(user).equals("")) {
                            Database.makingChange(user);
                        }
                        ProcesosApi.connectApiServiceLogInAsync(user, Database.getPass(user));
                        sleep(1000 * 3 );
                        textView.setText("Buscando grupos de "+user);
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
                        sleep(1000 * 4  );
                        textView.setText("Buscando Cuestionarios de "+user);
                        switch (Database.userType(user)){
                            case 1:
                                textView.setText("Tipo de Usuario invalido.");
                                break;
                            case 2:
                                textView.setText("Tipo de Usuario invalido.");
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
                        sleep(1000 * 3  );
                        textView.setText("Sincronizado Completado :)");
                    } catch (Exception e) {
                        Database.changeFailed(user);
                        System.out.println("Error in SincronizarFragment: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            };
            welcomeThread.start();
        } catch (Exception e) {
            System.out.println("Error en LoadingN1Activity: " + e.getMessage());
        }
    }
}
