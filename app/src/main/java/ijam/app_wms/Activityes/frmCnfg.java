//Datos conexión


package ijam.app_wms.Activityes;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ijam.app_wms.R;
import ijam.app_wms.SQLite.AdminSQLite;
import ijam.app_wms.SQLite.clsOCFG;
import ijam.app_wms.Clases.Global_Functions;

public class frmCnfg extends AppCompatActivity {
    AdminSQLite oAdmin;
    EditText txtUrl = null;
    EditText txtNamespace = null;
    EditText txtDB = null;
    TextView lblEntry = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_cnfg);
        Toolbar oToolbar = (Toolbar) findViewById(R.id.barra);
        setSupportActionBar(oToolbar);
        ActionBar oActionBar = getSupportActionBar(); // Objeto para instanciar el menu simple
        oActionBar.setHomeButtonEnabled(true); // Activa en el toolbar la opcion de regresar
        oActionBar.setDisplayHomeAsUpEnabled(true);
        final Global_Functions oVariablesGlobales = new Global_Functions();
        oAdmin = new AdminSQLite(this, oVariablesGlobales.getsNameDB(), null, oVariablesGlobales.getiVersion());
        SQLiteDatabase oDB = oAdmin.getWritableDatabase();

        txtUrl = (EditText) findViewById(R.id.txtServer);
        txtNamespace = (EditText) findViewById(R.id.txtNameSpace);
        txtDB = (EditText) findViewById(R.id.txtDB);
        lblEntry = (TextView) findViewById(R.id.lblEntry);
        CargaConfiguracion();
        txtUrl.setText("http://192.168.0.174:8181/WS_WMS/Service.asmx");
        txtNamespace.setText("http://192.168.0.174:8181/WS_WMS/");
        txtDB.setText("CORTIMEX_29_11_2016");
        /*txtUrl.setText("http://ijam.dynip.com:8181/WS_WMS_PROD/Service.asmx");
        txtNamespace.setText("http://ijam.dynip.com:8181/WS_WMS_PROD/");
        txtDB.setText("CORTIMEX_29_11_2016");*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnu_simple, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int iId = item.getItemId();
        if (iId == android.R.id.home) { // Valida la opcion seleccionada en el menu y  si es el boton regreso este ira al login
            Intent oIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(oIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void AgregarConfiguracion(View v) {
        clsOCFG oConn = null;
        try {
            if (txtUrl.getText().length() > 0) {
                if (txtNamespace.getText().length() > 0) {
                    if (txtNamespace.getText().length() > 0) {
                        oConn = new clsOCFG();
                        oConn.setsBD(txtDB.getText().toString());
                        oConn.setsNAMESPACE(txtNamespace.getText().toString());
                        oConn.setsSOAP_ACTION(txtNamespace.getText().toString());
                        oConn.setsURL(txtUrl.getText().toString());
                        //oConn = new clsOCFG("CORTIMEX_29_11_2016", "", "http://ijam.dynip.com:8181/WS_WMS/", "http://ijam.dynip.com:8181/WS_WMS/", "http://ijam.dynip.com:8181/WS_WMS/Service.asmx");
//                        oConn.setsBD("CORTIMEX_29_11_2016");
//                        oConn.setsNAMESPACE("http://ijam.dynip.com:8181/WS_WMS/");
//                        oConn.setsSOAP_ACTION("http://ijam.dynip.com:8181/WS_WMS/");
//                        oConn.setsURL("http://ijam.dynip.com:8181/WS_WMS/Service.asmx");
                       /* oConn.setsBD("Editorial_Delti");
                        oConn.setsNAMESPACE("http://deltisap.dynns.com/WS_WMS/");
                        oConn.setsSOAP_ACTION("http://deltisap.dynns.com/WS_WMS/");
                        oConn.setsURL("http://deltisap.dynns.com/WS_WMS/Service.asmx");*/
                        oConn.setiDocEntry(Integer.parseInt(lblEntry.getText().toString()));

                        if (Integer.parseInt(lblEntry.getText().toString()) > 0) {
                            if (oAdmin.Modificar_Configuracion(oConn)) {
                                Mensaje("¡Se ha configurado exitosamente!");
                            } else {
                                Mensaje("No se realizó la configuración, por favor valide");
                            }
                        } else {
                            if (oAdmin.Insertar_Configuracion(oConn)) {
                                Mensaje("¡Se ha configurado exitosamente!");
                            } else {
                                Mensaje("No se realizó la configuración, por favor valide");
                            }
                        }
                    } else {
                        Mensaje("Ingrese una base de datos");
                    }
                } else {
                    Mensaje("Ingrese un Namespace");
                }
            } else {
                Mensaje("Ingrese una URL de conexión");
            }

        } catch (Exception ex) {
            Mensaje("Error: " + ex.getMessage());
        } finally {
            oConn = null;
        }
    }

    public void CargaConfiguracion() {

        try {

            Cursor oCursor = oAdmin.Recupera_Configuracion();
            int i = oCursor.getCount();
            if (i > 0) {
                oCursor.moveToFirst();
                lblEntry.setText(oCursor.getString(0));
                txtUrl.setText(oCursor.getString(1));
                txtNamespace.setText(oCursor.getString(2));
                txtDB.setText(oCursor.getString(4));
            } else {
                lblEntry.setText("0");
            }
        } catch (Exception ex) {
            Mensaje(ex.getMessage());
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            //do whatever you want the 'Back' button to do
            //as an example the 'Back' button is set to start a new Activity named 'NewActivity'
            this.startActivity(new Intent(frmCnfg .this,MainActivity.class));
        }
        return true;
    }

    public void Mensaje(String sMsg) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setMessage(sMsg);
        dlgAlert.setTitle("Configuración");
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss the dialog
                    }
                });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
}

