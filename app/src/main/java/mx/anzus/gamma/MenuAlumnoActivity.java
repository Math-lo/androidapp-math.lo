package mx.anzus.gamma;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import mx.anzus.gamma.loadingscreens.LoadingN2Fragment;
import mx.anzus.gamma.loadingscreens.LoadingN3Fragment;

public class MenuAlumnoActivity extends AppCompatActivity {

    Intent lastActivity;
    String mode;
    String user;
    TextView textView1;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    // Make sure to be using androidx.appcompat.app.ActionBarDrawerToggle version.
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menualumno);
        try {
            lastActivity = getIntent();
            mode = lastActivity.getStringExtra("MODE");
            user = Database.getToken(lastActivity.getStringExtra("TOKEN"));
            textView1 = (TextView) findViewById(R.id.id_textview1_menualumno);
            textView1.setText(mode + " " + user);
            Toast.makeText(this, mode + " " + user, Toast.LENGTH_LONG).show();

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            // This will display an Up icon (<-), we will replace it with hamburger later
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // Find our drawer view
            mDrawer = (DrawerLayout) findViewById(R.id.activity_menualumno);
            nvDrawer = (NavigationView) findViewById(R.id.nv_alumno);
            drawerToggle = setupDrawerToggle();
            // Setup toggle to display hamburger icon with nice animation
            drawerToggle.setDrawerIndicatorEnabled(true);
            drawerToggle.syncState();
            // Tie DrawerLayout events to the ActionBarToggle
            mDrawer.addDrawerListener(drawerToggle);
            // Setup drawer view
            setupDrawerContent(nvDrawer);
        }catch (Exception e){
            System.out.println("Error en MenuAlumnoActivity: "+e.getMessage());
        }
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE 1: Make sure to override the method with only a single `Bundle` argument
    // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method.
    // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }else {
            switch (item.getItemId()) {
                case android.R.id.home:
                    mDrawer.openDrawer(GravityCompat.START);
                    return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        if(menuItem.getItemId() ==  R.id.cerrarsesion_nav_paa){
            cerrarSesion();
        }else {
            Fragment fragment = null;
            Class fragmentClass;
            switch (menuItem.getItemId()) {
                case R.id.cuestionarios_nav:
                    fragmentClass = LoadingN3Fragment.class;
                    break;
                case R.id.sincronizar_nav:
                    fragmentClass = SincronizarFragment.class;
                    break;
                case R.id.usuario_nav:
                    fragmentClass = UsuarioFragment.class;
                    break;
                default:
                    fragmentClass = CuestionariosActivityFragment.class;
            }
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent_alumno, fragment).commit();
            // Highlight the selected item has been done by NavigationView
            menuItem.setChecked(true);
            // Set action bar title
            setTitle(menuItem.getTitle());
            // Close the navigation drawer
            mDrawer.closeDrawers();
        }
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    public void cerrarSesion(){
        try{
            Database.cerrarSesion(user);
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }catch (Exception e){
            System.out.println("Error en cerrarSesion: "+e.getMessage());
        }
    }

    public void goUsuario(String clase){

    }

    public void goCuestionarios(){

    }

    public void Sync(){

    }

    public void logout(){

    }

}
