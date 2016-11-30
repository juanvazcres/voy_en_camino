package mx.com.neus.juanitovision;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import mx.com.neus.juanitovision.vo.Punto;
import mx.com.neus.juanitovision.vo.PuntosDAO;

public class PuntosConfigurationActivity extends FragmentActivity  implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {
    private AppCompatEditText logitud;
    private AppCompatEditText latitud;
    private AppCompatEditText nombre;
    private AppCompatEditText numero;
    private GoogleMap mMap;
    private PuntosDAO dao;
    private Activity activity= this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntos_configuration);
        //      Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        logitud = (AppCompatEditText) findViewById(R.id.logitud);
        latitud = (AppCompatEditText) findViewById(R.id.latitud);
        nombre = (AppCompatEditText) findViewById(R.id.nombre);
        numero = (AppCompatEditText) findViewById(R.id.numero);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        dao= new PuntosDAO(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click(view);
            }

            private void click(View view) {
                String longitudText = logitud.getText().toString();
                String latitudText = latitud.getText().toString();
                Punto punto = new Punto();
                punto.setLatitud(Double.parseDouble(latitudText));
                punto.setLongitud(Double.parseDouble(longitudText));
                punto.setNombre(nombre.getText().toString());
                punto.setNumero(numero.getText().toString());
                boolean  success = dao.insertPunto(punto);
                if(success){
                    setResult(Activity.RESULT_OK);
                    finishActivity(Constants.AGREGAR_PUNTOS);
                    finish();
                    Snackbar.make(view, "Longitud " + longitudText + " Latitud: " + latitudText, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }else{
                    Snackbar.make(view, "ERROR AGREGANDO NUEVO PUNTO", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(19.173773, -96.134224);
        LatLngBounds CentroFox = new LatLngBounds(new LatLng(20.978241,-101.695856), new LatLng(20.978241,-101.695856));
        LatLngBounds VERACRUZ = new LatLngBounds(
                new LatLng(19.2027248,-96.1642877), new LatLng(19.2027248,-96.1642877));
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(VERACRUZ.getCenter(), 16));
        mMap.setOnMapLongClickListener(this);
    }


    @Override
    public void onMapLongClick(LatLng point) {
        this.latitud.setText(String.valueOf(point.latitude));
        this.logitud.setText(String.valueOf(point.longitude));
        mMap.clear();
        mMap.addMarker(new MarkerOptions()
                .position(point)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
    }
}
