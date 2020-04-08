package mx.anzus.gamma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        try{
            databaseHandler = new Database(this);
            SQLiteDatabase db = databaseHandler.getWritableDatabase();
            databaseHandler.create(db);
        }catch (android.database.sqlite.SQLiteConstraintException e){
            System.out.println("UNIQUE CONSTRAINT");
        }catch(Exception e){
            System.out.println("Error en la BASE DE DATOS: "+e.getMessage());
        }
    }

    public void goMenu(View view){
        String user = editTextUser.getText().toString();
        String pass = editTextPass.getText().toString();
        if(!user.equals("") && !pass.equals("") && user.length()<40 && pass.length()<40) {
                Intent intent = new Intent(this, LoadingN1Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("user", user);
                intent.putExtra("password", pass);
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    System.out.println("Error en goMenu: " + e.getMessage());
            }
        }
    }
}
