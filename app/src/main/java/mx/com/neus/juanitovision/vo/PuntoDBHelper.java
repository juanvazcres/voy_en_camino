package mx.com.neus.juanitovision.vo;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by juan on 12/15/15.
 */
public class PuntoDBHelper {

    public static class PuntosContract implements BaseColumns {
        public static final String TABLE_NAME = "puntos";
        public static final String LONGITUD = "longitud";
        public static final String LATITUD = "latitud";
        public static final String NOMBRE = "nombre";
        public static final String NUMERO = "numero";
    }

    private static final String SQL_CREATE_TABLE =
            "create table IF NOT EXISTS "+ PuntosContract.TABLE_NAME+ "("
                    +PuntosContract._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                    +PuntosContract.LATITUD+" REAL NOT NULL DEFAULT 0, "
                    +PuntosContract.LONGITUD+ " REAL NOT NULL DEFAULT 0 ,"
                    +PuntosContract.NOMBRE+ " TEXT NOT NULL DEFAULT ' ' ,"
                    +PuntosContract.NUMERO+ " TEXT NOT NULL DEFAULT ' ');";

    private static final String SQL_DELETE_TABLE=
            "DROP TABLE IF EXISTS " + PuntosContract.TABLE_NAME;

    public static void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase db){
        db.execSQL(SQL_DELETE_TABLE);
    }

    public static void onDowngrade(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_TABLE);
    }

}
