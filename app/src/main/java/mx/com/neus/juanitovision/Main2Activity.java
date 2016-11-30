package mx.com.neus.juanitovision;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.telecom.TelecomManager;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //receive phone call

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        PhoneStateListener phoneStateListener = new PhoneStateListener() {

            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                // TODO Auto-generated method stub
                // super.onCallStateChanged(state, incomingNumber);
                String number = incomingNumber;
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    //Toast.makeText(getApplicationContext(), "Phone is Riging",Toast.LENGTH_SHORT).show();
                    SharedPreferences prefer =getSharedPreferences("XD", MODE_PRIVATE);
                    String phone = prefer.getString("UserPhone","");
                    String activo = prefer.getString("Activo","");
                    String lugar = prefer.getString("Lugar","");
                    String message= prefer.getString("UserMessage","") + " Estoy cerca de " + lugar;
                    if(activo.equals("Activo")) {
                        if (number.equals(phone)) {
                            sendSMS(phone, message);
                        } else {
                            sendSMS(number, "Estoy Conduciendo");
                        }
                    }
                }

                if(state == TelephonyManager.CALL_STATE_OFFHOOK){

                    SharedPreferences prefer =getSharedPreferences("XD", MODE_PRIVATE);

                    String phone = prefer.getString("UserPhone","");
                    String activo = prefer.getString("Activo","");
                    String lugar = prefer.getString("Lugar","");
                    String message= prefer.getString("UserMessage","") + " Estoy cerca de " + lugar;
                    if(activo.equals("Activo")) {
                        if (number.equals(phone)) {
                            sendSMS(phone, message);
                        } else {
                            sendSMS(number, "Estoy Conduciendo");
                        }
                    }

                    Toast.makeText(getApplicationContext(), "Phone is Currenty in A call",
                            Toast.LENGTH_SHORT).show();
                }

                if(state == TelephonyManager.CALL_STATE_IDLE){
                    //Toast.makeText(getApplicationContext(), "Phone is neither Riging nor in a Call",
                      //      Toast.LENGTH_SHORT).show();
                }
            }

        };
        telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void sendSMS(String phoneNo, String msg){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(getApplicationContext(), "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment juanitogFragmento;
        if (id == R.id.nav_slideshow) {
            juanitogFragmento = new MainActivityFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contenedor, juanitogFragmento).addToBackStack(null).commit();
        } else if (id == R.id.nav_manage) {
            juanitogFragmento = new ListaPuntosFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contenedor, juanitogFragmento).addToBackStack(null).commit();
        } else if (id == R.id.nav_send) {
            juanitogFragmento = new PreferenciasFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contenedor, juanitogFragmento).addToBackStack(null).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
