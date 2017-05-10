//Lista de pedidos  Código Agregado


package ijam.app_wms.Activityes;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ijam.app_wms.Adapters.adpEntradas_Lines;
import ijam.app_wms.Clases.Global_Functions;
import ijam.app_wms.Clases.clsCntInv_Lines;
import ijam.app_wms.Clases.clsDatos_OnLines;
import ijam.app_wms.R;
import ijam.app_wms.SQLite.AdminSQLite;
import ijam.app_wms.SQLite.clsOCFG;

public class frmLst_EntradasLines extends AppCompatActivity {
    ListView oLstLines;
    ProgressDialog oPrgsDialog = null;
    adpEntradas_Lines oAdapter;
    clsCntInv_Lines oCnt_Lineas = null;
    ArrayList<clsCntInv_Lines> alstLineas;
    Bundle oBundle;
    String sDocEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frmlst_entradaslines);
        Toolbar oToolbar = (Toolbar) findViewById(R.id.barra);
        setSupportActionBar(oToolbar);
        ActionBar oActionBar = getSupportActionBar(); // Objeto para instanciar el menu simple
        oActionBar.setHomeButtonEnabled(true); // Activa en el toolbar la opcion de regresar
        oActionBar.setDisplayHomeAsUpEnabled(true);
        oLstLines =(ListView)findViewById(R.id.list_Entr_Lines);
        oBundle = getIntent().getExtras();
        if (oBundle != null){
            sDocEntry = oBundle.getString("DocEntry");
        } else{
            sDocEntry="0";
        }

        oPrgsDialog = oPrgsDialog.show(this, "Por favor espere...", "Mientras se cargan sus artículos", true, false);
        Thread oHilo = new Thread(){
            @Override
            public void run() {

                CargaLineas(sDocEntry);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        oPrgsDialog.dismiss();
                        Llena_OnLineas(alstLineas);
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
            Intent oIntent = new Intent(getApplicationContext(), frmEntradas.class);
            startActivity(oIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void CargaLineas(String sDocEnt){
        AdminSQLite oAdmin = null;
        clsOCFG oConn= null;

        try{
            final Global_Functions oVariablesGlobales = new Global_Functions();
            oAdmin = new AdminSQLite(this, oVariablesGlobales.getsNameDB(), null, oVariablesGlobales.getiVersion());
            oConn = new clsOCFG();
            int i=  oAdmin.Recupera_Usuario(oConn).getCount();
            if (i>0){
                oCnt_Lineas = new clsCntInv_Lines();
                oCnt_Lineas.setsDocEntry(sDocEnt);
                alstLineas= oCnt_Lineas.Reupera_Entradas_Lineas(oConn);
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

    public void Llena_OnLineas(final ArrayList<clsCntInv_Lines> oRec) {
        try
        {
            oAdapter = new adpEntradas_Lines(this, getOnLines(oRec)  );
            oLstLines.setAdapter(oAdapter);
            oLstLines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    clsDatos_OnLines oDatos = (clsDatos_OnLines)((adpEntradas_Lines)parent.getAdapter()).getItem(position);
                    Intent oAddOnHead = new Intent(frmLst_EntradasLines.this, frmAdd_EntradasLines.class);
                    oAddOnHead.putExtra("DocEntry", oDatos.getDocEntry());
                    oAddOnHead.putExtra("CodeBars", oDatos.getCodeBars());
                    oAddOnHead.putExtra("Rtrn", "1");
                    startActivity(oAddOnHead);
                    finish();
                }
            });
          /*  oLstLines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    clsDatos_OnLines oDatos = (clsDatos_OnLines)((adpCntInv_Lines)parent.getAdapter()).getItem(position);
                    String se =  oDatos.getDocEntry();
                }
            });*/
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private ArrayList<clsDatos_OnLines> getOnLines(ArrayList<clsCntInv_Lines> oRec ){
        ArrayList<clsDatos_OnLines> oOnLine = new ArrayList<clsDatos_OnLines>();
        clsDatos_OnLines d;
        ArrayList<clsCntInv_Lines> oResp = new ArrayList<clsCntInv_Lines>(oRec);
        try {
            for (int i = 0; i < oResp.size(); i++) {
                if (Double.parseDouble(oResp.get(i).getsQuantity()) > 0) {
                    d = new clsDatos_OnLines(R.drawable.itm, R.drawable.btncircle_verde, oResp.get(i).getsDocEntry(), oResp.get(i).getiLineNum(), oResp.get(i).getsItemCode(), oResp.get(i).getsCodeBars(), oResp.get(i).getsItemName(), oResp.get(i).getsQuantity(), oResp.get(i).getsQuantityTotal(), oResp.get(i).getsUbicacion(), oResp.get(i).getsQuantityTotal()); //*****Agregado******
                } else {
                    d = new clsDatos_OnLines(R.drawable.itm, R.drawable.btncircle_rojo, oResp.get(i).getsDocEntry(), oResp.get(i).getiLineNum(), oResp.get(i).getsItemCode(), oResp.get(i).getsCodeBars(), oResp.get(i).getsItemName(), oResp.get(i).getsQuantity(), oResp.get(i).getsQuantityTotal(), oResp.get(i).getsUbicacion(), oResp.get(i).getsQuantityTotal()); //*****Agregado******
                }

                oOnLine.add(d);
            }
        }catch (Exception ex){
            Mensaje(ex.getMessage());
        }
        return oOnLine;
    }

    public void Mensaje(String sMsg ){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(sMsg);
        dlgAlert.setTitle("Artículos");
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
