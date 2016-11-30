package mx.com.neus.juanitovision.vo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by juan on 12/15/15.
 */
public class PuntosDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TarjadoRA.db";
    private static final int DATABASE_VERSION = 2;

    public PuntosDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        PuntoDBHelper.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        PuntoDBHelper.onUpgrade(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        PuntoDBHelper.onDowngrade(db);
    }

}