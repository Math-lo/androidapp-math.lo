package mx.anzus.gamma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import mx.anzus.gamma.loadingscreens.LoadingN1Activity;

public class MainActivity extends AppCompatActivity {

    EditText editTextUser;
    EditText editTextPass;
    Database databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextUser = (EditText) findViewById(R.id.edit_text_user);
        editTextPass = (EditText) findViewById(R.id.edit_text_pass);
        try {
            deleteCache(this);
            try{
                databaseHandler = new Database(this);
                SQLiteDatabase db = databaseHandler.getWritableDatabase();
                }catch (android.database.sqlite.SQLiteConstraintException e){
                System.out.println("UNIQUE CONSTRAINT");
            }catch(Exception e){
                System.out.println("Error en la BASE DE DATOS: "+e.getMessage());
            }
            System.out.println("PIC");
            previoInicioSesion();
            /*Calendar thatDay = Calendar.getInstance();
            thatDay.set(Calendar.DAY_OF_MONTH,20);
            thatDay.set(Calendar.MONTH,3); // 0-11 so 1 less
            thatDay.set(Calendar.YEAR, 2020);
            Calendar today = Calendar.getInstance();
            long diff = today.getTimeInMillis() - thatDay.getTimeInMillis(); //result in millis
            System.out.println(diff);
            System.out.println("TODAY");
            System.out.println(today);
            System.out.println(diff/(86400*1000));*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void goMenu(View view){
        String user = editTextUser.getText().toString();
        String pass = editTextPass.getText().toString();
        if(!user.equals("") && !pass.equals("") && user.length()<40 && pass.length()<40) {
                Intent intent = new Intent(this, LoadingN1Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("user", user.trim());
                intent.putExtra("password", pass.trim());
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    System.out.println("Error en goMenu: " + e.getMessage());
            }
        }
    }

    public void previoInicioSesion(){
        Calendar today = Calendar.getInstance();
        int usuario = Database.getIdUserLastUsedApp(today.getTimeInMillis());
        System.out.println(usuario);
        if(usuario!=0){
            String correo = String.valueOf(Database.getDataUser(String.valueOf(usuario)).get(0));
            long daysOut = (today.getTimeInMillis()-Database.getDate(correo))/(60*60*24*1000);
            Intent intent = new Intent(this, LoadingN1Activity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("sync",String.valueOf(daysOut));
            intent.putExtra("user", correo);
            intent.putExtra("password", Database.getPass(String.valueOf(Database.getDataUser(String.valueOf(usuario)).get(0))));
            finish();
            try {
                startActivity(intent);
            } catch (Exception e) {
                System.out.println("Error en goMenu: " + e.getMessage());
            }
        }
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

}
