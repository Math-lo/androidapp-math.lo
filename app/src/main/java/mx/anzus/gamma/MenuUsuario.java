package mx.anzus.gamma;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import mx.anzus.gamma.loadingscreens.LoadingN2Fragment;
import mx.anzus.gamma.loadingscreens.LoadingN3Fragment;

public class MenuUsuario extends AppCompatActivity  {

    Intent lastActivity;
    String mode,user;
    TextView textView;
    int tipoUser;

    public int loadingGroups = 0;
    public int loadingCuestionarios = 0;

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    // Make sure to be using androidx.appcompat.app.ActionBarDrawerToggle version.
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuusuario);
        try {
            textView = (TextView) findViewById(R.id.id_textView_menu);
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            mDrawer = (DrawerLayout) findViewById(R.id.activity_menu);
            nvDrawer = (NavigationView) findViewById(R.id.nv_menu);

            lastActivity = getIntent();
            mode = lastActivity.getStringExtra("MODE");
            user = lastActivity.getStringExtra("TOKEN");

            tipoUser = Database.getTipoMail(user);
            System.out.println(mode + " " + user+" "+tipoUser);
            textView.setText(mode + " " + user+" "+tipoUser);
            Toast.makeText(this, mode + " " + user+" "+tipoUser, Toast.LENGTH_LONG).show();

            setSupportActionBar(toolbar);// This will display an Up icon (<-), we will replace it with hamburger later
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);// Find our drawer view

            /*drawerToggle = setupDrawerToggle();// Setup toggle to display hamburger icon with nice animation
            drawerToggle.setDrawerIndicatorEnabled(true);
            drawerToggle.syncState();// Tie DrawerLayout events to the ActionBarToggle
            mDrawer.addDrawerListener(drawerToggle);*/

            nvDrawer.getMenu().clear();
            switch (tipoUser){
                case 1:
                    nvDrawer.inflateMenu(R.menu.navigation_menualumno);
                    break;
                case 2:
                    nvDrawer.inflateMenu(R.menu.navigation_menualumno);
                    break;
                case 3:
                    nvDrawer.inflateMenu(R.menu.navigation_menuprauad);
                    break;
                case 4:
                    nvDrawer.inflateMenu(R.menu.navigation_menuprauad);
                    break;
                case 5:
                    nvDrawer.inflateMenu(R.menu.navigation_menuprauad);
                    break;
                default:
                    nvDrawer.inflateMenu(R.menu.navigation_menuprauad);
                    break;
            }
            // Setup drawer view
            setupDrawerContent(nvDrawer);

        }catch (Exception e){
            System.out.println("Error en MenuUsuario: "+e.getMessage());
            e.printStackTrace();
        }

    }

    public void selectDrawerItem(MenuItem menuItem) {

        Fragment fragment = null;// Create a new fragment and specify the fragment to show based on nav item clicked
        if(tipoUser == 1) {
            if(menuItem.getItemId() ==  R.id.cerrarsesion_nav){
                cerrarSesion();
            }else {
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
                        fragmentClass = UsuarioFragment.class;
                }
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent_menu, fragment).commit();// Highlight the selected item has been done by NavigationView
                menuItem.setChecked(true);// Set action bar title
                setTitle(menuItem.getTitle());// Close the navigation drawer
                mDrawer.closeDrawers();
        }
        }else{
            if(menuItem.getItemId() ==  R.id.cerrarsesion_nav_paa){
                cerrarSesion();
            }else {
                Class fragmentClass;
                switch (menuItem.getItemId()) {
                    case R.id.grupos_nav_paa:
                        fragmentClass = LoadingN2Fragment.class;
                        break;
                    case R.id.cuestionarios_nav_paa:
                        fragmentClass = LoadingN3Fragment.class;
                        break;
                    case R.id.sincronizar_nav_paa:
                        fragmentClass = SincronizarFragment.class;
                        break;
                    case R.id.usuario_nav_paa:
                        fragmentClass = UsuarioFragment.class;
                        break;
                    default:
                        fragmentClass = UsuarioFragment.class;
                }
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.flContent_menu, fragment).commit();// Highlight the selected item has been done by NavigationView
                menuItem.setChecked(true);// Set action bar title
                setTitle(menuItem.getTitle());// Close the navigation drawer
                mDrawer.closeDrawers();
        }
            // Insert the fragment by replacing any existing fragment

        }
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE 1: Make sure to override the method with only a single `Bundle` argument
    // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method.
    // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.
    /*@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }*/

    /*@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        /*if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }else {*/
            switch (item.getItemId()) {
                case android.R.id.home:
                    mDrawer.openDrawer(GravityCompat.START);
                    return true;
            }
        //}
        return super.onOptionsItemSelected(item);
    }

    /*private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }*/

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

    public ArrayList<String> getData(){
        ArrayList<String> array = new ArrayList<>();
        array.add(user);
        array.add(mode);
        array.add(String.valueOf(tipoUser));
        return array;
    }

    public int getLoadingGroups(){
        return loadingGroups;
    }

    public void setLoadingGroups(int loadingGroups){
        this.loadingGroups = loadingGroups;
    }

    public int getLoadingCuestionarios(){
        return loadingCuestionarios;
    }

    public void setLoadingCuestionarios(int loadingCuestionarios){
        this.loadingCuestionarios = loadingCuestionarios;
    }

    public void setMode(String mode){
        this.mode = mode;
    }

}
