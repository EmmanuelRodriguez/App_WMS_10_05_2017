//Login

package ijam.app_wms.Activityes;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ijam.app_wms.Clases.Global_Functions;
import ijam.app_wms.Clases.clsContext;
import ijam.app_wms.R;
import ijam.app_wms.SQLite.AdminSQLite;
import ijam.app_wms.SQLite.clsOCFG;

public class MainActivity extends AppCompatActivity {
    ProgressDialog oPrgsDialog = null;
    String sRespWS;
    EditText txtUsuario =null;
    EditText txtPassword = null;
    clsContext oContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar oToolbar =(Toolbar) findViewById(R.id.barra);
        setSupportActionBar(oToolbar);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtPassword = (EditText) findViewById(R.id.txtPwd);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnu_cnfg, menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int iId =item.getItemId();
        if (iId==R.id.action_settings){
            Intent oIntent = new Intent(getApplicationContext(), frmCnfg.class);
            startActivity(oIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void Login(View v) {

        oPrgsDialog = oPrgsDialog.show(MainActivity.this, "Por favor espere...", "Mientras se carga su sesión", true, false);
        Thread th = new Thread() {
            @Override
            public void run() {
                if (txtUsuario.getText().length() > 0) {
                    if (txtPassword.getText().length() > 0) {
                        if (ValidaAccesos(txtUsuario.getText().toString(), txtPassword.getText().toString())){
                            sRespWS= "Success";
                        }
                    }
                    else{
                        sRespWS="Por favor ingrese la contraseña";
                    }
                }else{
                    sRespWS= "Por favor ingrese un usuario";
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        oPrgsDialog.dismiss();
                        if (sRespWS.equals("Success")) {
                            Intent lstMenu = new Intent(MainActivity.this, frmMenu.class);
                            startActivity(lstMenu);
                            finish();
                            Toast.makeText(getBaseContext(), "Bienvenido " + txtUsuario.getText().toString(), Toast.LENGTH_LONG).show();
                            //Mensaje("Bienvenido " + txtUsuario.getText().toString());
                        } else
                            //Toast.makeText(getBaseContext(),  sRespWS, Toast.LENGTH_SHORT).show();
                            Mensaje(sRespWS);
                    }
                });
            }
        };
        th.start();

    }

    public boolean ValidaAccesos(String sUser, String sPwd){
        boolean bResp =false;
        AdminSQLite oAdmin= null;
        clsOCFG oConn= null;
        Cursor oCursor=null;
        try
        {
            final Global_Functions oVariablesGlobales = new Global_Functions();

            oAdmin = new AdminSQLite(this, oVariablesGlobales.getsNameDB(), null, oVariablesGlobales.getiVersion());
            oCursor = oAdmin.Recupera_Configuracion();
            int i = oCursor.getCount();
            if (i > 0) {
                oCursor.moveToFirst();
                //oConn = new clsOCFG(oCursor.getString(4),  "", oCursor.getString(2), oCursor.getString(2), oCursor.getString(1));
                oConn = new clsOCFG();
                oContext = new clsContext();
                oContext.setsUser(sUser);
                oContext.setsPwd(sPwd);
                oConn.setsBD(oCursor.getString(4));
                oConn.setsURL(oCursor.getString(1));
                oConn.setsNAMESPACE(oCursor.getString(2));
                oConn.setsSOAP_ACTION(oCursor.getString(2));
                if (oContext.Login_WS(oConn)) {
                    oConn.setsUsuario(sUser);
                    oConn.setsPassword(sPwd);
                    oConn.setsCodeUser(String.valueOf(oContext.getiDocEntry()));
                   if (oAdmin.Insertar_Usuarios(oConn)){
                       sRespWS = "Success";
                   }else {
                       sRespWS = "No se guardo la sesión del usuario";
                   }
                } else {
                    sRespWS = oContext.getsMsg_Error();
                }
            } else {
                throw new Exception("No se encontro configuración.");
            }
        }catch (Exception ex){
            sRespWS = ex.getMessage();
        }finally {
            oAdmin=null;
            oCursor=null;
            oConn=null;
        }

        return bResp;
    }

    public void Mensaje(String sMsg ){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(sMsg);
        dlgAlert.setTitle("Inicio de sesión");
        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss the dialog
                    }
                });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    //Override the onKeyDown method
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //Handle the back button
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            //Ask the user if they want to quit
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Salir")
                    .setMessage("¿Desea salir?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        }
        else
        {
            return super.onKeyDown(keyCode, event);
        }
    }
}
