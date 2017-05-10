package ijam.app_wms.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alberto.ramirez on 15/02/2017.
 */
public class AdminSQLite extends SQLiteOpenHelper {

    //Creamos una variable qeu contendra la sentencia de SQL de Creacion de la tabla
    public AdminSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Comando que se ejecuta automaticamente cuando la BD no existe
        String sSQL = "CREATE TABLE OCFG (DocEntry integer primary key, Url TEXT, BD TEXT, Namespace TEXT, Soap_Action TEXT)";
        db.execSQL(sSQL);
        sSQL ="CREATE TABLE OUSR (Id integer primary key, Code TEXT, User TEXT, Pwd TEXT)";
        db.execSQL(sSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Metodo se ejecuta para la versiond e la BD , migrando de la vieja  al a nueva.
        db.execSQL("DROP TABLE IF EXIST OCFG");
        String sSQL = "CREATE TABLE OCFG (DocEntry integer primary key, Url TEXT, BD TEXT, Namespace TEXT, Soap_Action TEXT)";
        db.execSQL(sSQL);
        db.execSQL("DROP TABLE IF EXIST OUSR");
        sSQL ="CREATE TABLE OUSR (Id integer primary key, Code TEXT, User TEXT, Pwd TEXT)";
        db.execSQL(sSQL);

    }

    public boolean Insertar_Configuracion(clsOCFG oConfiguracion) {
        boolean bResp = false;
        long lResp = 0;
        try {
            ContentValues oValores = new ContentValues();
            oValores.put("Url", oConfiguracion.getsURL());
            oValores.put("Namespace", oConfiguracion.getsNAMESPACE());
            oValores.put("Soap_Action", oConfiguracion.getsSOAP_ACTION());
            oValores.put("BD", oConfiguracion.getsBD());
            SQLiteDatabase oDB = getWritableDatabase();
            lResp = oDB.insert("OCFG", null, oValores);

            if (lResp > 0) {
                bResp = true;
            } else {
                bResp = false;
            }
            oDB.close();
        } catch (Exception ex) {
            oConfiguracion.setsMsg_Error(ex.getMessage());
        }
        return bResp;
    }

    public Cursor Recupera_Configuracion() {
        Cursor oCursor= null;
        try {
            SQLiteDatabase oDB = getReadableDatabase();
            String sQuery = ("SELECT DocEntry, Url, Namespace, Soap_Action, BD FROM OCFG  ");
            oCursor = oDB.rawQuery(sQuery, null);
            if (oCursor != null) {
                oCursor.moveToFirst();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return oCursor;
    }

    public boolean Modificar_Configuracion(clsOCFG oConfiguracion) {
        boolean bResp = false;
        int iResp = 0;
        try {
            ContentValues oValores = new ContentValues();
            oValores.put("Url", oConfiguracion.getsURL());
            oValores.put("Namespace", oConfiguracion.getsNAMESPACE());
            oValores.put("Soap_Action", oConfiguracion.getsSOAP_ACTION());
            oValores.put("BD", oConfiguracion.getsBD());
            SQLiteDatabase oDB = getWritableDatabase();
            iResp=   oDB.update("OCFG", oValores, "DocEntry=" + oConfiguracion.getiDocEntry(), null);

            if (iResp > 0) {
                bResp = true;
            } else {
                bResp = false;
            }
            oDB.close();
        } catch (Exception ex) {
            oConfiguracion.setsMsg_Error(ex.getMessage());
        }
        return bResp;
    }

    public boolean Insertar_Usuarios(clsOCFG oConfiguracion) {
        boolean bResp = false;
        long lResp = 0;
        try {
            ContentValues oValores = new ContentValues();
            oValores.put("Code", oConfiguracion.getsCodeUser());
            oValores.put("User", oConfiguracion.getsUsuario());
            oValores.put("Pwd", oConfiguracion.getsPassword());
            SQLiteDatabase oDB = getWritableDatabase();
            lResp = oDB.insert("OUSR",  null, oValores);

            if (lResp > 0) {
                bResp = true;
            } else {
                bResp = false;
            }
            oDB.close();
        } catch (Exception ex) {
            oConfiguracion.setsMsg_Error(ex.getMessage());
        }
        return bResp;
    }

    public Cursor Recupera_Usuario(clsOCFG oConfiguracion) {
        Cursor oCursor= null;
        try {
            SQLiteDatabase oDB = getReadableDatabase();
            String sQuery = ("SELECT MAX(T0.Id) as Id, T0.Code, T0.User, T0.Pwd, T1.Url, T1.BD, T1.Namespace, T1.Soap_Action FROM OUSR T0 CROSS JOIN OCFG T1 ");
            oCursor = oDB.rawQuery(sQuery, null);
            if (oCursor != null) {
                oCursor.moveToFirst();
                oConfiguracion.setsUserId(oCursor.getString(0));
                oConfiguracion.setsCodeUser(oCursor.getString(1));
                oConfiguracion.setsUsuario(oCursor.getString(2));
                oConfiguracion.setsPassword(oCursor.getString(3));
                oConfiguracion.setsURL(oCursor.getString(4));
                oConfiguracion.setsBD(oCursor.getString(5));
                oConfiguracion.setsNAMESPACE(oCursor.getString(6));
                oConfiguracion.setsSOAP_ACTION(oCursor.getString(7));
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return oCursor;
    }

    public void Elimina_Usuario(){
        SQLiteDatabase oDB = getReadableDatabase();
        oDB.execSQL("TRUNCATE table OUSR");
    }




}