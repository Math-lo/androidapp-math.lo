package mx.anzus.gamma;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MenuAlumnoActivity extends AppCompatActivity {

    Intent lastActivity;
    String mode;
    String user;
    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menualumno);
        lastActivity = getIntent();
        mode = lastActivity.getStringExtra("MODE");
        user = Database.getToken(lastActivity.getStringExtra("TOKEN"));
        textView1 = (TextView) findViewById(R.id.id_textview1_menualumno);
        textView1.setText(mode+" "+user);
        Toast.makeText(this,mode+" "+user,Toast.LENGTH_LONG).show();
    }


}
