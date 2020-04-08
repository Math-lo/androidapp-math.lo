package mx.anzus.gamma;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import mx.anzus.gamma.loadingscreens.LoadingN2Activity;
import mx.anzus.gamma.loadingscreens.LoadingN3Activity;

public class MenuPrAuAdActivity extends AppCompatActivity {

    Intent lastActivity;
    String mode;
    String user;
    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuprauad);
        try {
            lastActivity = getIntent();
            mode = lastActivity.getStringExtra("MODE");
            user = lastActivity.getStringExtra("TOKEN");
            textView1 = (TextView) findViewById(R.id.id_textview1_menuprauad);
            textView1.setText(mode + " " + user);
            Toast.makeText(this, mode + " " + user, Toast.LENGTH_LONG).show();

            //TODO Tiene que aceder antes a grupos para ver cuestionarios a causa de las relaciones
        }catch (Exception e){
            System.out.println("Error en MenuPrAuAdActivity: "+e.getMessage());
        }
    }
    public void goGroups(View view){
        Intent groups = new Intent(MenuPrAuAdActivity.this, LoadingN2Activity.class);
        groups.putExtra("TOKEN",user);
        startActivity(groups);
    }

    public void goCuestionarios(View view){
        Intent groups = new Intent(MenuPrAuAdActivity.this, LoadingN3Activity.class);
        groups.putExtra("TOKEN",user);
        startActivity(groups);
    }

}
