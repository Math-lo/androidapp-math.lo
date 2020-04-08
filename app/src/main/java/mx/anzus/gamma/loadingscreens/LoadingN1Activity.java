package mx.anzus.gamma.loadingscreens;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import mx.anzus.gamma.Database;
import mx.anzus.gamma.MainActivity;
import mx.anzus.gamma.MenuAlumnoActivity;
import mx.anzus.gamma.MenuPrAuAdActivity;
import mx.anzus.gamma.ProcesosApi;
import mx.anzus.gamma.R;

public class LoadingN1Activity extends AppCompatActivity {

    String user;
    String pass;

    Database databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        try {
            Intent intent = getIntent();
            user = intent.getStringExtra("user");
            pass = intent.getStringExtra("password");
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
                        sleep(1000 * 5 );
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
                                menu = new Intent(LoadingN1Activity.this, MenuAlumnoActivity.class);
                                break;
                            case 2:
                                menu = new Intent(LoadingN1Activity.this, MenuPrAuAdActivity.class);
                                break;
                            case 3:
                                menu = new Intent(LoadingN1Activity.this, MenuPrAuAdActivity.class);
                                break;
                            case 4:
                                menu = new Intent(LoadingN1Activity.this, MenuPrAuAdActivity.class);
                                break;
                            case 5:
                                menu = new Intent(LoadingN1Activity.this, MenuPrAuAdActivity.class);
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
            welcomeThread.start();
        } catch (Exception e) {
            System.out.println("Error en LoadingN1Activity: " + e.getMessage());
        }
    }
}
