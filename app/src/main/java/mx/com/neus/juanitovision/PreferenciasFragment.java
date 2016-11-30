package mx.com.neus.juanitovision;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PreferenciasFragment extends Fragment {

    private Context context = getActivity();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context=activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View fragmentView = inflater.inflate(R.layout.activity_preferencias,container,false);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        SharedPreferences prefer = context.getSharedPreferences("XD", context.MODE_PRIVATE);
        final AppCompatEditText phoneNumber = (AppCompatEditText) fragmentView.findViewById(R.id.phone_number);
        final AppCompatEditText message = (AppCompatEditText) fragmentView.findViewById(R.id.mensaje);

        message.setText(prefer.getString("UserMessage",""));
        phoneNumber.setText(prefer.getString("UserPhone", ""));

        FloatingActionButton fab = (FloatingActionButton) fragmentView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numero = phoneNumber.getText().toString();
                String mensaje = message.getText().toString();
                salvar(numero, mensaje);

                Snackbar.make(view, "Se ha guardado Exitósamente " + numero, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return fragmentView;
    }

    public void salvar(String number,String mensaje) {
        SharedPreferences settings = context.getSharedPreferences("XD", context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putString("UserMessage", mensaje);
        prefEditor.putString("UserPhone", number);
        prefEditor.putString("Lugar"," ");
        prefEditor.putString("Activo", "Inactivo");
        prefEditor.commit();
    }

}
