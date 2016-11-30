package mx.com.neus.juanitovision;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import mx.com.neus.juanitovision.vo.Punto;
import mx.com.neus.juanitovision.vo.PuntosDAO;

public class ListaPuntosFragment extends Fragment {
    private  PuntosDAO dao;
    private Context context = getActivity();
    private ListView listView;
    private ArrayList<Punto> puntos;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context=activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View fragmentView = inflater.inflate(R.layout.activity_lista_puntos,container,false);
        dao = new PuntosDAO(context);
        listView = (ListView) fragmentView.findViewById(R.id.lista);
        puntos = dao.getAllPuntos();
        ArrayAdapter<Punto> adaptador = new ArrayAdapter<Punto>(context, android.R.layout.simple_list_item_1, puntos);
        listView.setAdapter(adaptador);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(context, "Poision "+position, Toast.LENGTH_SHORT).show();
                SharedPreferences prefer = context.getSharedPreferences("XD", context.MODE_PRIVATE);
                String activo = prefer.getString("Activo","");
                Punto punto = puntos.get(position);
                if(activo.equals("Activo")){
                    SharedPreferences.Editor prefEditor = prefer.edit();
                    prefEditor.putString("UserPhone", " ");
                    prefEditor.putString("Lugar", " ");
                    prefEditor.putString("Activo","Inactivo");
                    prefEditor.commit();
                }
                else{
                    SharedPreferences.Editor prefEditor = prefer.edit();
                    prefEditor.putString("UserPhone", punto.getNumero().toString());
                    prefEditor.putString("Lugar", punto.getNombre().toString());
                    prefEditor.putString("Activo","Activo");
                    prefEditor.commit();
                }
                activo = prefer.getString("Activo","");
                Snackbar.make(view, "Destino Seleccionado "+activo, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, "Poision "+position, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                return false;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) fragmentView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(view.getContext(),PuntosConfigurationActivity.class);
                startActivityForResult(intent, Constants.AGREGAR_PUNTOS);
            }
        });
        return fragmentView;
    }

    private void setAdapter(){
        puntos = dao.getAllPuntos();
        ArrayAdapter<Punto> adaptador = new ArrayAdapter<Punto>(context, android.R.layout.simple_list_item_1, puntos);
        listView.setAdapter(adaptador);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode== Activity.RESULT_OK){
            switch (requestCode){
                case Constants.AGREGAR_PUNTOS:
                    setAdapter();
                    break;
                    default:
                        Log.d("Default",resultCode+"");
            }
        }
    }
}
