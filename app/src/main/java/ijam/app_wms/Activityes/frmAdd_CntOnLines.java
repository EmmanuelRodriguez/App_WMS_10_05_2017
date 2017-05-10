//Escaneo de los artículos (agregado)


package ijam.app_wms.Activityes;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import ijam.app_wms.Clases.Global_Functions;
import ijam.app_wms.Clases.clsCntInv_Head;
import ijam.app_wms.Clases.clsCntInv_Lines;
import ijam.app_wms.R;
import ijam.app_wms.SQLite.AdminSQLite;
import ijam.app_wms.SQLite.clsOCFG;

public class frmAdd_CntOnLines extends AppCompatActivity {
    private ProgressDialog oPrgsDialog=null;
    private EditText txtCntOnLines_CodeBars= null;
    private EditText txtCntOnLines_Cnt = null;
    private EditText txtCntOnLines_Ubic= null;
    private TextView lblCntOnLines_CntTotal= null;
    private TextView lblCntOnLines_CntTotalReg= null;
    private TextView lblCntOnLines_ItemName= null;
    private TextView lblCntOnLines_ItemCode= null;
    private TextView lblCntOnLines_CodeBars= null;
    private TextView lblCntOnLines_WshCode= null;
    private TextView lblCntOnLines_LineNum= null;
    private TextView lblCntOnLines_Msg= null;
    private String sDocEntry;
    private String sCodeBars;
    private String sMsgError ="";
    private String sLastLayout = "";
    private clsCntInv_Lines oCnt_Lines = null;
    Bundle oBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frmadd_cntonlines);
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
                txtCntOnLines_CodeBars.setText(sCodeBars);
                ScanCodeBars(txtCntOnLines_CodeBars.getText().toString());
                txtCntOnLines_CodeBars.requestFocus();
                //if (ValidaItemCntLines()) {}
            }


        } else{
            sDocEntry="0";
        }


        InstanciarElementos();

        //Eneto para lectura de la caja de texto CodeBars
        txtCntOnLines_CodeBars.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String sBar = String.valueOf(txtCntOnLines_CodeBars.getText().toString());
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if ((sBar != "") || (sBar != null)) {
                        if (Valida_Item(lblCntOnLines_CodeBars.getText().toString(), sBar)) {//Validar si el CodeBars es igual al lblCodeBars   Si
                            if (Integer.valueOf(lblCntOnLines_CntTotal.getText().toString()) >= 0) {
                                if ((txtCntOnLines_Cnt.getText().toString() != "") || (txtCntOnLines_Cnt.getText().toString() != null)) {
                                    lblCntOnLines_CntTotal.setText(String.valueOf(Integer.valueOf(txtCntOnLines_Cnt.getText().toString()) + Integer.valueOf(lblCntOnLines_CntTotal.getText().toString())));
                                    // if (ValidaItemCntLines()) {}
                                    String sLine = lblCntOnLines_LineNum.getText().toString();
                                    if (sLine.equals("") || sLine.equals(null) || sLine == null) {
                                        ScanCodeBars(txtCntOnLines_CodeBars.getText().toString());
                                        txtCntOnLines_CodeBars.setText("");
                                        txtCntOnLines_CodeBars.requestFocus();

                                    } else {
                                        AumentaCantidad(1, Integer.parseInt(lblCntOnLines_CntTotal.getText().toString()));
                                        txtCntOnLines_CodeBars.setText("");
                                        txtCntOnLines_CodeBars.requestFocus();
                                    }

                                }
                            }
                        } else {
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(frmAdd_CntOnLines.this);
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
                                                    ScanCodeBars(txtCntOnLines_CodeBars.getText().toString());
                                                    txtCntOnLines_CodeBars.setText("");
                                                    lblCntOnLines_CntTotal.setText("1");
                                                    txtCntOnLines_CodeBars.requestFocus();
                                                }
                                            });
                            android.app.AlertDialog alert = builder.create();
                            alert.show();
                        }
                    }
                }
                txtCntOnLines_CodeBars.requestFocus();
                return true;
            }
        });

    }

    public void InstanciarElementos () {
         txtCntOnLines_CodeBars= (EditText) findViewById(R.id.txtCntOnLines_CodeBars);

         txtCntOnLines_Cnt = (EditText) findViewById(R.id.txtCntOnLines_Cnt);
         txtCntOnLines_Ubic= (EditText) findViewById(R.id.txtCntOnLines_Ubic);
         lblCntOnLines_CntTotal= (TextView) findViewById(R.id.lblCntOnLines_CntTotal);
         lblCntOnLines_ItemName= (TextView) findViewById(R.id.lblCntOnLines_ItemName);
         lblCntOnLines_ItemCode= (TextView) findViewById(R.id.lblCntOnLines_ItemCode);
         lblCntOnLines_CodeBars= (TextView) findViewById(R.id.lblCntOnLines_CodeBars);
         lblCntOnLines_WshCode= (TextView) findViewById(R.id.lblCntOnLines_WshCode);
         lblCntOnLines_LineNum= (TextView) findViewById(R.id.lblCntOnLines_LineNum);
         lblCntOnLines_CntTotalReg= (TextView) findViewById(R.id.lblCntOnLines_CntTotalReg);
         lblCntOnLines_Msg= (TextView) findViewById(R.id.lblCntOnLines_Msg);
         lblCntOnLines_CntTotal.setText("0");
         txtCntOnLines_Cnt.setText("1");
         lblCntOnLines_CntTotalReg.setText("0");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnu_detalle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int iId = item.getItemId();
        if (iId == android.R.id.home) { // Valida la opcion seleccionada en el menu y  si es el boton regreso este ira al login
            if(Integer.parseInt(sLastLayout) == 0 ){
                Intent oIntent = new Intent(getApplicationContext(), frmConteoOnline.class);
                startActivity(oIntent);
                finish();
            }
            else{
                Intent oIntent = new Intent(getApplicationContext(), frmLst_CntOnLines.class);
                oIntent.putExtra("DocEntry", sDocEntry);
                startActivity(oIntent);
                finish();
            }

        }
        if (iId==R.id.action_Detalle_OnHeadLines){
            Intent oIntent = new Intent(getApplicationContext(), frmLst_CntOnLines.class);
            oIntent.putExtra("DocEntry",sDocEntry);
            startActivity(oIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void btnMas(View view){
       // InstanciarElementos();
        try {
            if (txtCntOnLines_Cnt.length() > 0) {
                if (Integer.valueOf(lblCntOnLines_CntTotal.getText().toString()) >= 0) {
                    if ((txtCntOnLines_Cnt.getText().toString() != "") || (txtCntOnLines_Cnt.getText().toString() != null)) {
                        lblCntOnLines_CntTotal.setText(String.valueOf(Integer.valueOf(txtCntOnLines_Cnt.getText().toString()) + Integer.valueOf(lblCntOnLines_CntTotal.getText().toString())));
                    }
                }
            } else {
                txtCntOnLines_Cnt.setText(String.valueOf("1"));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void btnMenos(View view){
        if (txtCntOnLines_Cnt.length() > 0) {
            if ((Integer.valueOf(lblCntOnLines_CntTotal.getText().toString()) - Integer.valueOf(txtCntOnLines_Cnt.getText().toString())) > 0) {
                if (Integer.valueOf(lblCntOnLines_CntTotal.getText().toString()) >= 0) {
                    lblCntOnLines_CntTotal.setText(String.valueOf(Integer.valueOf(lblCntOnLines_CntTotal.getText().toString()) - Integer.valueOf(txtCntOnLines_Cnt.getText().toString())));
                }
            }else{
                lblCntOnLines_CntTotal.setText("0");
            }
        } else {
            txtCntOnLines_Cnt.setText(String.valueOf("1"));
        }
    }

    public Integer AumentaCantidad(Integer iCantidad, Integer iContador) {
        Integer iTotal = 0;
        iTotal = iCantidad + iContador;
        return iTotal;
    }

    public boolean ScanCodeBars(final String sCodeBars) {
         boolean bResp = false;
            sMsgError=null;
            oPrgsDialog = oPrgsDialog.show(frmAdd_CntOnLines.this, "Por favor espere ....", "Mientras se carga su artículo", true, false);
            Thread oHilo = new Thread() {
                @Override
                public void run() {
                    AdminSQLite oAdmin = null;
                    clsOCFG oConn = null;
                    try {
                        final Global_Functions oVariablesGlobales = new Global_Functions();
                        oAdmin = new AdminSQLite(frmAdd_CntOnLines.this, oVariablesGlobales.getsNameDB(), null, oVariablesGlobales.getiVersion());
                        oConn = new clsOCFG();
                        int i = oAdmin.Recupera_Usuario(oConn).getCount();
                        if (i > 0) {
                            oCnt_Lines = new clsCntInv_Lines();
                            oCnt_Lines.setsDocEntry(sDocEntry);
                            oCnt_Lines.setsCodeBars(sCodeBars);
                            if (oCnt_Lines.Get_Item(oConn)) {
                                sMsgError=null;
                            }else {
                                throw new Exception(oCnt_Lines.getsMsgError());
                            }
                        } else {
                            throw new Exception("No hay artículos disponibles.");
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
                                    lblCntOnLines_ItemCode.setText(oCnt_Lines.getsItemCode());
                                    lblCntOnLines_ItemName.setText(oCnt_Lines.getsItemName());
                                    lblCntOnLines_CodeBars.setText(oCnt_Lines.getsCodeBars());
                                    lblCntOnLines_WshCode.setText(oCnt_Lines.getsWhsHouse());
                                    lblCntOnLines_CntTotalReg.setText(oCnt_Lines.getsQuantity());
                                    txtCntOnLines_Ubic.setText(oCnt_Lines.getsUbicacion().replace("anyType{}" ,"" ));
                                    if (oCnt_Lines.getiLineNum() == -1) {
                                        lblCntOnLines_LineNum.setText(String.valueOf(oCnt_Lines.getiLineNum()));
                                        lblCntOnLines_Msg.setText("El artículo no existe en el conteo.");
                                    }
                                    else{
                                        lblCntOnLines_LineNum.setText(String.valueOf(oCnt_Lines.getiLineNum()));
                                    }
                                    txtCntOnLines_CodeBars.setText("");
                                } else if(oCnt_Lines.getiLineNum() == -2){
                                    Mensaje("El artículo no esta disponible.");
                                }
                            }
                            oPrgsDialog.dismiss();
                            txtCntOnLines_CodeBars.requestFocus();
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
        if(lblCntOnLines_CodeBars.length()>0){
            if(txtCntOnLines_Ubic.length()>0){
                if (Integer.parseInt(lblCntOnLines_CntTotal.getText().toString()) >0){
                    if (Integer.parseInt(lblCntOnLines_CntTotalReg.getText().toString())>0){
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(frmAdd_CntOnLines.this);
                        builder.setMessage("Ya existe una cantidad de " + lblCntOnLines_CntTotalReg.getText().toString() + " para este artículo, ¿Desea remplazar o agregarlo al conteo?.");

                        builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                txtCntOnLines_CodeBars.requestFocus();
                            }
                        });

                        builder.setPositiveButton("Remplazar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (Update_ItemCntLines(0)){
                                    //Limpia_campos();
                                    txtCntOnLines_CodeBars.requestFocus();
                                }
                            }
                        });

                        builder.setNegativeButton("Agregar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (Update_ItemCntLines(1)){
                                    //Limpia_campos();
                                    txtCntOnLines_CodeBars.requestFocus();
                                }
                            }
                        });

                        android.app.AlertDialog alert = builder.create();
                        alert.show();
                    }
                    else
                    {
                        if (Update_ItemCntLines(0)){
                            //Limpia_campos();
                            txtCntOnLines_CodeBars.requestFocus();
                        }
                    }
                    /* if (ValidaItemCntLines()){

                    }else {
                        Mensaje("Nose registro el conteo, por favor intentelo mastarde.");
                    }*/

                }else{
                    Mensaje("Por favor registre una cantidad.");
                }
            }else{
                Mensaje("Por favor ingrese una ubicación.");
            }
        }else
        {
            Mensaje("Por favor ingrese un código de barras.");
        }
    }

    public boolean ValidaItemCntLines()
    {
         boolean bResp= false;
        oPrgsDialog = oPrgsDialog.show(frmAdd_CntOnLines.this, "Por favor espere...", "Mientras se carga su conteo.", true, false);
        Thread oThread = new Thread(){
            @Override
            public void run() {
                AdminSQLite oAdmin = null;
                clsOCFG oConn= null;
                Cursor oCursor=null;
                try{
                    Global_Functions oVariablesGlobales = new Global_Functions();
                    oAdmin = new AdminSQLite(frmAdd_CntOnLines.this, oVariablesGlobales.getsNameDB(), null, oVariablesGlobales.getiVersion());
                    oConn = new clsOCFG();
                    int i=  oAdmin.Recupera_Usuario(oConn).getCount();
                    if (i>0){
                        oCnt_Lines = new clsCntInv_Lines();
                        oCnt_Lines.setsDocEntry(sDocEntry);
                        oCnt_Lines.setsCodeBars(txtCntOnLines_CodeBars.getText().toString());
                        if(oCnt_Lines.Valida_Cnt_Item(oConn)) {
                            sMsgError=null;
                        }else{
                            throw new Exception(oCnt_Lines.getsMsgError());
                        }
                    }
                    else{
                        throw new Exception("No hay conexión disponible.");
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
                                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(frmAdd_CntOnLines.this);
                                    builder.setMessage("Ya existe una cantidad de " + oCnt_Lines.getsQuantity() + " para este artículo, ¿Desea remplazar o agregarlo al conteo?");

                                    builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                                    builder.setPositiveButton("Remplazar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            ScanCodeBars(txtCntOnLines_CodeBars.getText().toString());
                                            lblCntOnLines_CntTotal.setText(oCnt_Lines.getsQuantity());
                                        }
                                    });

                                    builder.setNegativeButton("Agregar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            ScanCodeBars(txtCntOnLines_CodeBars.getText().toString());
                                            lblCntOnLines_CntTotal.setText(String.valueOf(Integer.parseInt(oCnt_Lines.getsQuantity()) + Integer.parseInt(lblCntOnLines_CntTotal.getText().toString())));
                                        }
                                    });

                                    android.app.AlertDialog alert = builder.create();
                                    alert.show();
                                }else
                                {
                                    ScanCodeBars(txtCntOnLines_CodeBars.getText().toString());
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
        oPrgsDialog = oPrgsDialog.show(frmAdd_CntOnLines.this, "Por favor espere...", "Mientras se guarda su conteo.", true, false);
        Thread oThread = new Thread(){
            @Override
            public void run() {
                AdminSQLite oAdmin = null;
                clsOCFG oConn= null;
                Cursor oCursor=null;
                try{
                    Global_Functions oVariablesGlobales = new Global_Functions();
                    oAdmin = new AdminSQLite(frmAdd_CntOnLines.this, oVariablesGlobales.getsNameDB(), null, oVariablesGlobales.getiVersion());
                    oConn = new clsOCFG();
                    int i=  oAdmin.Recupera_Usuario(oConn).getCount();
                    if (i>0){
                        Double dTotalCount = 0.0 ;
                        oCnt_Lines = new clsCntInv_Lines();
                        oCnt_Lines.setsDocEntry(sDocEntry);
                        oCnt_Lines.setiLineNum(Integer.parseInt(lblCntOnLines_LineNum.getText().toString()));
                        oCnt_Lines.setsItemCode(lblCntOnLines_ItemCode.getText().toString());
                        oCnt_Lines.setsCodeBars(lblCntOnLines_CodeBars.getText().toString());
                        if(iTipe == 0){
                            dTotalCount = Double.parseDouble( lblCntOnLines_CntTotal.getText().toString());
                        }else {
                            dTotalCount = Double.parseDouble( lblCntOnLines_CntTotal.getText().toString()) + Double.parseDouble(lblCntOnLines_CntTotalReg.getText().toString());
                        }
                        oCnt_Lines.setsQuantity(String.valueOf(dTotalCount));
                        oCnt_Lines.setsUbicacion(txtCntOnLines_Ubic.getText().toString());

                            if (oCnt_Lines.Update_ItemCntOnline(oConn)) {
                                sMsgError = null;
                            } else {
                                throw new RuntimeException(oCnt_Lines.getsMsgError());
                            }
                    }
                    else{
                        throw new RuntimeException("No hay usuario disponible.");
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
            Toast.makeText(getBaseContext(), "Conteo de artículo " + lblCntOnLines_CodeBars.getText().toString() + " guardado exitosamente", Toast.LENGTH_LONG).show();
        }

        return bResp;
    }

    public void Limpia_campos(){
        txtCntOnLines_CodeBars.setText("");
        txtCntOnLines_Cnt.setText("1");
        txtCntOnLines_Ubic.setText("");
        lblCntOnLines_CntTotalReg.setText("0");
        lblCntOnLines_CntTotal.setText("0");
        lblCntOnLines_ItemName.setText("");
        lblCntOnLines_ItemCode.setText("");
        lblCntOnLines_CodeBars.setText("");
        lblCntOnLines_WshCode.setText("");
        lblCntOnLines_LineNum.setText("");
        lblCntOnLines_Msg.setText("");


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
