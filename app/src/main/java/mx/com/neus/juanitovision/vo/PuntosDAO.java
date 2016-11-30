package mx.com.neus.juanitovision.vo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juan on 12/15/15.
 */
public class PuntosDAO {
    private static final String COMMA=" , ";
    private SQLiteDatabase database;
    private PuntosDBHelper dbHelper;

    private static final String SELECT_BY_PARAM_QUERY = "";
    private static final String SELECT_BY_ID = "";

    private String[] allColumns={
            PuntoDBHelper.PuntosContract._ID,
            PuntoDBHelper.PuntosContract.LATITUD,
            PuntoDBHelper.PuntosContract.LONGITUD,
            PuntoDBHelper.PuntosContract.NOMBRE,
            PuntoDBHelper.PuntosContract.NUMERO
            };

    public PuntosDAO(Context context){
        dbHelper =  new PuntosDBHelper(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }
    public ArrayList<Punto> getAllPuntos(){
        ArrayList<Punto> listaPuntos = new ArrayList<Punto>();
        open();
        Cursor cursor = database.query(PuntoDBHelper.PuntosContract.TABLE_NAME, allColumns, null, null, null, null, null);
        if (cursor.moveToFirst()){

            while (!cursor.isAfterLast()) {
                Punto punto = cursorToPunto(cursor);
                listaPuntos.add(punto);
                cursor.moveToNext();
            }
        }
        cursor.close();
        dbHelper.close();
        return listaPuntos;
    }

    public boolean insertPunto(Punto punto) {
        open();
        ContentValues values=new ContentValues();

            values.put(PuntoDBHelper.PuntosContract.LATITUD, punto.getLatitud());
            values.put(PuntoDBHelper.PuntosContract.LONGITUD, punto.getLongitud());
            values.put(PuntoDBHelper.PuntosContract.NOMBRE, punto.getNombre());
            values.put(PuntoDBHelper.PuntosContract.NUMERO, punto.getNumero());
            Long res = database.insert(PuntoDBHelper.PuntosContract.TABLE_NAME, null, values);

        dbHelper.close();
        if(res<0){
            Log.d("Insert sencillo", "Todo ERROR");
            return false;
        }else{
            Log.d("Insert sencillo", "Todo OK");
            return true;
        }
    }

    private Punto cursorToPunto(Cursor cursor) {
        Punto punto = new Punto();
        punto.setId(cursor.getInt(0));
        punto.setLatitud(cursor.getDouble(1));
        punto.setLongitud(cursor.getDouble(2));
        punto.setNombre(cursor.getString(3));
        punto.setNumero(cursor.getString(4));
        return punto;
    }

}
