package mx.anzus.gamma.loadingscreens;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import mx.anzus.gamma.Database;
import mx.anzus.gamma.DetalleCuestionarioActivity;
import mx.anzus.gamma.MainActivity;
import mx.anzus.gamma.ProcesosApi;
import mx.anzus.gamma.R;
import mx.anzus.gamma.procesos.cifrado;

public class LoadingN4Activity extends AppCompatActivity {

    String user,idCue;
    Database databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        try {
            Intent intent = getIntent();
            user = intent.getStringExtra("TOKEN");
            idCue = intent.getStringExtra("CUEST");
            System.out.println("IDCUESTIONARIO: "+idCue);
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
                        ProcesosApi.connectApiServiceDetalleCuestionariosAsync(cifrado.decrypt(Database.getToken(user)), user, idCue);
                        sleep(1000 * 8 );
                    } catch (Exception e) {
                        Database.changeFailed(user);
                        System.out.println("Error in LoadingN4Activity: " + e.getMessage());
                        e.printStackTrace();
                    }
                    Intent menu;
                    switch (Database.userType(user)){
                        case 1:
                            menu = new Intent(LoadingN4Activity.this, MainActivity.class);
                            break;
                        case 2:
                            menu = new Intent(LoadingN4Activity.this, DetalleCuestionarioActivity.class);
                            break;
                        case 3:
                            menu = new Intent(LoadingN4Activity.this, DetalleCuestionarioActivity.class);
                            break;
                        case 4:
                            menu = new Intent(LoadingN4Activity.this, DetalleCuestionarioActivity.class);
                            break;
                        case 5:
                            menu = new Intent(LoadingN4Activity.this, DetalleCuestionarioActivity.class);
                            break;
                        default:
                            menu = new Intent(LoadingN4Activity.this, MainActivity.class);
                            break;
                    }
                    if (Database.checkChange(user)) {
                        System.out.println("ONLINE");
                        menu.putExtra("TOKEN",user);
                        menu.putExtra("CUEST",idCue);
                        menu.putExtra("MODE", "ONLINE");
                        startActivity(menu);
                    } else {
                        System.out.println("OFFLINE");
                        menu.putExtra("TOKEN",user);
                        menu.putExtra("CUEST",idCue);
                        menu.putExtra("MODE", "OFFLINE");
                        startActivity(menu);
                    }
                    finish();
                }
            };
            welcomeThread.start();
        } catch (Exception e) {
            System.out.println("Error en LoadingN4Activity: " + e.getMessage());
        }
    }
}