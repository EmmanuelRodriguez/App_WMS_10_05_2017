//Podría ser el listado o agregado de artículos contados

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

public class frmAdd_TrflLines extends AppCompatActivity {
    private ProgressDialog oPrgsDialog=null;
    private EditText txtTrnsfLines_CodeBars= null;
    private EditText txtTrnsfLines_Cnt = null;
    private EditText txtTrnsfLines_Ubic= null;
    private TextView lblTrnsfLines_CntTotal= null;
    private TextView lblTrnsfLines_CntTotalReg= null;
    private TextView lblTrnsfLines_ItemName= null;
    private TextView lblTrnsfLines_ItemCode= null;
    private TextView lblTrnsfLines_CodeBars= null;
    private TextView lblTrnsfLines_WshCode= null;
    private TextView lblTrnsfLines_LineNum= null;
    private TextView lblTrnsfLines_Msg= null;
    private TextView lblTrnsfLines_OrderEntry = null;
    //private TextView lblTrnsLines_QuantityTot = null; //Agregado
    private String sDocEntry;
    private String sCodeBars;
    private String sMsgError ="";
    private String sLastLayout = "";
    private clsCntInv_Lines oCnt_Lines = null;
    Bundle oBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frmadd_trfllines );
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
                txtTrnsfLines_CodeBars.setText(sCodeBars);
                ScanCodeBars(txtTrnsfLines_CodeBars.getText().toString());
                txtTrnsfLines_CodeBars.requestFocus();
                //if (ValidaItemCntLines()) {}
            }
        } else{
            sDocEntry="0";
        }

        InstanciarElementos();

        //Esto es para lectura de la caja de texto CodeBars
        txtTrnsfLines_CodeBars.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String sBar = String.valueOf(txtTrnsfLines_CodeBars.getText().toString());
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    if ((sBar != "") || (sBar != null)) {
                        if (Valida_Item(lblTrnsfLines_CodeBars.getText().toString(), sBar)) {//Validar si el CodeBars es igual al lblCodeBars   Si
                            if (Integer.valueOf(lblTrnsfLines_CntTotal.getText().toString()) >= 0) {
                                if ((txtTrnsfLines_Cnt.getText().toString() != "") || (txtTrnsfLines_Cnt.getText().toString() != null)) {
                                    lblTrnsfLines_CntTotal.setText(String.valueOf(Integer.valueOf(txtTrnsfLines_Cnt.getText().toString()) + Integer.valueOf(lblTrnsfLines_CntTotal.getText().toString())));
                                    // if (ValidaItemCntLines()) {}
                                    String sLine = lblTrnsfLines_LineNum.getText().toString();
                                    if (sLine.equals("") || sLine.equals(null) || sLine == null) {
                                        ScanCodeBars(txtTrnsfLines_CodeBars.getText().toString());
                                        txtTrnsfLines_CodeBars.setText("");
                                        txtTrnsfLines_CodeBars.requestFocus();

                                    } else {
                                        AumentaCantidad(1, Integer.parseInt(lblTrnsfLines_CntTotal.getText().toString()));
                                        txtTrnsfLines_CodeBars.setText("");
                                        txtTrnsfLines_CodeBars.requestFocus();
                                    }
                                }
                            }
                        } else {
                            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(frmAdd_TrflLines.this);
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
                                                    ScanCodeBars(txtTrnsfLines_CodeBars.getText().toString());
                                                    txtTrnsfLines_CodeBars.setText("");
                                                    lblTrnsfLines_CntTotal.setText("1");
                                                    txtTrnsfLines_CodeBars.requestFocus();
                                                }
                                            });
                            android.app.AlertDialog alert = builder.create();
                            alert.show();
                        }
                    }
                }
                txtTrnsfLines_CodeBars.requestFocus();
                return true;
            }
        });

    }

    public void InstanciarElementos () {
         txtTrnsfLines_CodeBars= (EditText) findViewById(R.id.txtTrnsfLines_CodeBars);
         txtTrnsfLines_Cnt = (EditText) findViewById(R.id.txtTrnsfLines_Cnt);
         txtTrnsfLines_Ubic= (EditText) findViewById(R.id.txtTrnsfLines_Ubic);
         lblTrnsfLines_CntTotal= (TextView) findViewById(R.id.lblTrnsfLines_CntTotal);
         lblTrnsfLines_ItemName= (TextView) findViewById(R.id.lblTrnsfLines_ItemName);
         lblTrnsfLines_ItemCode= (TextView) findViewById(R.id.lblTrnsfLines_ItemCode);
         lblTrnsfLines_CodeBars= (TextView) findViewById(R.id.lblTrnsfLines_CodeBars);
         lblTrnsfLines_WshCode= (TextView) findViewById(R.id.lblTrnsfLines_WshCode);
         lblTrnsfLines_LineNum= (TextView) findViewById(R.id.lblTrnsfLines_LineNum);
         lblTrnsfLines_CntTotalReg= (TextView) findViewById(R.id.lblTrnsfLines_CntTotalReg);
        lblTrnsfLines_OrderEntry = (TextView) findViewById(R.id.lblTrnsfLines_OrderEntry );
         lblTrnsfLines_Msg= (TextView) findViewById(R.id.lblTrnsfLines_Msg);
         lblTrnsfLines_CntTotal.setText("0");
         txtTrnsfLines_Cnt.setText("1");
         lblTrnsfLines_CntTotalReg.setText("0");
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
                Intent oIntent = new Intent(getApplicationContext(), frmTransfer.class);
                startActivity(oIntent);
                finish();
            }
            else{
                Intent oIntent = new Intent(getApplicationContext(), frmLst_TrflLines.class);
                oIntent.putExtra("DocEntry", sDocEntry);
                startActivity(oIntent);
                finish();
            }

        }
        if (iId==R.id.action_Detalle_OnHeadLines){
            Intent oIntent = new Intent(getApplicationContext(), frmLst_TrflLines.class);
            oIntent.putExtra("DocEntry", sDocEntry);
            startActivity(oIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void btnMas(View view){
       // InstanciarElementos();
        try {
            if (txtTrnsfLines_Cnt.length() > 0) {
                if (Integer.valueOf(lblTrnsfLines_CntTotal.getText().toString()) >= 0) {
                    if ((txtTrnsfLines_Cnt.getText().toString() != "") || (txtTrnsfLines_Cnt.getText().toString() != null)) {
                        lblTrnsfLines_CntTotal.setText(String.valueOf(Integer.valueOf(txtTrnsfLines_Cnt.getText().toString()) + Integer.valueOf(lblTrnsfLines_CntTotal.getText().toString())));
                    }
                }
            } else {
                txtTrnsfLines_Cnt.setText(String.valueOf("1"));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void btnMenos(View view){
        if (txtTrnsfLines_Cnt.length() > 0) {
            if ((Integer.valueOf(lblTrnsfLines_CntTotal.getText().toString()) - Integer.valueOf(txtTrnsfLines_Cnt.getText().toString())) > 0) {
                if (Integer.valueOf(lblTrnsfLines_CntTotal.getText().toString()) >= 0) {
                    lblTrnsfLines_CntTotal.setText(String.valueOf(Integer.valueOf(lblTrnsfLines_CntTotal.getText().toString()) - Integer.valueOf(txtTrnsfLines_Cnt.getText().toString())));
                }
            }else{
                lblTrnsfLines_CntTotal.setText("0");
            }
        } else {
            txtTrnsfLines_Cnt.setText(String.valueOf("1"));
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
            oPrgsDialog = oPrgsDialog.show(frmAdd_TrflLines.this, "Por favor espere...", "Mientras se carga su artículo", true, false);
            Thread oHilo = new Thread() {
                @Override
                public void run() {
                    AdminSQLite oAdmin = null;
                    clsOCFG oConn = null;
                    try {
                        final Global_Functions oVariablesGlobales = new Global_Functions();
                        oAdmin = new AdminSQLite(frmAdd_TrflLines.this, oVariablesGlobales.getsNameDB(), null, oVariablesGlobales.getiVersion());
                        oConn = new clsOCFG();
                        int i = oAdmin.Recupera_Usuario(oConn).getCount();
                        if (i > 0) {
                            oCnt_Lines = new clsCntInv_Lines();
                            oCnt_Lines.setsDocEntry(sDocEntry);
                            oCnt_Lines.setsCodeBars(sCodeBars);
                            if (oCnt_Lines.Get_TrflItem(oConn) ) {
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
                                    lblTrnsfLines_ItemCode.setText(oCnt_Lines.getsItemCode());
                                    lblTrnsfLines_ItemName.setText(oCnt_Lines.getsItemName());
                                    lblTrnsfLines_CodeBars.setText(oCnt_Lines.getsCodeBars());
                                    lblTrnsfLines_WshCode.setText(oCnt_Lines.getsWhsHouse());
                                    lblTrnsfLines_CntTotalReg.setText(oCnt_Lines.getsQuantityTotal());
                                    lblTrnsfLines_OrderEntry.setText(oCnt_Lines.getsOrderEntry());
                                    txtTrnsfLines_Ubic.setText(oCnt_Lines.getsUbicacion().replace("anyType{}" ,"" ));
                                    if (oCnt_Lines.getiLineNum() == -1) {
                                        lblTrnsfLines_LineNum.setText(String.valueOf(oCnt_Lines.getiLineNum()));
                                        lblTrnsfLines_Msg.setText("El artículo no existe en el conteo");
                                    }
                                    else{
                                        lblTrnsfLines_LineNum.setText(String.valueOf(oCnt_Lines.getiLineNum()));
                                    }
                                    txtTrnsfLines_CodeBars.setText("");
                                } else if(oCnt_Lines.getiLineNum() == -2){
                                    Mensaje("El artículo no esta disponible");
                                }
                            }
                            oPrgsDialog.dismiss();
                            txtTrnsfLines_CodeBars.requestFocus();
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
        if(lblTrnsfLines_CodeBars.length()>0){
            if(txtTrnsfLines_Ubic.length()>0){
                if (Integer.parseInt(lblTrnsfLines_CntTotal.getText().toString()) >0){
                    if (Integer.parseInt(lblTrnsfLines_CntTotal.getText().toString()) > Integer.parseInt(lblTrnsfLines_CntTotalReg.getText().toString())){
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(frmAdd_TrflLines.this);
                        builder.setMessage("Introduzca una cantidad igual o inferior a la cantidad liberada, la cantidad es de:  " + lblTrnsfLines_CntTotalReg.getText().toString());

                        builder.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                txtTrnsfLines_CodeBars.requestFocus();
                            }
                        });

/*                        builder.setNegativeButton("Agregar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (Update_ItemCntLines(0)){
                                    txtTrnsfLines_CodeBars.requestFocus();
                                }
                            }
                        });*/

                        android.app.AlertDialog alert = builder.create();
                        alert.show();
                    }
                    else
                    {
                        if (Update_ItemCntLines(0)){
                            //Limpia_campos();d
                            txtTrnsfLines_CodeBars.requestFocus();
                        }
                    }
                    /* if (ValidaItemCntLines()){

                    }else {
                        Mensaje("Nose registro el conteo, por favor intentelo mastarde.");
                    }*/

                }else{
                    Mensaje("Por favor registre una cantidad");
                }
            }else{
                Mensaje("Por favor ingrese una ubicación");
            }
        }else
        {
            Mensaje("Por favor ingrese un código de barras");
        }
    }

    public boolean ValidaItemCntLines()
    {
         boolean bResp= false;
        oPrgsDialog = oPrgsDialog.show(frmAdd_TrflLines.this, "Por favor espere...", "Mientras se carga su conteo", true, false);
        Thread oThread = new Thread(){
            @Override
            public void run() {
                AdminSQLite oAdmin = null;
                clsOCFG oConn= null;
                Cursor oCursor=null;
                try{
                    Global_Functions oVariablesGlobales = new Global_Functions();
                    oAdmin = new AdminSQLite(frmAdd_TrflLines.this, oVariablesGlobales.getsNameDB(), null, oVariablesGlobales.getiVersion());
                    oConn = new clsOCFG();
                    int i=  oAdmin.Recupera_Usuario(oConn).getCount();
                    if (i>0){
                        oCnt_Lines = new clsCntInv_Lines();
                        oCnt_Lines.setsDocEntry(sDocEntry);
                        oCnt_Lines.setsCodeBars(txtTrnsfLines_CodeBars.getText().toString());
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
                                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(frmAdd_TrflLines.this);
                                    builder.setMessage("Ya existe una cantidad de " + oCnt_Lines.getsQuantity() + " para este artículo, ¿Desea remplazar o agregarlo al conteo?");

                                    builder.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                                    builder.setPositiveButton("Remplazar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            ScanCodeBars(txtTrnsfLines_CodeBars.getText().toString());
                                            lblTrnsfLines_CntTotal.setText(oCnt_Lines.getsQuantity());
                                        }
                                    });

                                    builder.setNegativeButton("Agregar", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            ScanCodeBars(txtTrnsfLines_CodeBars.getText().toString());
                                            lblTrnsfLines_CntTotal.setText(String.valueOf(Integer.parseInt(oCnt_Lines.getsQuantity()) + Integer.parseInt(lblTrnsfLines_CntTotal.getText().toString())));
                                        }
                                    });

                                    android.app.AlertDialog alert = builder.create();
                                    alert.show();
                                }else
                                {
                                    ScanCodeBars(txtTrnsfLines_CodeBars.getText().toString());
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
        oPrgsDialog = oPrgsDialog.show(frmAdd_TrflLines.this, "Por favor espere...", "Mientras se guarda su conteo", true, false);
        Thread oThread = new Thread(){
            @Override
            public void run() {
                AdminSQLite oAdmin = null;
                clsOCFG oConn= null;
                Cursor oCursor=null;
                try{
                    Global_Functions oVariablesGlobales = new Global_Functions();
                    oAdmin = new AdminSQLite(frmAdd_TrflLines.this, oVariablesGlobales.getsNameDB(), null, oVariablesGlobales.getiVersion());
                    oConn = new clsOCFG();
                    int i=  oAdmin.Recupera_Usuario(oConn).getCount();
                    if (i>0){
                        Double dTotalCount = 0.0 ;
                        oCnt_Lines = new clsCntInv_Lines();
                        oCnt_Lines.setsDocEntry(sDocEntry);
                        oCnt_Lines.setiLineNum(Integer.parseInt(lblTrnsfLines_LineNum.getText().toString()));
                        oCnt_Lines.setsItemCode(lblTrnsfLines_ItemCode.getText().toString());
                        oCnt_Lines.setsCodeBars(lblTrnsfLines_CodeBars.getText().toString());
                        if(iTipe == 0){
                            dTotalCount = Double.parseDouble( lblTrnsfLines_CntTotal.getText().toString());
                        }else {
                            dTotalCount = Double.parseDouble( lblTrnsfLines_CntTotal.getText().toString()) + Double.parseDouble(lblTrnsfLines_CntTotalReg.getText().toString());
                        }
                        oCnt_Lines.setsQuantity(String.valueOf(dTotalCount));
                        oCnt_Lines.setsUbicacion(txtTrnsfLines_Ubic.getText().toString());
                        oCnt_Lines.setsOrderEntry(lblTrnsfLines_OrderEntry.getText().toString()) ;

                            if (oCnt_Lines.Update_ItemTrlfline(oConn) ) {
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
                                Toast.makeText(getBaseContext(), "Cantidad de artículo " + lblTrnsfLines_CodeBars.getText().toString() + " guardada exitosamente.", Toast.LENGTH_LONG).show();
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
        txtTrnsfLines_CodeBars.setText("");
        txtTrnsfLines_Cnt.setText("1");
        txtTrnsfLines_Ubic.setText("");
        lblTrnsfLines_CntTotalReg.setText("0");
        lblTrnsfLines_CntTotal.setText("0");
        lblTrnsfLines_ItemName.setText("");
        lblTrnsfLines_ItemCode.setText("");
        lblTrnsfLines_CodeBars.setText("");
        lblTrnsfLines_WshCode.setText("");
        lblTrnsfLines_LineNum.setText("");
        lblTrnsfLines_Msg.setText("");
        lblTrnsfLines_OrderEntry.setText("");

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
