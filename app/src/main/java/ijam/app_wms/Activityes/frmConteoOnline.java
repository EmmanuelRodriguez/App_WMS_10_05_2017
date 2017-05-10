//Conteo de inventario


package ijam.app_wms.Activityes;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import ijam.app_wms.Clases.Global_Functions;
import ijam.app_wms.Clases.clsCntInv_Head;
import ijam.app_wms.Clases.clsContext;
import ijam.app_wms.Clases.clsDatos_OnHead;
import ijam.app_wms.R;
import ijam.app_wms.SQLite.AdminSQLite;
import ijam.app_wms.SQLite.clsOCFG;
import ijam.app_wms.Adapters.adpCntInv_OnHead;

/**
 * Created by alberto.ramirez on 17/02/2017.
 */
public class frmConteoOnline extends AppCompatActivity {
    SearchView oSearchView;
    MenuItem oSearchMenuItem;
    ListView oLstHeads;
    clsCntInv_Head oCnt_Head = null;
    ProgressDialog oPrgsDialog = null;
    adpCntInv_OnHead oAdapter;
    ArrayList<clsCntInv_Head> alstDoc;
    private String sDocEntry;
    private String sMsgError ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_conteoonline);
        Toolbar oToolbar = (Toolbar) findViewById(R.id.barra);
        setSupportActionBar(oToolbar);
        ActionBar oActionBar = getSupportActionBar(); // Objeto para instanciar el menu simple
        oActionBar.setHomeButtonEnabled(true); // Activa en el toolbar la opcion de regresar
        oActionBar.setDisplayHomeAsUpEnabled(true);
        oLstHeads =(ListView)findViewById(R.id.list_Onl_Docs);

        oPrgsDialog = oPrgsDialog.show(this, "Por favor espere...", "Mientras se cargan sus documentos", true, false);
        Thread oHilo = new Thread(){
            @Override
            public void run() {

                CargaConfiguracion();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        oPrgsDialog.dismiss();
                        Llena_OnHeads(alstDoc);
                    }
                });
            }
        };
        oHilo.start();
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mnu_search, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchViewAndroidActionBar.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String oText = newText;
                oAdapter.Filtro(oText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int iId = item.getItemId();
        if (iId == android.R.id.home) { // Valida la opcion seleccionada en el menu y  si es el boton regreso este ira al login
                Intent oIntent = new Intent(getApplicationContext(), frmMenuInv.class);
                startActivity(oIntent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void CargaConfiguracion(){
        AdminSQLite oAdmin = null;
        clsOCFG oConn= null;

        try{
            final Global_Functions oVariablesGlobales = new Global_Functions();
            oAdmin = new AdminSQLite(this, oVariablesGlobales.getsNameDB(), null, oVariablesGlobales.getiVersion());
            oConn = new clsOCFG();
            int i=  oAdmin.Recupera_Usuario(oConn).getCount();
            if (i>0){
                oCnt_Head = new clsCntInv_Head();
                alstDoc= oCnt_Head.Reupera_CntIn_Cab(oConn);
            }
            else{
                Mensaje("No hay documentos disponibles");
            }
        }catch (Exception ex){
            Mensaje(ex.getMessage());
        }finally {
            oAdmin=null;
            oConn=null;
        }
    }

    public void Llena_OnHeads(final ArrayList<clsCntInv_Head> oRec) {
        try
        {
            oAdapter = new adpCntInv_OnHead(this,getOnHead(oRec)  );

            oLstHeads.setAdapter(oAdapter);
            oLstHeads.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    clsDatos_OnHead oDatos = (clsDatos_OnHead)((adpCntInv_OnHead)parent.getAdapter()).getItem(position);
                    Intent oAddOnHead = new Intent(frmConteoOnline.this, frmAdd_CntOnLines.class);
                    oAddOnHead.putExtra("DocEntry", oDatos.getDocEntry());
                    oAddOnHead.putExtra("CodeBars", "");
                    oAddOnHead.putExtra("Rtrn", "0");
                    startActivity(oAddOnHead);
                    finish();
                }
            });
            oLstHeads.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    String sDocNum =""; final int iPosition;
                    final clsDatos_OnHead oDatos = (clsDatos_OnHead)((adpCntInv_OnHead)parent.getAdapter()).getItem(position);
                    sDocNum =oDatos.getDocNum();
                    sDocEntry= oDatos.getDocEntry();
                    iPosition= position;
                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(frmConteoOnline.this);
                    builder.setMessage("¿Desea cerrar el documento " + sDocNum + "?");

                    builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

                    builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if(Cerrar_Cabecera(sDocEntry)){
                                Object toRemove = alstDoc.remove(iPosition);
                                alstDoc.remove(toRemove);
                            }
                        }
                    });
                    android.app.AlertDialog alert = builder.create();
                    alert.show();
                    return true;
                }
            });
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private ArrayList<clsDatos_OnHead> getOnHead(ArrayList<clsCntInv_Head> oRec ){
        ArrayList<clsDatos_OnHead> oOnHead = new ArrayList<clsDatos_OnHead>();
        clsDatos_OnHead d;
        ArrayList<clsCntInv_Head> oResp = new ArrayList<clsCntInv_Head>(oRec);
        for (int i = 0; i < oResp.size(); i++) {
            d= new clsDatos_OnHead( R.drawable.doc, oResp.get(i).getsDocEntry(),oResp.get(i).getsDocNum(), oResp.get(i).getsDocDate(), oResp.get(i).getsCountType(),  oResp.get(i).getsComentarios(), oResp.get(i).getsHora() );
            oOnHead.add(d);
        }

        return oOnHead;
    }

    public void AddOnHead (View v){
        Intent oAddOnHead = new Intent(frmConteoOnline.this, frmAdd_CntOnHead.class);
        startActivity(oAddOnHead);
        finish();
    }

    public boolean Cerrar_Cabecera( final String sEntry){
        boolean bResp= false;
        oPrgsDialog = oPrgsDialog.show(frmConteoOnline.this, "Por favor espere...", "Mientras se cierra su documento", true, false);
        Thread oThread = new Thread(){
            @Override
            public void run() {
                AdminSQLite oAdmin = null;
                clsOCFG oConn= null;

                try{
                    final Global_Functions oVariablesGlobales = new Global_Functions();
                    oAdmin = new AdminSQLite(frmConteoOnline.this, oVariablesGlobales.getsNameDB(), null, oVariablesGlobales.getiVersion());
                    oConn = new clsOCFG();
                    int i=  oAdmin.Recupera_Usuario(oConn).getCount();
                    if (i>0){
                        oCnt_Head = new clsCntInv_Head();
                        oCnt_Head.setsDocEntry(sEntry);
                        if (oCnt_Head.Close_DocumentoCntOnline(oConn)){
                            sMsgError = null;
                        } else {
                            throw new RuntimeException(oCnt_Head.getsMsgError());
                        }
                    }
                    else{
                        sMsgError ="No hay configuración disponible";
                    }
                }catch (Exception ex){
                    sMsgError = ex.getMessage();
                }finally {
                    oAdmin=null;
                    oConn=null;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        oPrgsDialog.dismiss();
                        try {
                            if (sMsgError != null || sMsgError == "") {
                                throw new RuntimeException(sMsgError);
                            }
                            else
                            {
                                oPrgsDialog = oPrgsDialog.show(frmConteoOnline.this, "Por favor espere...", "Mientras se cargan sus documentos", true, false);
                                Thread oHilo = new Thread(){
                                    @Override
                                    public void run() {

                                        CargaConfiguracion();
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                oPrgsDialog.dismiss();
                                                Llena_OnHeads(alstDoc);
                                            }
                                        });
                                    }
                                };
                                oHilo.start();
                            }
                        } catch (Exception ex) {
                            Mensaje(ex.getMessage());
                        }
                    }
                });
            }
        };
        oThread.start();
        if (sMsgError != null || sMsgError==""){
            bResp=false;
        }else{
            bResp=true;
            Toast.makeText(getBaseContext(), "Documento cerrado exitosamente", Toast.LENGTH_LONG).show();
        }
        return bResp;
    }

    public void Mensaje(String sMsg ){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(sMsg);
        dlgAlert.setTitle("Conteo de inventario");
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
