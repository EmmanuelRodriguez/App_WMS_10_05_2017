package ijam.app_wms.Activityes;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ijam.app_wms.Clases.Global_Functions;
import ijam.app_wms.Clases.clsCntInv_Lines;
import ijam.app_wms.R;
import ijam.app_wms.SQLite.AdminSQLite;
import ijam.app_wms.SQLite.clsOCFG;

public class frmAdd_EntradasLines extends AppCompatActivity {
    private ProgressDialog oPrgsDialog=null;
    private EditText txtEntrLines_CodeBars= null;
    //private EditText txtEntrLines_Cnt = null;
    private TextView lblEntrLines_Cnt = null;
    private EditText txtEntrLines_Ubic= null;
    private TextView lblEntrLines_CntTotal= null;
    private TextView lblEntrLines_CntTotalReg= null;
    private TextView lblEntrLines_ItemName= null;
    private TextView lblEntrLines_ItemCode= null;
    private TextView lblEntrLines_CodeBars= null;
    private TextView lblEntrLines_WshCode= null;
    private TextView lblEntrLines_LineNum= null;
    private TextView lblEntrLines_Msg= null;
    private TextView lblEntrLines_OrderEntry = null;
    private String sDocEntry;
    private String sCodeBars;
    private String sMsgError ="";
    private String sLastLayout = "";
    private clsCntInv_Lines oCnt_Lines = null;
    Bundle oBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frmadd_entradaslines );
        Toolbar oToolbar =(Toolbar) findViewById(R.id.barra);
        setSupportActionBar(oToolbar);
        ActionBar oActionBar = getSupportActionBar(); // Objeto para instanciar el menu simple
        oActionBar.setHomeButtonEnabled(true); // Activa en el toolbar la opcion de regresar
        oActionBar.setDisplayHomeAsUpEnabled(true);
        oBundle = getIntent().getExtras();
        if (oBundle != null){
            sDocEntry = oBundle.getString("DocEntry");
            sCodeBars = oBundle.getString("CodeBars");
            sLastLayout =oBundle.getString("Rtrn");

            if (sCodeBars.length()>0){
                InstanciarElementos();
                txtEntrLines_CodeBars.setText(sCodeBars);
                ScanCodeBars(txtEntrLines_CodeBars.getText().toString());
                txtEntrLines_CodeBars.requestFocus();
                //if (ValidaItemCntLines()) {}
            }
        } else{
            sDocEntry="0";
        }

        InstanciarElementos();

        //Esto es para lectura de la caja de texto CodeBars
        txtEntrLines_CodeBars.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String sBar = String.valueOf(txtEntrLines_CodeBars.getText().toString());
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if ((sBar != "") || (sBar != null)) {
                        if (Valida_Item(lblEntrLines_CodeBars.getText().toString(), sBar)) {//Validar si el CodeBars es igual al lblCodeBars   Si
                            if (Integer.valueOf(lblEntrLines_CntTotal.getText().toString()) >= 0) {
                                if ((lblEntrLines_Cnt.getText().toString() != "") || (lblEntrLines_Cnt.getText().toString() != null)) {
                                    lblEntrLines_CntTotal.setText(String.valueOf(Integer.valueOf(lblEntrLines_Cnt.getText().toString()) + Integer.valueOf(lblEntrLines_CntTotal.getText().toString())));
                                    // if (ValidaItemCntLines()) {}
                                    String sLine = lblEntrLines_LineNum.getText().toString();
                                    if (sLine.equals("") || sLine.equals(null) || sLine == null) {
                                        ScanCodeBars(txtEntrLines_CodeBars.getText().toString());
                                        txtEntrLines_CodeBars.setText("");
                                        txtEntrLines_CodeBars.requestFocus();

                                    } else {
                                        AumentaCantidad(1, Integer.parseInt(lblEntrLines_CntTotal.getText().toString()));
                                        txtEntrLines_CodeBars.setText("");
                                        txtEntrLines_CodeBars.requestFocus();
                                    }
                                }
                            }
                        } else {
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(frmAdd_EntradasLines.this);
                            builder.setMessage("¿Desea cambiar del artículo?")
                                    .setTitle("Confime")
                                    .setCancelable(false)
                                    .setNegativeButton("Cancelar",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    dialog.cancel();
                                                }
                                            })
                                    .setPositiveButton("Continuar",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                    //if (ValidaItemCntLines()) {}
                                                    ScanCodeBars(txtEntrLines_CodeBars.getText().toString());
                                                    txtEntrLines_CodeBars.setText("");
                                                    lblEntrLines_CntTotal.setText("1");
                                                    txtEntrLines_CodeBars.requestFocus();
                                                }
                                            });
                            android.app.AlertDialog alert = builder.create();
                            alert.show();
                        }
                    }
                }
                txtEntrLines_CodeBars.requestFocus();
                return true;
            }
        });

    }

    //
    public void InstanciarElementos () {
         txtEntrLines_CodeBars= (EditText) findViewById(R.id.txtEntrLines_CodeBars);
         //txtEntrLines_Cnt = (EditText) findViewById(R.id.txtEntrLines_Cnt);
         lblEntrLines_Cnt = (TextView) findViewById(R.id.lblEntrLines_Cnt);
         lblEntrLines_Cnt = (TextView) findViewById(R.id.lblEntrLines_Cnt);
         txtEntrLines_Ubic= (EditText) findViewById(R.id.txtEntrLines_Ubic);
         lblEntrLines_CntTotal= (TextView) findViewById(R.id.lblEntrLines_CntTotal);
         lblEntrLines_ItemName= (TextView) findViewById(R.id.lblEntrLines_ItemName);
         lblEntrLines_ItemCode= (TextView) findViewById(R.id.lblEntrLines_ItemCode);
         lblEntrLines_CodeBars= (TextView) findViewById(R.id.lblEntrLines_CodeBars);
         lblEntrLines_WshCode= (TextView) findViewById(R.id.lblEntrLines_WshCode);
         lblEntrLines_LineNum= (TextView) findViewById(R.id.lblEntrLines_LineNum);
         lblEntrLines_CntTotalReg= (TextView) findViewById(R.id.lblEntrLines_CntTotalReg);
         lblEntrLines_OrderEntry = (TextView) findViewById(R.id.lblEntrLines_OrderEntry );
         lblEntrLines_Msg= (TextView) findViewById(R.id.lblEntrLines_Msg);
         lblEntrLines_CntTotal.setText("0");
         lblEntrLines_Cnt.setText("1");
         lblEntrLines_CntTotalReg.setText("0");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnu_detalle , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int iId = item.getItemId();
        if (iId == android.R.id.home) { // Valida la opcion seleccionada en el menu y  si es el boton regreso este ira al login
            if(Integer.parseInt(sLastLayout) == 0 ){
                Intent oIntent = new Intent(getApplicationContext(), frmEntradas.class);
                startActivity(oIntent);
                finish();
            }
            else{
                Intent oIntent = new Intent(getApplicationContext(), frmLst_EntradasLines.class);
                oIntent.putExtra("DocEntry", sDocEntry);
                startActivity(oIntent);
                finish();
            }
        }
        if (iId==R.id.action_Detalle_OnHeadLines){
            Intent oIntent = new Intent(getApplicationContext(), frmLst_EntradasLines.class);
            oIntent.putExtra("DocEntry",sDocEntry);
            startActivity(oIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public Integer AumentaCantidad(Integer iCantidad, Integer iContador) {
        Integer iTotal = 0;
        iTotal = iCantidad + iContador;
        return iTotal;
    }

    public boolean ScanCodeBars(final String sCodeBars) {
         boolean bResp = false;
            sMsgError=null;
            oPrgsDialog = oPrgsDialog.show(frmAdd_EntradasLines.this, "Por favor espere... ", "Mientras se carga su artículo", true, false);
            Thread oHilo = new Thread() {
                @Override
                public void run() {
                    AdminSQLite oAdmin = null;
                    clsOCFG oConn = null;
                    try {
                        final Global_Functions oVariablesGlobales = new Global_Functions();
                        oAdmin = new AdminSQLite(frmAdd_EntradasLines.this, oVariablesGlobales.getsNameDB(), null, oVariablesGlobales.getiVersion());
                        oConn = new clsOCFG();
                        int i = oAdmin.Recupera_Usuario(oConn).getCount();
                        if (i > 0) {
                            oCnt_Lines = new clsCntInv_Lines();
                            oCnt_Lines.setsDocEntry(sDocEntry);
                            oCnt_Lines.setsCodeBars(sCodeBars);
                            if (oCnt_Lines.Get_EntradasItem(oConn) ) {
                                sMsgError=null;
                            }else {
                                throw new Exception(oCnt_Lines.getsMsgError());
                            }
                        } else {
                            throw new Exception("No hay artículos disponibles");
                        }
                    } catch (Exception ex) {
                        sMsgError = ex.getMessage();
                    } finally {
                        oAdmin = null;
                        oConn = null;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (sMsgError !=null ) {
                                Mensaje(sMsgError);
                            }else
                            {
                                if(oCnt_Lines.getiLineNum() >= -1) {
                                    lblEntrLines_ItemCode.setText(oCnt_Lines.getsItemCode());
                                    lblEntrLines_ItemName.setText(oCnt_Lines.getsItemName());
                                    lblEntrLines_CodeBars.setText(oCnt_Lines.getsCodeBars());
                                    lblEntrLines_WshCode.setText(oCnt_Lines.getsWhsHouse());
                                    lblEntrLines_CntTotalReg.setText(oCnt_Lines.getsQuantityTotal());
                                    lblEntrLines_OrderEntry.setText(oCnt_Lines.getsOrderEntry());
                                    txtEntrLines_Ubic.setText(oCnt_Lines.getsUbicacion().replace("anyType{}" ,"" ));
                                    if (oCnt_Lines.getiLineNum() == -1) {
                                        lblEntrLines_LineNum.setText(String.valueOf(oCnt_Lines.getiLineNum()));
                                        lblEntrLines_Msg.setText("El artículo no existe");
                                    }
                                    else{
                                        lblEntrLines_LineNum.setText(String.valueOf(oCnt_Lines.getiLineNum()));
                                    }
                                    txtEntrLines_CodeBars.setText("");
                                } else if(oCnt_Lines.getiLineNum() == -2){
                                    Mensaje("El artículo no esta disponible");
                                }
                            }
                            oPrgsDialog.dismiss();
                            txtEntrLines_CodeBars.requestFocus();
                        }
                    });
                }
            };
            oHilo.start();
        if (sMsgError !=null || sMsgError!="") {
            bResp =false;
        }else{
            bResp =true;
        }

        return bResp;
    }

    public boolean Valida_Item(String sInic_Bar, String sFin_Bar) {
        boolean bResp = false;
        if (sInic_Bar.equals("") ) {
            bResp = true;
        }else{
            if (sInic_Bar.compareTo(sFin_Bar) == 0) {
                bResp = true;
            }
        }
        return bResp;
    }

    public void Btn_SaveItem(View view){
        if(lblEntrLines_CodeBars.length() > 0) {
            if(txtEntrLines_Ubic.length() > 0) {
                if (Integer.parseInt(lblEntrLines_CntTotal.getText().toString()) > 0){
                    if (Integer.parseInt(lblEntrLines_CntTotal.getText().toString()) > Integer.parseInt(lblEntrLines_CntTotalReg.getText().toString())){
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(frmAdd_EntradasLines.this);
                        builder.setMessage("Introduzca una cantidad igual o inferior a la cantidad liberada, la cantidad es de:  " + lblEntrLines_CntTotalReg.getText().toString());

                        builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                txtEntrLines_CodeBars.requestFocus();
                            }
                        });

                        builder.setNegativeButton("Agregar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (Update_ItemCntLines(0)){
                                    txtEntrLines_CodeBars.requestFocus();
                                }
                            }
                        });

                        android.app.AlertDialog alert = builder.create();
                        alert.show();
                    }
                    else
                    {
                        if (Update_ItemCntLines(0)){
                            //Limpia_campos();d
                            txtEntrLines_CodeBars.requestFocus();
                        }
                    }
                    /* if (ValidaItemCntLines()){

                    }else {
                        Mensaje("Nose registro el conteo, por favor intentelo mas tarde.");
                    }*/

                }else{
                    Mensaje("Por favor registre una cantidad");
                }
            }else{
                Mensaje("Por favor ingrese una ubicación");
            }
        } else
        {
            Mensaje("Por favor ingrese un código de barras");
        }
    }

    public boolean ValidaItemCntLines()
    {
         boolean bResp= false;
        oPrgsDialog = oPrgsDialog.show(frmAdd_EntradasLines.this, "Por favor espere...", "Mientras se carga su conteo", true, false);
        Thread oThread = new Thread(){
            @Override
            public void run() {
                AdminSQLite oAdmin = null;
                clsOCFG oConn= null;
                Cursor oCursor=null;
                try{
                    Global_Functions oVariablesGlobales = new Global_Functions();
                    oAdmin = new AdminSQLite(frmAdd_EntradasLines.this, oVariablesGlobales.getsNameDB(), null, oVariablesGlobales.getiVersion());
                    oConn = new clsOCFG();
                    int i=  oAdmin.Recupera_Usuario(oConn).getCount();
                    if (i>0){
                        oCnt_Lines = new clsCntInv_Lines();
                        oCnt_Lines.setsDocEntry(sDocEntry);
                        oCnt_Lines.setsCodeBars(txtEntrLines_CodeBars.getText().toString());
                        if(oCnt_Lines.Valida_Cnt_Item(oConn)) {
                            sMsgError=null;
                        }else{
                            throw new Exception(oCnt_Lines.getsMsgError());
                        }
                    }
                    else{
                        throw new Exception("No hay conexión disponible");
                    }
                }catch (Exception ex){
                    sMsgError= ex.getMessage();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        oPrgsDialog.dismiss();
                        try
                        {
                            if (sMsgError != null ){
                                throw new RuntimeException(sMsgError);
                            }else{
                                if (Integer.parseInt(oCnt_Lines.getsQuantity()) > 0) {
                                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(frmAdd_EntradasLines.this);
                                    builder.setMessage("Ya existe una cantidad de " + oCnt_Lines.getsQuantity() + " para este artículo, ¿Desea remplazar o agregarlo al conteo?");

                                    builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                                    builder.setPositiveButton("Remplazar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            ScanCodeBars(txtEntrLines_CodeBars.getText().toString());
                                            lblEntrLines_CntTotal.setText(oCnt_Lines.getsQuantity());
                                        }
                                    });

                                    builder.setNegativeButton("Agregar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            ScanCodeBars(txtEntrLines_CodeBars.getText().toString());
                                            lblEntrLines_CntTotal.setText(String.valueOf(Integer.parseInt(oCnt_Lines.getsQuantity()) + Integer.parseInt(lblEntrLines_CntTotal.getText().toString())));
                                        }
                                    });

                                    android.app.AlertDialog alert = builder.create();
                                    alert.show();
                                }else
                                {
                                    ScanCodeBars(txtEntrLines_CodeBars.getText().toString());
                                }
                            }

                        }catch (Exception ex){
                            Mensaje(ex.getMessage());
                        }
                    }
                });
            }
        };
        oThread.start();
        if (sMsgError != null ){
            bResp=false;
        }else{
            bResp=true;
        }

        return bResp;
    }

    public boolean Update_ItemCntLines(final int iTipe){
        boolean bResp= false;
        oPrgsDialog = oPrgsDialog.show(frmAdd_EntradasLines.this, "Por favor espere...", "Mientras se guarda su conteo", true, false);
        Thread oThread = new Thread(){
            @Override
            public void run() {
                AdminSQLite oAdmin = null;
                clsOCFG oConn= null;
                Cursor oCursor=null;
                try{
                    Global_Functions oVariablesGlobales = new Global_Functions();
                    oAdmin = new AdminSQLite(frmAdd_EntradasLines.this, oVariablesGlobales.getsNameDB(), null, oVariablesGlobales.getiVersion());
                    oConn = new clsOCFG();
                    int i=  oAdmin.Recupera_Usuario(oConn).getCount();
                    if (i>0){
                        Double dTotalCount = 0.0 ;
                        oCnt_Lines = new clsCntInv_Lines();
                        oCnt_Lines.setsDocEntry(sDocEntry);
                        oCnt_Lines.setiLineNum(Integer.parseInt(lblEntrLines_LineNum.getText().toString()));
                        oCnt_Lines.setsItemCode(lblEntrLines_ItemCode.getText().toString());
                        oCnt_Lines.setsCodeBars(lblEntrLines_CodeBars.getText().toString());
                        if(iTipe == 0){
                            dTotalCount = Double.parseDouble( lblEntrLines_CntTotal.getText().toString());
                        }else {
                            dTotalCount = Double.parseDouble( lblEntrLines_CntTotal.getText().toString()) + Double.parseDouble(lblEntrLines_CntTotalReg.getText().toString());
                        }
                        oCnt_Lines.setsQuantity(String.valueOf(dTotalCount));
                        oCnt_Lines.setsUbicacion(txtEntrLines_Ubic.getText().toString());
                        oCnt_Lines.setsOrderEntry(lblEntrLines_OrderEntry.getText().toString()) ;

                            if (oCnt_Lines.Update_ItemEntradasline(oConn) ) {
                                sMsgError = null;
                            } else {
                                throw new RuntimeException(oCnt_Lines.getsMsgError());
                            }
                    }
                    else{
                        throw new RuntimeException("No hay usuario disponible");
                    }
                }catch (Exception ex){
                    sMsgError= ex.getMessage();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        oPrgsDialog.dismiss();
                        try
                        {
                            if (sMsgError != null || sMsgError==""){
                                throw new RuntimeException(sMsgError);
                            }else
                            {
                                Limpia_campos();
                                Toast.makeText(getBaseContext(), "Cantidad de artículo " + lblEntrLines_CodeBars.getText().toString() + " guardada exitosamente", Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception ex){
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

        }

        return bResp;
    }

    public void Limpia_campos(){
        txtEntrLines_CodeBars.setText("");
        lblEntrLines_Cnt.setText("1");
        txtEntrLines_Ubic.setText("");
        lblEntrLines_CntTotalReg.setText("0");
        lblEntrLines_CntTotal.setText("0");
        lblEntrLines_ItemName.setText("");
        lblEntrLines_ItemCode.setText("");
        lblEntrLines_CodeBars.setText("");
        lblEntrLines_WshCode.setText("");
        lblEntrLines_LineNum.setText("");
        lblEntrLines_Msg.setText("");
        lblEntrLines_OrderEntry.setText("");

    }

    public void Mensaje(String sMsg ){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage(sMsg);
        dlgAlert.setTitle("Escaneo de artículos");
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
