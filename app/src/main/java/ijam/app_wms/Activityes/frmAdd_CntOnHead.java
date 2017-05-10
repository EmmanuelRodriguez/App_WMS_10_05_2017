//Agregar conteo en línea

package ijam.app_wms.Activityes;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ijam.app_wms.Clases.Global_Functions;
import ijam.app_wms.Clases.clsCntInv_Head;
import ijam.app_wms.R;
import ijam.app_wms.SQLite.AdminSQLite;
import ijam.app_wms.SQLite.clsOCFG;

public class frmAdd_CntOnHead extends AppCompatActivity {
    private int iDia, iMes, iYear, iHora, iMinutos;
    EditText txtCntOnHead_Fecha=null;
    EditText txtCntOnHead_Hora = null;
    EditText txtCntOnHead_Referencia=null;
    EditText txtCntOnHead_Comentarios=null;
    private ProgressDialog oPrgsDialog=null;
    private static DatePickerDialog.OnDateSetListener oOyenteFecha;
    private static TimePickerDialog.OnTimeSetListener oOyenteTime;
    clsCntInv_Head oCntIn_Head = null;
    private String sMsgError ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frmadd__cntonhead);
        Toolbar oToolbar =(Toolbar) findViewById(R.id.barra);
        setSupportActionBar(oToolbar);

        txtCntOnHead_Fecha = (EditText)findViewById(R.id.txtCntOnHead_Fecha);
        txtCntOnHead_Hora = (EditText)findViewById(R.id.txtCntOnHead_Hora);
        txtCntOnHead_Referencia = (EditText)findViewById(R.id.txtCntOnHead_Referencia);
        txtCntOnHead_Comentarios = (EditText)findViewById(R.id.txtCntOnHead_Comentarios);

        Calendar oDate = Calendar.getInstance();
        iDia= oDate.get(Calendar.DAY_OF_MONTH);
        iMes= oDate.get(Calendar.MONTH);
        iYear= oDate.get(Calendar.YEAR);
        iHora = oDate.get(Calendar.HOUR_OF_DAY);
        iMinutos = oDate.get(Calendar.MINUTE);

        GetHrs();
        GetDate();

        oOyenteFecha = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                iDia= dayOfMonth;
                iMes= monthOfYear ;
                iYear= year ;
                GetDate();
            }
        };

        oOyenteTime = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                iHora=hourOfDay;
                iMinutos=minute;
                GetHrs();
            }
        };

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id){
            case 0:
                return new DatePickerDialog(this, oOyenteFecha,iYear, iMes +1, iDia);
            case 1:
                return new TimePickerDialog(this,oOyenteTime,iHora,iMinutos,false);
        }
        return null;
    }

    public void mostrarDate (View oControl){
        showDialog(0);
    }

    public void mostrarTimer (View oControl){
        showDialog(1);
    }

    public void GetDate(){
        Date dFec = null;
        SimpleDateFormat oFormato = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dFec = oFormato.parse(iDia + "/"+ iMes +"/"+iYear);
            txtCntOnHead_Fecha.setText(oFormato.format(dFec));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void GetHrs(){
        Date dFec = null;
        SimpleDateFormat oFormato = new SimpleDateFormat("HH:mm");
        try {
            dFec = oFormato.parse(iHora+":"+iMinutos);
            txtCntOnHead_Hora.setText(oFormato.format(dFec));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void ValidaCntOnHead(View view){
        if (txtCntOnHead_Referencia.length()>0){
            if (txtCntOnHead_Fecha.length()>0){
                if (txtCntOnHead_Hora.length()>0){
                    //if (txtCntOnHead_Comentarios.length()>0){
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(frmAdd_CntOnHead.this);
                        builder.setMessage("¿Desea continuar generando el conteo?")
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
                                                GuardaCntOnHead();
                                            }
                                        });
                        android.app.AlertDialog alert = builder.create();
                        alert.show();
                    //}
                }else{
                    Mensaje("Por favor ingrese una hora");
                }
            }else {
                Mensaje("Por favor ingrese una fecha");
            }
        }else{
            Mensaje("Por favor ingrese una referencia");
        }
    }

    public  void GuardaCntOnHead()
    {
        oPrgsDialog = oPrgsDialog.show(frmAdd_CntOnHead.this, "Por favor espere...", "Mientras se guarda su conteo", true, false);
        Thread oThread = new Thread(){
            @Override
            public void run() {
                AdminSQLite oAdmin = null;
                clsOCFG oConn= null;
                Cursor oCursor=null;
                try{
                    Global_Functions oVariablesGlobales = new Global_Functions();
                    oAdmin = new AdminSQLite(frmAdd_CntOnHead.this, oVariablesGlobales.getsNameDB(), null, oVariablesGlobales.getiVersion());
                    oConn = new clsOCFG();
                    int i=  oAdmin.Recupera_Usuario(oConn).getCount();
                    if (i>0){
                        oCntIn_Head = new clsCntInv_Head();
                        oCntIn_Head.setsRef2(txtCntOnHead_Referencia.getText().toString());
                        oCntIn_Head.setsDocDate(txtCntOnHead_Fecha.getText().toString());
                        oCntIn_Head.setsHora(txtCntOnHead_Hora.getText().toString());
                        oCntIn_Head.setsComentarios(txtCntOnHead_Comentarios.getText().toString());
                        oCntIn_Head.setsCountType("1");
                        if(oCntIn_Head.Add_DocumentoCntOnline(oConn)){
                            Intent oAddOnHead = new Intent(frmAdd_CntOnHead.this, frmAdd_CntOnLines.class);
                            oAddOnHead.putExtra("DocEntry", oCntIn_Head.getsDocEntry());
                            startActivity(oAddOnHead);
                            finish();
                        }else{
                            throw new RuntimeException(oCntIn_Head.getsMsgError());
                        }
                    }
                    else{
                        throw new RuntimeException("No hay conexión disponible");
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
                            }
                        }catch (Exception ex){
                            Mensaje(ex.getMessage());
                        }
                    }
                });
            }
        };
        oThread.start();
    }

    public void Mensaje(String sMsg) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setMessage(sMsg);
        dlgAlert.setTitle("Conteo online");
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
